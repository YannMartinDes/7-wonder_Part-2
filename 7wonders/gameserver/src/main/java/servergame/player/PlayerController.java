package servergame.player;

import client.AI.AI;
import commun.card.Deck;
import commun.action.Action;
import commun.action.BuildAction;

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
    public void chooseCardFromDeck (Deck deck)
	{
    	Action value;

    	value = ai.chooseCardFromDeck(deck);
    	//TODO ia crée une action
    	this.action = value;
    }
    
	public Action getAction() {
		return action;
	}

}
