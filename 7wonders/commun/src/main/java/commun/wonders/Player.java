package commun.wonders;

import commun.card.Deck;
import commun.card.WonderBoard;

public class Player {
	
	private final String name;
	private final WonderBoard wondersBoard;
	private Deck currentDeck;
	
	public Player(String name,WonderBoard wondersBoard) {
		this.name = name;
		this.wondersBoard = wondersBoard;
	}
	
	
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
		wondersBoard.addCardToBuilding(currentDeck.getCard(deckIndex));
		return true;
	}
	

	
	

}
