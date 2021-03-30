package commun.player;

import commun.card.Deck;
import commun.wonderboard.WonderBoard;
import log.Logger;

/**
 * Represente un joueur
 * @author Yohann et Yann le bg
 *
 */
public class Player implements Comparable<Player>
{
	/* Fields */
	private String name;
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

	public Player(){}//Pour le restTemplate serialisation json

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

//	/**
//	 * fait jouer l'action par le joueur
//	 */
//	public void playAction (StatObject statObject)
//	{
//		controller.playAction(currentDeck,wonderBoard, statObject, name, leftNeightbour, rightNeightbour);
//	}
//
//	public void finishAction(Deck discardingDeck){
//		controller.finishAction(name,wonderBoard,discardingDeck,leftNeightbour ,rightNeightbour);
//	}
//
//	public void afterAction(Deck discardingDeck){
//		controller.afterAction(name,wonderBoard, leftNeightbour, rightNeightbour, discardingDeck);
//	}
//
//	public  void  playLastCard(Deck discardingDeck){
//		controller.playLastCard(currentDeck, wonderBoard,name,leftNeightbour,rightNeightbour,this.wonderBoard.getCoin(),wonderBoard.getAllEffects(),discardingDeck);
//	}

//	/**
//	 * L'ia est appelée pour choisir le coup
//	 * qu'elle veux jouer
//	 */
//	public void chooseAction ()
//	{ controller.chooseAction(currentDeck, this.wonderBoard.getCoin(), wonderBoard.getAllEffects()); }
//
//	/**
//	 * @return the controller
//	 */
//	public PlayerController getController ()
//	{ return controller; }
//
//	/**
//	 * @param controller the controller to set
//	 */
//	public void setController (PlayerController controller)
//	{ this.controller = controller; }

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
		Logger.logger.log("Voisin de gauche : "+getLeftNeightbour().getWonderName());
		Logger.logger.log("Voisin de droite : "+getRightNeightbour().getWonderName());
		Logger.logger.logSpaceBefore("Pièces : "+getWonderBoard().getCoin());
		Logger.logger.log("Puissance millitaire : "+getWonderBoard().getMilitaryPower());
		Logger.logger.log("Jetons conflits : "+getWonderBoard().getConflictPoints());
		Logger.logger.logSpaceAfter("Etapes Merveilles : "+getWonderBoard().countStepBuild()+"/"+getWonderBoard().getWonderSteps().size());
		Logger.logger.log("Constructions : "+getWonderBoard().getBuilding().toString());
		Logger.logger.log("Chainage : "+getWonderBoard().getNameOfFreeCards());
	}
}
