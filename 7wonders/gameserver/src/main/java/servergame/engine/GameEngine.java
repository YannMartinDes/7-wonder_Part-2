package servergame.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import commun.card.Deck;
import commun.communication.StatObject;
import commun.wonderboard.WonderStep;
import servergame.player.Player;
import commun.wonderboard.WonderBoard;
import log.ConsoleColors;
import log.GameLogger;
import servergame.card.CardManager;
import servergame.player.PlayerManager;
import servergame.score.ScoreCalculator;
import servergame.wonderboard.WonderBoardFactory;

/**
 * Moteur de jeu qui a pour role de gerer le deroulement d'une partie
 * elle gere les tour, les age et la fin de partie.
 * @author Yohann
 *
 */
public class GameEngine {
	
	private int nbPlayer;
	private PlayerManager players;
	private CardManager cardManager;
	private final int nbAge; //nombre d'age durant la partie
	private int currentAge;

	/** Objet pour les statistiques */
	private StatObject statObject;
	
	public GameEngine(PlayerManager allPlayers) {
		this.setNbPlayer(allPlayers.getNbPlayer());
		this.players = allPlayers;
		this.cardManager = new CardManager(allPlayers.getNbPlayer());
		this.nbAge = 3;
		this.currentAge = 1;
		this.statObject = new StatObject();
		this.statObject.construct(players.getNbPlayer());
	}

	/** Constructeur pour Tests Unitaires */
	public GameEngine (int nbPlayer, PlayerManager allPlayers, CardManager cardManager, int nbAge, int currentAge)
	{
		this.nbPlayer = nbPlayer;
		this.players = allPlayers;
		this.cardManager = cardManager;
		this.currentAge = currentAge;
		this.nbAge = nbAge;
		this.statObject = new StatObject();
		this.statObject.construct(this.players.getNbPlayer());
	}
	
	/**
	 * Permet de lancer une parti
	 */
	public void startGame()
	{
		GameLogger.getInstance().logSpaceAfter("---- Début de la partie ----", ConsoleColors.ANSI_YELLOW_BOLD_BRIGHT);

		ArrayList<String> usernames = new ArrayList<String>();
		assignPlayersWonderBoard();
		usernames.add("/");
		for(Player player : players.getAllPlayers())
		{
			GameLogger.getInstance().log("Le joueur "+player.getName()+" à rejoint la partie avec la Merveille "+player.getWonderBoard().getWonderName()+" face "+player.getWonderBoard().getFace()+" .");
			usernames.add(player.getName());
		}
		/** Ajout des pseudonymes */
		this.statObject.setUsernames(usernames);
		assignNeightbours();
		gameLoop();
	}

	/**
	 * Represente le deroulement de la partie
	 */
	private void gameLoop()
	{
		/*---- deroulement des age ----*/
		while (currentAge<=nbAge) {
			GameLogger.getInstance().log("---- Début de l'Âge "+currentAge+" ----", ConsoleColors.ANSI_YELLOW_BOLD);
			cardManager.createHands(currentAge); //on distribue la carte pour l'age qui commence
			//reset les jokers dans les étapes de la merveille pour pouvoir les reutiliser

			for (Player p: players.getAllPlayers()) {
				p.getWonderBoard().resetWonderStepsJokers();
			}

			/*---- deroulement de l'age courant ----*/
			while (!cardManager.isEndAge()) {
				assignPlayersDeck();
				round();
			}

			/*------jouer la derniere carte avec l'étape special de la merveille---*/
			for(Player player : players.getAllPlayers()){
				for (WonderStep wonderStep: player.getWonderBoard().getWonderSteps()
				) {
					if (wonderStep.getBuilt() && wonderStep.isCanPlayLastCard()) {
						player.playLastCard(cardManager.getDiscarding());

					}
				}
			}


			GameLogger.getInstance().logSpaceBefore("-- Début conflits militaires --", ConsoleColors.ANSI_RED_BOLD_BRIGHT);
			for(Player player : players.getAllPlayers()){
				calculateConflictPoints(player,currentAge);
			}
			GameLogger.getInstance().logSpaceBefore("-- Fin conflits militaires --", ConsoleColors.ANSI_RED_BOLD_BRIGHT);

			currentAge++; //on passe a l'age superieur
		}

		/*----- fin de la partie -----*/
		GameLogger.getInstance().logSpaceBefore("---- Fin de la partie ----", ConsoleColors.ANSI_YELLOW_BOLD_BRIGHT);
		//permet de demander au joueur le type de leurs carte guilde des scientifiques
		new ScientistsGuildAction(players.getAllPlayers()).useScientistsGuildEffect();

		GameLogger.getInstance().logSpaceBefore("--------- Score ------------", ConsoleColors.ANSI_YELLOW_BOLD_BRIGHT);
		ScoreCalculator score = new ScoreCalculator(this.statObject);
		score.printRanking(players.getAllPlayers());
	}
	
