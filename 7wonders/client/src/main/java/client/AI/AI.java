package client.AI;

import commun.card.Deck;
import commun.action.Action;

public interface AI {
    /**
     * Choisi une carte au hasard dans un deck
     * @param deck
     * @return la carte choisie au hasard.
     */
    public Action chooseAction(Deck deck);
}
