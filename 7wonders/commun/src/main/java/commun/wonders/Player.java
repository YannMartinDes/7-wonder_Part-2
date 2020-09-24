package commun.wonders;

import commun.card.Card;
import commun.card.Deck;
import commun.card.WonderBoard;

public class Player {
	private final String name;
	private final WonderBoard wondersBoard;
	private Deck currentDeck;
	private Card playedCard;
	
	public Player(String name,WonderBoard wondersBoard) {
		this.name = name;
		this.wondersBoard = wondersBoard;
	}

	public Player(String name)
	{ this(name, null); }
	
	
	public String getName() {
		return name;
	}

	public WonderBoard getWondersBoard() {
		return wondersBoard;
	}


	public Deck getCurrentDeck() {
		return currentDeck;
	}


	public void setCurrentDeck(Deck currentDeck) {
		this.currentDeck = currentDeck;
	}
	
	public boolean play(int deckIndex) {
		if(deckIndex<0 && deckIndex>=currentDeck.getLength()) return false;
		playedCard = currentDeck.getCard(deckIndex);
		currentDeck.removeCard(deckIndex);
		return true;
	}
	
	
	public void playAction(){
		wondersBoard.addCardToBuilding(playedCard);
		playedCard=null;
	}
	
	public void iaPlay() {
		//TODO ia.play
		//TODO play(index)
	}
	

	
	

}
