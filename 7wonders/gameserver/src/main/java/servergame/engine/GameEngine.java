package servergame.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import commun.communication.StatObject;
import servergame.player.Player;
import commun.wonderboard.WonderBoard;
import log.ConsoleColors;
import log.GameLogger;
import servergame.card.CardManager;
import servergame.ScoreCalculator;
import servergame.wonderboard.WonderBoardFactory;

/**
 * Moteur de jeu qui a pour role de gerer le deroulement d'une partie
 * elle gere les tour, les age et la fin de partie.
 * @author Yohann
 *
 */
public class GameEngine {
	
	private int nbPlayer;
	private List<Player> allPlayers;
	private CardManager cardManager;
	private final int nbAge; //nombre d'age durant la partie
	private int currentAge;

	/** Objet pour les statistiques */
	private StatObject statObject;
	
	public GameEngine(List<Player> allPlayers) {
		this.setNbPlayer(allPlayers.size());
		this.allPlayers = allPlayers;
		this.cardManager = new CardManager(allPlayers.size());
		this.nbAge = 2;
		this.currentAge = 1;
		this.statObject = new StatObject();
	}

	/** Constructeur pour Tests Unitaires */
	public GameEngine (int nbPlayer, List<Player> allPlayers, CardManager cardManager, int nbAge, int currentAge)
	{
		this.nbPlayer = nbPlayer;
		this.allPlayers = allPlayers;
		this.cardManager = cardManager;
		this.currentAge = currentAge;
		this.nbAge = nbAge;
		this.statObject = new StatObject();
	}
	
	
	/**
	 * Permet de lancer une parti
	 */
	public void startGame()
	{
		GameLogger.logSpaceAfter("---- Début de la partie ----", ConsoleColors.ANSI_YELLOW_BOLD_BRIGHT);

		ArrayList<String> usernames = new ArrayList<String>();

		usernames.add("/");
		for(Player player : allPlayers)
		{
			GameLogger.log("Le joueur "+player.getName()+" à rejoint la partie.");
			usernames.add(player.getName());

		}
		/** Ajout des pseudonymes */
		this.statObject.setUsernames(usernames);
		assignPlayersWonderBoard();
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
			GameLogger.log("---- Début de l'Âge "+currentAge+" ----", ConsoleColors.ANSI_YELLOW_BOLD);
			cardManager.createHands(currentAge); //on distribue la carte pour l'age qui commence

			/*---- deroulement de l'age courant ----*/
			while (!cardManager.isEndAge()) {
				assignPlayersDeck();
				round();
			}
			
			GameLogger.logSpaceBefore("-- Début conflits militaires --", ConsoleColors.ANSI_RED_BOLD_BRIGHT);
			for(Player player : allPlayers){
				calculateConflictPoints(player,currentAge);
			}
			GameLogger.logSpaceBefore("-- Fin conflits militaires --", ConsoleColors.ANSI_RED_BOLD_BRIGHT);

			currentAge++; //on passe a l'age superieur
		}

