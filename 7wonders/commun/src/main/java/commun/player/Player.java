package commun.player;

import commun.card.Card;
import commun.card.Deck;
import commun.wonderboard.WonderBoard;
import log.GameLogger;

/**
 * Represente un joueur
 * @author Yohann
 *
 */
public class Player {

	private final String name;
	private WonderBoard wonderBoard;

	private Deck currentDeck;
	private Deck discarding; //un acces a la defausse

	//action
	private PlayerController controller;
	private Card playedCard;
	
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
	
	/**
	 * prend l'action que le joueur a effectuer au tour et le maintient en memoire
	 * @param deckIndex l'index de la carte que le joueur joue dans le deck
	 * 
	 */
	public void play(int deckIndex) {
		playedCard = currentDeck.getCard(deckIndex);
		currentDeck.removeCard(deckIndex);
	}
	
	
	/**
	 * L'action que le joueur a fait s'effectue 
	 * tout les action s'effectue après que tout les joueur
	 * on finit de jouer le tour
	 */
	public void playAction(){
		wonderBoard.addCardToBuilding(playedCard);
		GameLogger.log("Le joueur : ["+name+"] a joué la carte : "+playedCard.toString());
		playedCard=null;
	}
	
	/**
	 * L'ia est appelée pour choisir le coup
	 * qu'elle veux jouer
	 */
	public void playController() {
		int value = getController().chooseCardFromDeck(currentDeck);
		play(value);
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

}
