package commun.card;

import java.util.ArrayList;
import java.util.List;

/** Deck est une classe qui représente un ensemble de cartes */
public class Deck extends ArrayList<Card>
{
	/** Ajoute une carte au deck
	 * @param card : carte qu'on veut ajouter */
	public void addCard(Card card)
	{
		this.add(card);
	}

	/** Supprime une carte du deck
	 * @param index : index de la carte dans la main */
	public void removeCard (int index)
	{ this.remove(index); }

	/* Getters */

	public Card getCard (int index)
	{ return this.get(index); }
	
	public int getLength ()
	{ return this.size(); }

	public String toString ()
	{
		int nbCard = 0;
		String res = "[";

		for (int i = 0; i < this.getLength(); i++)
		{
			res += this.getCard(i).getName();
			if(i != this.getLength()-1) //Séparateur si ce n'est pas le dernier.
				res += ", ";
			nbCard++;
			if (nbCard >= 4 && i != this.getLength() - 1)
			{
				nbCard = 0;
				res += "\n[*]"; //Retour à la ligne si ce n'est pas le dernier
			}
		}
		return res + "]";
	}
}
