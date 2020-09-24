package commun.wonders;

import commun.card.Deck;
import client.iainterface.*;

/**
 * permet de verifier les entrer de l'ia
 * @author Yohann
 *
 */
public class PlayerController {
	
	private AI ai;
	
	public PlayerController(AI ai) {
		this.ai = ai;
	}
    /**
     * Choisi une carte au hasard dans un deck
     * @param deck
     * @return la carte choisie au hasard.
     */
    public int chooseCardFromDeck(Deck deck) {
    	int value;
    	do{
    		value = ai.chooseCardFromDeck(deck);
    	}
    	while(value<0 && value>=deck.getLength());
    	
    	return value;
    	
    }

}
