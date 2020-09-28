package commun.player;

import client.iainterface.*;
import commun.card.Deck;
import commun.player.action.Action;
import commun.player.action.BuildAction;

/**
 * permet de verifier les entrer de l'ia
 * @author Yohann
 *
 */
public class PlayerController {
	
	private AI ai;
	private Action action;
	
	
	public PlayerController(AI ai) {
		this.ai = ai;
	}
    /**
     * Choisi une carte au hasard dans un deck
     * @param deck
     * @return la carte choisie au hasard.
     */
    public void chooseCardFromDeck(Deck deck) {
    	int value;
    	do{
    		value = ai.chooseCardFromDeck(deck);
    	}
    	while(value<0 && value>=deck.getLength());
    	
    	//TODO ia crée une action
    	this.action = new BuildAction(value);
    
    	
    }
    
	public Action getAction() {
		return action;
	}

}