	/**
	 * le deroulement d'un tour de jeu
	 */
	private void round() {
		GameLogger.getInstance().logSpaceBefore("-- Début du round --", ConsoleColors.ANSI_YELLOW);

		for(Player player : players.getAllPlayers()) {
			player.chooseAction();
			player.playAction(this.statObject);
		}

		for(Player player : players.getAllPlayers()){//On applique les effets de leur action.
			player.finishAction(cardManager.getDiscarding());
		}
		for(Player player : players.getAllPlayers()){//On applique les effets post-action
			player.afterAction(cardManager.getDiscarding());
		}

		cardManager.rotateHands(currentAge%2==1);//Age impair = sens horaire
		GameLogger.getInstance().logSpaceBefore("-- Fin du round --", ConsoleColors.ANSI_YELLOW);

		GameLogger.getInstance().logSpaceBefore("--- Information ---", ConsoleColors.ANSI_BLUE_BOLD_BRIGHT);
		for(Player player : players.getAllPlayers()) {
			GameLogger.getInstance().logSpaceBefore("-- Information du joueur "+player.getName()+" ("+player.getWonderBoard().getWonderName()+") :",ConsoleColors.ANSI_BLUE);
			player.information();
		}

		//TODO score calcule + display result
	}
	
	
	/**
	 * on assigne le deck au joueur pour le tour qui commence
	 */
	private void assignPlayersDeck(){
		for(int i =0; i<nbPlayer; i++) {
			players.getAllPlayers().get(i).setCurrentDeck(cardManager.getHand(i));
		}
	}
	
	
	/**
	 * On assigne une merveille au joueur pour la partie
	 */
	private void assignPlayersWonderBoard(){
		ArrayList<WonderBoard> wonders = new WonderBoardFactory().chooseWonderBoard(nbPlayer);
		
		for(int i =0; i<nbPlayer; i++) {
			players.getAllPlayers().get(i).setWonderBoard(wonders.get(i));
		}
	}

	/**
	 * Assigne les voisins (leur wonderboard) aux joueurs.
	 */
	private void assignNeightbours(){
		for(int i = 0; i<nbPlayer; i++){
			//voisin de droite
			players.getAllPlayers().get(i).setRightNeightbour(players.getAllPlayers().get((i+1)%nbPlayer).getWonderBoard());
			//voisin de gauche.
			if(i == 0){//Cas particulier
				players.getAllPlayers().get(0).setLeftNeightbour(players.getAllPlayers().get(nbPlayer-1).getWonderBoard());
			}
			else{
				players.getAllPlayers().get(i).setLeftNeightbour(players.getAllPlayers().get(i-1).getWonderBoard());
			}
		}
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

		GameLogger.getInstance().log("");

		if (leftMilitaryPower < playerMilitaryPower){
			GameLogger.getInstance().log("Le joueur "+player.getName()+" a gagné son conflit militaire face à son voisin de gauche", ConsoleColors.ANSI_GREEN);
			GameLogger.getInstance().log("Le joueur obtient un jeton Victoire de '+" + conflictsPoints +"' points");
			player.getWonderBoard().addConflictPoints(conflictsPoints);

			conflictsStats.set(currentPlayer, conflictsStats.get(currentPlayer) + conflictsPoints);
		}
		else if (leftMilitaryPower > playerMilitaryPower){
			GameLogger.getInstance().log("Le joueur "+player.getName()+" a perdu son conflit militaire face à son voisin de gauche", ConsoleColors.ANSI_RED);
			GameLogger.getInstance().log("Le joueur obtient un jeton Défaite de '-1' point");
			player.getWonderBoard().removeConflictPoints(1);

			conflictsStats.set(currentPlayer, conflictsStats.get(currentPlayer) - 1);
		}
		else {
			GameLogger.getInstance().log("Le joueur " + player.getName() + " et son voisin de gauche ont la même puissance militaire", ConsoleColors.ANSI_BLACK);
			GameLogger.getInstance().log("Le joueur n'obtient pas de jeton");
		}
		if (rightMilitaryPower < playerMilitaryPower){
			GameLogger.getInstance().log("Le joueur "+player.getName()+" a gagné son conflit militaire face à son voisin de droite", ConsoleColors.ANSI_GREEN);
			GameLogger.getInstance().log("Le joueur obtient un jeton Victoire de '+" + conflictsPoints +"' points");
			player.getWonderBoard().addConflictPoints(conflictsPoints);

			conflictsStats.set(currentPlayer, conflictsStats.get(currentPlayer) + conflictsPoints);
		}
		else if (rightMilitaryPower > playerMilitaryPower){
			GameLogger.getInstance().log("Le joueur "+player.getName()+" a perdu son conflit militaire face à son voisin de droite", ConsoleColors.ANSI_RED);
			GameLogger.getInstance().log("Le joueur obtient un jeton Défaite de '-1' point");
			player.getWonderBoard().removeConflictPoints(1);

			conflictsStats.set(currentPlayer, conflictsStats.get(currentPlayer) - 1);
		}
		else {
			GameLogger.getInstance().log("Le joueur " + player.getName() + " et son voisin de droite ont la même puissance militaire", ConsoleColors.ANSI_BLACK);
			GameLogger.getInstance().log("Le joueur n'obtient pas de jeton");
		}

		/** Enregistrer les statistiques */
		this.statObject.getStatConflics(age - 1).add(conflictsStats);
	}

}
