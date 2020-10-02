package client.iainterface;

import commun.action.Action;
import commun.action.ActionType;
import commun.card.Deck;
import commun.effect.EffectList;

public interface AI {
    /**
     * Choisi une carte au hasard dans un deck
     * @param deck
     * @return la carte choisie au hasard.
     */
    public Action chooseAction(Deck deck,  int playerCoins, EffectList playerEffects);
}
