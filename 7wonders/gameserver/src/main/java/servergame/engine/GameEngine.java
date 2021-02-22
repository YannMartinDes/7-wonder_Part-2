package servergame.engine;

import commun.communication.StatModule;
import commun.communication.StatObject;
import commun.player.Player;
import commun.wonderboard.WonderStep;
import log.Logger;
import log.coloring.ConsoleColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import servergame.card.CardManager;
import servergame.player.PlayerController;
import servergame.player.PlayerManager;
import servergame.score.ScoreCalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Moteur de jeu qui a pour role de gerer le deroulement d'une partie
 * elle gere les tour, les age et la fin de partie.
 * @author Yohann
 */
@Component
@Scope("singleton")
public class GameEngine {
	
	private int nbPlayer;

	@Autowired
	private PlayerManager players;

	@Autowired
	private CardManager cardManager;

	private final int nbAge; //nombre d'age durant la partie
	private int currentAge;

	/** Objet pour les statistiques */
	private StatObject statObject;

	public GameEngine() {
		this.nbAge = 3;
		this.currentAge = 1;
		this.statObject = StatModule.getInstance();
	}

	/** Constructeur pour Tests Unitaires */
	public GameEngine ( PlayerManager allPlayers, CardManager cardManager, int nbAge, int currentAge)
	{
		this.setNbPlayer(allPlayers.getNbPlayer());
		this.players = allPlayers;
		this.cardManager = cardManager;
		this.currentAge = currentAge;
		this.nbAge = nbAge;
		this.statObject = StatModule.getInstance();
	}

	public void init (PlayerManager allPlayers)
	{
		this.currentAge = 1;
		this.statObject = StatModule.getInstance();
		this.setNbPlayer(allPlayers.getNbPlayer());
		this.players = allPlayers;
		this.cardManager.init(allPlayers.getNbPlayer());
		this.statObject.construct(this.players.getAllPlayers().size());
	}
	
	/**
	 * Permet de lancer une parti
	 */
	public void startGame()
	{
		Logger.logger.logSpaceAfter("---- Début de la partie ----", ConsoleColors.ANSI_YELLOW_BOLD_BRIGHT);

		ArrayList<String> usernames = new ArrayList<>();
		ArrayList<String> AINames = new ArrayList<>();

		players.initPlayerView(); //le joueur peut voir ces voisin

		players.assignPlayersWonderBoard();
		usernames.add("/");
		for(Player player : players.getAllPlayers())
		{
			Logger.logger.log("Le joueur "+player.getName()+" à rejoint la partie avec la Merveille "+player.getWonderBoard().getWonderName()+" face "+player.getWonderBoard().getFace()+" .");
			usernames.add(player.getName());
		}

		AINames.add("IA utilisée");
		for (PlayerController playerController : players.getPlayerControllers())
		{
			AINames.add(playerController.getAI().toString());
		}

		/** Ajout des pseudonymes */
		this.statObject.setUsernames(usernames);
		this.statObject.setAIUsed(AINames);

		players.assignNeightbours();
		gameLoop();
	}


	/**
	 * Represente le deroulement de la partie
	 */
	private void gameLoop()
	{
		/*---- deroulement des age ----*/
		while (currentAge<=nbAge) {
			Logger.logger.log("---- Début de l'Âge "+currentAge+" ----", ConsoleColors.ANSI_YELLOW_BOLD);
			cardManager.createHands(currentAge); //on distribue la carte pour l'age qui commence
			//reset les jokers dans les étapes de la merveille pour pouvoir les reutiliser

			for (Player p: players.getAllPlayers()) {
				p.getWonderBoard().resetWonderStepsJokers();
			}

			/*---- deroulement de l'age courant ----*/
			while (!cardManager.isEndAge()) {
				players.assignPlayersDeck(cardManager);
				round();
			}

			/*------jouer la derniere carte avec l'étape special de la merveille---*/

			for(PlayerController playerController : players.getPlayerControllers()){
				Player player = playerController.getPlayer();
				for (WonderStep wonderStep: player.getWonderBoard().getWonderSteps()
				) {
					if (wonderStep.getBuilt() && wonderStep.isCanPlayLastCard()) {
						playerController.playLastCard(cardManager.getDiscarding());
					}
				}
			}

			Logger.logger.logSpaceBefore("-- Début conflits militaires --", ConsoleColors.ANSI_RED_BOLD_BRIGHT);
			for(Player player : players.getAllPlayers()){
				calculateConflictPoints(player,currentAge);
			}
			Logger.logger.logSpaceBefore("-- Fin conflits militaires --", ConsoleColors.ANSI_RED_BOLD_BRIGHT);


			/* Calcul des statistiques a la fin d'un age */
			if (currentAge < 3)
			{
				ScoreCalculator score = new ScoreCalculator();
				score.midGameStatistics(this.players.getAllPlayers());
				this.statObject.incrementAge();
			}

			currentAge++; //on passe a l'age superieur
		}

		/*----- fin de la partie -----*/
		Logger.logger.logSpaceBefore("---- Fin de la partie ----", ConsoleColors.ANSI_YELLOW_BOLD_BRIGHT);
		//permet de demander au joueur le type de leurs carte guilde des scientifiques
		new ScientistsGuildAction(players.getPlayerControllers()).useScientistsGuildEffect();


		Logger.logger.logSpaceBefore("--------- Score ------------", ConsoleColors.ANSI_YELLOW_BOLD_BRIGHT);
		ScoreCalculator score = new ScoreCalculator();
		score.printRanking(players.getAllPlayers());


	}
	
