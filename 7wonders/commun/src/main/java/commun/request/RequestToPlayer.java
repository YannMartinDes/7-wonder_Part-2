package commun.request;

import commun.action.AbstractAction;
import commun.card.Deck;
import commun.effect.ScientificType;

import java.util.List;

/**
 * demande au joueur l'action qu'il veut faire
 */
public interface RequestToPlayer {

    /**
     * Choisi une carte au hasard dans un deck et l'action qu'elle veut effectuer sur cette carte
     * @param deck
     * @return la carte choisie au hasard.
     */
    public AbstractAction chooseAction(Deck deck);

    /**
     * Choisi une possibilité d'achat chez les voisins selon une liste de possibilité d'achat.
     * @param purchaseChoice : la liste de possibilité d'achat
     * @return la possibilité choisie
     */
    public Integer[] choosePurchasePossibility(List<Integer[]> purchaseChoice);



    /**
     * Permet de choisir l'effet guildes des scientifiques a la fin de la partie
     * @return le type selectionner
     */
    public ScientificType useScientificsGuildEffect();


    /**
     * Choisi une carte au hasard dans les carte defaussé
     * @param deck
     * @return la carte choisie au hasard.
     */
    public int chooseCard(Deck deck);


}
