package servergame.engine;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

	private StatObject statObject;
	
	public GameEngine(List<Player> allPlayers) {
		this.setNbPlayer(allPlayers.size());
		this.allPlayers = allPlayers;
		this.cardManager = new CardManager(allPlayers.size());
		this.nbAge = 1;
		this.currentAge = 1;
		this.statObject = new StatObject();

		/** A DELETE */
		ArrayList<String> usernames = new ArrayList<String>();
		usernames.add("/");
		for (Player p : this.allPlayers)
		{
			usernames.add(p.getName());
		}
		this.statObject.setUsernames(usernames);
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

		/** A DELETE */
		ArrayList<String> usernames = new ArrayList<String>();
		usernames.add("/");
		for (Player p : this.allPlayers)
		{
			usernames.add(p.getName());
		}
		this.statObject.setUsernames(usernames);
	}
	
	
	/**
	 * Permet de lancer une parti
	 */
	public void startGame() {
		GameLogger.logSpaceAfter("---- Début de la partie ----", ConsoleColors.ANSI_YELLOW);
		for(Player player : allPlayers){
			GameLogger.log("Le joueur "+player.getName()+" à rejoint la partie.");
		}

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
			GameLogger.log("---- Debut de l'Age "+currentAge+" ----", ConsoleColors.ANSI_YELLOW);
			cardManager.createHands(currentAge); //on distribue la carte pour l'age qui commence

			/*---- deroulement de l'age courant ----*/
			while (!cardManager.isEndAge()) {
				assignPlayersDeck();
				round();
			}

			//TODO mettre les operation de la fin de l'age (bataille, ...)
			currentAge++; //on passe a l'age superieur
		}

		/*----- fin de la partie -----*/
		GameLogger.logSpaceBefore("---- Fin de la partie ----", ConsoleColors.ANSI_YELLOW);
		GameLogger.logSpaceBefore("--------- Score ------------", ConsoleColors.ANSI_YELLOW);
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
		}
		
		for(Player player : allPlayers) {
			player.playAction(cardManager.getDiscarding());
		}

		for(Player player : allPlayers){//On applique les effets de leur action.
			player.finishAction(cardManager.getDiscarding());
		}
		for(Player player : allPlayers){//On applique les effets post-action
			player.afterAction();
		}

		cardManager.rotateHands(currentAge%2==1);//Age impair = sens horaire
		GameLogger.log("-- Fin du round --", ConsoleColors.ANSI_YELLOW);

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
}