		/*----- fin de la partie -----*/
		GameLogger.logSpaceBefore("---- Fin de la partie ----", ConsoleColors.ANSI_YELLOW_BOLD_BRIGHT);
		GameLogger.logSpaceBefore("--------- Score ------------", ConsoleColors.ANSI_YELLOW_BOLD_BRIGHT);
		ScoreCalculator score = new ScoreCalculator(this.statObject);
		score.printRanking(allPlayers);
	}
	
	/**
	 * le deroulement d'un tour de jeu
	 */
	private void round() {
		GameLogger.logSpaceBefore("-- Début du round --", ConsoleColors.ANSI_YELLOW);

		for(Player player : allPlayers) {
			player.chooseAction();
			player.playAction(this.statObject);
		}

		for(Player player : allPlayers){//On applique les effets de leur action.
			player.finishAction(cardManager.getDiscarding());
		}
		for(Player player : allPlayers){//On applique les effets post-action
			player.afterAction();
		}

		cardManager.rotateHands(currentAge%2==1);//Age impair = sens horaire
		GameLogger.logSpaceBefore("-- Fin du round --", ConsoleColors.ANSI_YELLOW);

		GameLogger.logSpaceBefore("--- Information ---", ConsoleColors.ANSI_BLUE_BOLD_BRIGHT);
		for(Player player : allPlayers) {
			GameLogger.logSpaceBefore("-- Information du joueur "+player.getName()+" ("+player.getWonderBoard().getWonderName()+") :",ConsoleColors.ANSI_BLUE);
			player.information();
		}

		//TODO score calcule + display result
	}
	
	
	/**
	 * on assigne le deck au joueur pour le tour qui commence
	 */
	private void assignPlayersDeck(){
		for(int i =0; i<nbPlayer; i++) {
			allPlayers.get(i).setCurrentDeck(cardManager.getHand(i));
		}
	}
	
	
	/**
	 * On assigne une merveille au joueur pour la partie
	 */
	private void assignPlayersWonderBoard(){
		ArrayList<WonderBoard> wonders = new WonderBoardFactory().chooseWonderBoard(nbPlayer);
		
		for(int i =0; i<nbPlayer; i++) {
			allPlayers.get(i).setWonderBoard(wonders.get(i));
		}
	}

	/**
	 * Assigne les voisins (leur wonderboard) aux joueurs.
	 */
	private void assignNeightbours(){
		for(int i = 0; i<nbPlayer; i++){
			//voisin de droite
			allPlayers.get(i).setRightNeightbour(allPlayers.get((i+1)%nbPlayer).getWonderBoard());
			//voisin de gauche.
			if(i == 0){//Cas particulier
				allPlayers.get(0).setLeftNeightbour(allPlayers.get(nbPlayer-1).getWonderBoard());
			}
			else{
				allPlayers.get(i).setLeftNeightbour(allPlayers.get(i-1).getWonderBoard());
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
		return allPlayers;
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
		for (int i = 0; i < this.allPlayers.size(); i++)
		{ conflictsStats.add(0); }

		GameLogger.log("");

		if (leftMilitaryPower < playerMilitaryPower){
			GameLogger.log("Le joueur "+player.getName()+" a gagné son conflit militaire face à son voisin de gauche", ConsoleColors.ANSI_GREEN);
			GameLogger.log("Le joueur obtient un jeton Victoire de '+" + conflictsPoints +"' points");
			player.getWonderBoard().addConflictPoints(conflictsPoints);

			conflictsStats.set(currentPlayer, conflictsStats.get(currentPlayer) + conflictsPoints);
		}
		else if (leftMilitaryPower > playerMilitaryPower){
			GameLogger.log("Le joueur "+player.getName()+" a perdu son conflit militaire face à son voisin de gauche", ConsoleColors.ANSI_RED);
			GameLogger.log("Le joueur obtient un jeton Défaite de '-1' point");
			player.getWonderBoard().removeConflictPoints(1);

			conflictsStats.set(currentPlayer, conflictsStats.get(currentPlayer) - 1);
		}
		else {
			GameLogger.log("Le joueur " + player.getName() + " et son voisin de gauche ont la même puissance militaire", ConsoleColors.ANSI_BLACK);
			GameLogger.log("Le joueur n'obtient pas de jeton");
		}
		if (rightMilitaryPower < playerMilitaryPower){
			GameLogger.log("Le joueur "+player.getName()+" a gagné son conflit militaire face à son voisin de droite", ConsoleColors.ANSI_GREEN);
			GameLogger.log("Le joueur obtient un jeton Victoire de '+" + conflictsPoints +"' points");
			player.getWonderBoard().addConflictPoints(conflictsPoints);

			conflictsStats.set(currentPlayer, conflictsStats.get(currentPlayer) + conflictsPoints);
		}
		else if (rightMilitaryPower > playerMilitaryPower){
			GameLogger.log("Le joueur "+player.getName()+" a perdu son conflit militaire face à son voisin de droite", ConsoleColors.ANSI_RED);
			GameLogger.log("Le joueur obtient un jeton Défaite de '-1' point");
			player.getWonderBoard().removeConflictPoints(1);

			conflictsStats.set(currentPlayer, conflictsStats.get(currentPlayer) - 1);
		}
		else {
			GameLogger.log("Le joueur " + player.getName() + " et son voisin de droite ont la même puissance militaire", ConsoleColors.ANSI_BLACK);
			GameLogger.log("Le joueur n'obtient pas de jeton");
		}

		/** Enregistrer les statistiques */
		this.statObject.getStatConflics(age - 1).add(conflictsStats);
	}
}
