package commun.card;

import java.util.ArrayList;
import java.util.List;


public class Deck extends ArrayList<Card>{


	/**
	 * Ajoute une carte au deck
	 * @param card : carte qu'on veut ajouter
	 */
	public void addCard(Card card)
	{
		this.add(card);
	}


	/**
	 * Supprime une carte du deck
	 * @param index : index de la carte dans la main
	 */
	public void removeCard(int index) {
		this.remove(index);
	}

	public Card getCard(int index)
	{
		return this.get(index);
	}
	
	public int getLength(){
		return this.size();
	}
}
