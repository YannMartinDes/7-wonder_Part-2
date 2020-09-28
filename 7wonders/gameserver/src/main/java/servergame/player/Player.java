package servergame.player;

import commun.card.Deck;
import commun.wonderboard.WonderBoard;
import servergame.player.PlayerController;

/**
 * Represente un joueur
 * @author Yohann
 *
 */
public class Player {

	private final String name;
	private WonderBoard wonderBoard;

	private Deck currentDeck;


	//action
	private PlayerController controller;

	
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
	 * fait jouer l'action par le joueur
	 * @param deckIndex l'index de la carte que le joueur joue dans le deck
	 * 
	 */
	public void playAction(Deck discardingDeck) {
		controller.getAction().playAction(currentDeck, discardingDeck, wonderBoard, name);
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

}