	/**
	 * le deroulement d'un tour de jeu
	 */
	private void round() {
		Logger.logger.logSpaceBefore("-- Début du round --", ConsoleColors.ANSI_YELLOW);

		players.chooseAction();

		players.playAction(cardManager.getDiscarding());

		players.finishAction(cardManager.getDiscarding());


		cardManager.rotateHands(currentAge%2==1);//Age impair = sens horaire
		Logger.logger.logSpaceBefore("-- Fin du round --", ConsoleColors.ANSI_YELLOW);

		players.informations();
		//TODO score calcule + display result
	}


	
	public int getNbPlayer() {
		return nbPlayer;
	}

	public void setNbPlayer(int nbPlayer) {
		this.nbPlayer = nbPlayer;
	}

	public List<Player> getAllPlayers() {
		return players.getAllPlayers();
	}

	public CardManager getCardManager() {
		return cardManager;
	}

	public StatObject getStatObject ()
	{ return this.statObject; }


	/**
	 * Renvoie le nombre de points de conflits attribués suivant l'âge
	 * @param age : age courant
	 * @return Nombre de Points de conflits
	 */
	public int getConflictPointsByAge(int age){
		switch (age) {
			case 1:
				return 1;
			case 2:
				return 3;
			case 3:
				return 5;
			default:
				return -1;
		}
	}

	/**
	 * Calcule le nombre de points de conflits que l'on va donner au joueur
	 * @param player : joueur qu'on veut testet
	 * @param age : age courant
	 */
	protected void calculateConflictPoints(Player player, int age)
	{
		int playerMilitaryPower = player.getWonderBoard().getMilitaryPower();
		int leftMilitaryPower = player.getLeftNeightbour().getMilitaryPower();
		int rightMilitaryPower = player.getRightNeightbour().getMilitaryPower();
		int conflictsPoints = getConflictPointsByAge(age);

		/** Statistiques militaires */
		ArrayList<Integer> conflictsStats = new ArrayList<Integer>();
		int currentPlayer = this.statObject.getUsernames().indexOf(player.getName()) - 1;
		for (int i = 0; i < this.players.getNbPlayer(); i++)
		{ conflictsStats.add(0); }

		Logger.logger.log("");

		if (leftMilitaryPower < playerMilitaryPower){
			Logger.logger.log("Le joueur "+player.getName()+" a gagné son conflit militaire face à son voisin de gauche", ConsoleColors.ANSI_GREEN);
			Logger.logger.log("Le joueur obtient un jeton Victoire de '+" + conflictsPoints +"' points");
			player.getWonderBoard().addConflictPoints(conflictsPoints);

			conflictsStats.set(currentPlayer, conflictsStats.get(currentPlayer) + conflictsPoints);
		}
		else if (leftMilitaryPower > playerMilitaryPower){
			Logger.logger.log("Le joueur "+player.getName()+" a perdu son conflit militaire face à son voisin de gauche", ConsoleColors.ANSI_RED);
			Logger.logger.log("Le joueur obtient un jeton Défaite de '-1' point");
			player.getWonderBoard().removeConflictPoints(1);

			conflictsStats.set(currentPlayer, conflictsStats.get(currentPlayer) - 1);
		}
		else {
			Logger.logger.log("Le joueur " + player.getName() + " et son voisin de gauche ont la même puissance militaire", ConsoleColors.ANSI_BLACK);
			Logger.logger.log("Le joueur n'obtient pas de jeton");
		}
		if (rightMilitaryPower < playerMilitaryPower){
			Logger.logger.log("Le joueur "+player.getName()+" a gagné son conflit militaire face à son voisin de droite", ConsoleColors.ANSI_GREEN);
			Logger.logger.log("Le joueur obtient un jeton Victoire de '+" + conflictsPoints +"' points");
			player.getWonderBoard().addConflictPoints(conflictsPoints);

			conflictsStats.set(currentPlayer, conflictsStats.get(currentPlayer) + conflictsPoints);
		}
		else if (rightMilitaryPower > playerMilitaryPower){
			Logger.logger.log("Le joueur "+player.getName()+" a perdu son conflit militaire face à son voisin de droite", ConsoleColors.ANSI_RED);
			Logger.logger.log("Le joueur obtient un jeton Défaite de '-1' point");
			player.getWonderBoard().removeConflictPoints(1);

			conflictsStats.set(currentPlayer, conflictsStats.get(currentPlayer) - 1);
		}
		else {
			Logger.logger.log("Le joueur " + player.getName() + " et son voisin de droite ont la même puissance militaire", ConsoleColors.ANSI_BLACK);
			Logger.logger.log("Le joueur n'obtient pas de jeton");
		}

		/** Enregistrer les statistiques */
		this.statObject.getStatByAge(age - 1).getStatConflict().add(conflictsStats);
	}

	public int getCurrentAge() {
		return currentAge;
	}

	// pour les test
	public PlayerManager getPlayers() {
		return players;
	}
}
