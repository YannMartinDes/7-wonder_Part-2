package commun.card;

import java.util.ArrayList;
import java.util.List;


public class Deck{

	List<Card> deck = new ArrayList<Card>();
	
	//TODO implement methode pour gerer les carte

	/**
	 * Ajoute une carte au deck
	 * @param card : carte qu'on veut ajouter
	 */
	public void addCard(Card card)
	{
		deck.add(card);
	}


	/**
	 * Supprime une carte du deck
	 * @param index : index de la carte dans la main
	 */
	public void removeCard(int index) {
		deck.remove(index);
	}

	public Card getCard(int index)
	{
		return deck.get(index);
	}
	
	public int getLength(){
		return deck.size();
	}
}
