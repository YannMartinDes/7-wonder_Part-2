package servergame.player;

import commun.card.Card;
import commun.card.Deck;
import commun.wonderboard.WonderBoard;
import log.GameLogger;
import servergame.coins.Coins;

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
	private Card playedCard;
	private int finalScore;
	private Coins coins;

	/** Constructeur */
	public Player (String name, WonderBoard wondersBoard, Coins coins)
	{
		this.name = name;
		this.setWonderBoard(wondersBoard);
		this.coins = coins;
	}

	public Player(String name)
	{ this(name, null, new Coins(3)); }

	/* Getters - Setters */
	
	public String getName ()
	{ return name; }

	public WonderBoard getWonderBoard ()
	{ return wonderBoard; }

	public int getFinalScore ()
	{ return finalScore; }

	public Coins getCoins ()
	{ return this.coins; }

	public void setCoins (Coins coins)
	{ this.coins = coins; }

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
	 * @param discardingDeck le deck de defaussement
	 */
	public void playAction (Deck discardingDeck)
	{
		int discardingDeckSize;

		discardingDeckSize = discardingDeck.getLength();
		controller.getAction().playAction(currentDeck, discardingDeck, wonderBoard, name);

		// Si le deck de defaussement a ete change, une carte a ete defaussee
		if (discardingDeck.getLength() != discardingDeckSize)
		{
			// Toute carte vaut 3 pieces a la revente
			this.coins.obtain3coins();
		}
	}

	/**
	 * L'action que le joueur a fait s'effectue 
	 * tout les action s'effectue après que tout les joueur
	 * on finit de jouer le tour
	 */
	public void playAction ()
	{
		wonderBoard.addCardToBuilding(playedCard);
		GameLogger.log("le joueur : ["+name+"] a jouer la carte : "+playedCard.toString());
		playedCard=null;
	}

	/**
	 * L'ia est appelée pour choisir le coup
	 * qu'elle veux jouer
	 */
	public void playController ()
	{ controller.chooseCardFromDeck(currentDeck); }

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
}
