package client.AI;

import commun.action.AbstractAction;
import commun.card.Deck;
import commun.action.Action;
import commun.effect.EffectList;
import commun.effect.ScientificType;
import commun.request.RequestToPlayer;
import commun.wonderboard.WonderBoard;
import commun.wonderboard.WonderStep;

import java.util.List;

/** Interface qui représente les fonctionnalités d'une intelligence artificielle */
public abstract class AI implements RequestToPlayer {
    /**
     * Choisi une carte au hasard dans un deck et l'action qu'elle veut effectuer sur cette carte
     * @param deck
     * @return la carte choisie au hasard.
     */
    public abstract AbstractAction chooseAction(Deck deck, int playerCoins, EffectList playerEffects);

    /**
     * Choisi une possibilité d'achat chez les voisins selon une liste de possibilité d'achat.
     * @param purchaseChoice : la liste de possibilité d'achat
     * @return la possibilité choisie
     */
    public abstract Integer[] choosePurchasePossibility(List<Integer[]> purchaseChoice);



    /**
     * Permet de choisir l'effet guildes des scientifiques a la fin de la partie
     * @param wonderBoard la wonderboard du joueur
     * @return le type selectionner
     */
    public abstract ScientificType useScientificsGuildEffect(WonderBoard wonderBoard);

  
    /**
     * Choisi une carte au hasard dans les carte defaussé
     * @param deck
     * @return la carte choisie au hasard.
     */
    public abstract int chooseCard(Deck deck);


}
