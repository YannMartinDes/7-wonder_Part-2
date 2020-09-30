package client.iainterface;

import commun.card.Deck;

public interface AI {
    /**
     * Choisi une carte au hasard dans un deck
     * @param deck
     * @return la carte choisie au hasard.
     */
    public int chooseCardFromDeck(Deck deck);
}
