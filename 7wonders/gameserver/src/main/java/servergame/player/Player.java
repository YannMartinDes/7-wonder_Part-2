package servergame.player;

import commun.card.Card;
import commun.card.Deck;
import commun.wonderboard.WonderBoard;
import log.GameLogger;


/**
 * Represente un joueur
 * @author Yohann
 *
 */

public class Player implements Comparable<Player>{
	
	private PlayerController controller;
	private final String name;
	private WonderBoard wonderBoard;
	private Deck currentDeck;
	private Card playedCard;
	private int finalScore;

	
	public Player(String name,WonderBoard wondersBoard) {
		this.name = name;
		this.setWonderBoard(wondersBoard);
	}

	public Player(String name)
	{ this(name, null); }
	
	
	public String getName() {
		return name;
	}

	public WonderBoard getWonderBoard() {
		return wonderBoard;
	}

	public int getFinalScore() { return finalScore; }

	/**
	 * @param wondersBoard the wondersBoard to set
	 */
	public void setWonderBoard(WonderBoard wondersBoard) {
		this.wonderBoard = wondersBoard;
	}

	public Deck getCurrentDeck() {
		return currentDeck;
	}


	public void setCurrentDeck(Deck currentDeck) {
		this.currentDeck = currentDeck;
	}
	public void setFinalScore(int finalScore) {
		this.finalScore = finalScore;
	}
	
	/**
	 * fait jouer l'action par le joueur
	 * @param deckIndex l'index de la carte que le joueur joue dans le deck
	 * 
	 */
	public void playAction(Deck discardingDeck) {
		controller.getAction().playAction(currentDeck, discardingDeck, wonderBoard, name);
	}

	/**
	 * L'action que le joueur a fait s'effectue 
	 * tout les action s'effectue après que tout les joueur
	 * on finit de jouer le tour
	 */
	public void playAction(){
		wonderBoard.addCardToBuilding(playedCard);
		GameLogger.log("le joueur : ["+name+"] a jouer la carte : "+playedCard.toString());
		playedCard=null;
	}

	/**
	 * L'ia est appelée pour choisir le coup
	 * qu'elle veux jouer
	 */
	public void playController() {
		controller.chooseCardFromDeck(currentDeck);
	}
	

	/**
	 * @return the controller
	 */
	public PlayerController getController() {
		return controller;
	}

	/**
	 * @param controller the controller to set
	 */
	public void setController(PlayerController controller) {
		this.controller = controller;
	}


	/**
	 *  Comparer le score de 2 joueurs
	 */
	@Override
	public int compareTo(Player player){
		return getFinalScore()-player.getFinalScore();
	}
}
