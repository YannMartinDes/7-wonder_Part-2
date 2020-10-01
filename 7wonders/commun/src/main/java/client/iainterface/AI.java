package client.iainterface;

import commun.action.Action;
import commun.action.ActionType;
import commun.card.Deck;

public interface AI {
    /**
     * Choisi une carte au hasard dans un deck
     * @param deck
     * @return la carte choisie au hasard.
     */
    public Action chooseAction(Deck deck);
}
