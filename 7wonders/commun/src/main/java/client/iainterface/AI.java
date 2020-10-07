package client.iainterface;

import commun.action.Action;
import commun.action.ActionType;
import commun.card.Deck;
import commun.effect.EffectList;

import java.util.List;

/** Interface qui représente les fonctionnalités d'une intelligence artificielle */
public interface AI {
    /**
     * Choisi une carte au hasard dans un deck
     * @param deck
     * @return la carte choisie au hasard.
     */
    public Action chooseAction(Deck deck,  int playerCoins, EffectList playerEffects);

    /**
     * Choisi une possibilité d'achat chez les voisins selon une liste de possibilité d'achat.
     * @param purchaseChoice : la liste de possibilité d'achat
     * @return la possibilité choisie
     */
    public Integer[] choosePurchasePossibility(List<Integer[]> purchaseChoice);
}
