package servergame.player;

import commun.card.Card;
import commun.card.Deck;
import commun.wonderboard.WonderBoard;
import log.ConsoleColors;
import log.GameLogger;

/**
 * Represente un joueur
 * @author Yohann
 *
 */
public class Player implements Comparable<Player>
{
	/* Fields */
	private PlayerController controller;
	private final String name;
	private WonderBoard wonderBoard;
	private Deck currentDeck;
	private int finalScore;
	private WonderBoard leftNeightbour;
	private WonderBoard rightNeightbour;

	/** Constructeur */
	public Player (String name, WonderBoard wondersBoard)
	{
		this.name = name;
		this.setWonderBoard(wondersBoard);
	}

	public Player(String name)
	{ this(name, null); }

	/* Getters - Setters */
	
	public String getName ()
	{ return name; }

	public WonderBoard getWonderBoard ()
	{ return wonderBoard; }

	public int getFinalScore ()
	{ return finalScore; }

	/**
	 * @param wondersBoard the wondersBoard to set
	 */
	public void setWonderBoard (WonderBoard wondersBoard)
	{ this.wonderBoard = wondersBoard; }

	public Deck getCurrentDeck ()
	{ return currentDeck; }

	public void setCurrentDeck (Deck currentDeck)
	{ this.currentDeck = currentDeck; }
	public void setFinalScore (int finalScore)

	{ this.finalScore = finalScore; }

	/**
	 * fait jouer l'action par le joueur
	 */
	public void playAction ()
	{
		controller.playAction(currentDeck,wonderBoard);
	}

	public void finishAction(Deck discardingDeck){
		controller.finishAction(name,wonderBoard,discardingDeck);
	}

	public void afterAction(){
		controller.afterAction(name,wonderBoard, leftNeightbour, rightNeightbour);
	}

	/**
	 * L'ia est appelée pour choisir le coup
	 * qu'elle veux jouer
	 */
	public void chooseAction ()
	{ controller.chooseAction(currentDeck); }

	/**
	 * @return the controller
	 */
	public PlayerController getController ()
	{ return controller; }

	/**
	 * @param controller the controller to set
	 */
	public void setController (PlayerController controller)
	{ this.controller = controller; }

	/**
	 *  Comparer le score de 2 joueurs
	 */
	@Override
	public int compareTo (Player player)
	{ return getFinalScore()-player.getFinalScore(); }

	public WonderBoard getLeftNeightbour() {
		return leftNeightbour;
	}

	public void setLeftNeightbour(WonderBoard leftNeightbour) {
		this.leftNeightbour = leftNeightbour;
	}

	public WonderBoard getRightNeightbour() {
		return rightNeightbour;
	}

	public void setRightNeightbour(WonderBoard rightNeightbour) {
		this.rightNeightbour = rightNeightbour;
	}

	public void information(){
		GameLogger.log("Pièces : "+getWonderBoard().getCoin());
		GameLogger.log("Puissance millitaire : "+getWonderBoard().getMilitaryPower());
		GameLogger.log("Jetons conflits : "+getWonderBoard().getConflictPoints());
		GameLogger.log("Constructions :");
		GameLogger.log(getWonderBoard().getBuilding().toString());
	}
}
