package commun.request;

import commun.action.Action;
import commun.card.Deck;
import commun.effect.EffectList;
import commun.effect.ScientificType;
import commun.wonderboard.WonderBoard;

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
    public Action chooseAction(Deck deck, int playerCoins, EffectList playerEffects);

    /**
     * Choisi une possibilité d'achat chez les voisins selon une liste de possibilité d'achat.
     * @param purchaseChoice : la liste de possibilité d'achat
     * @return la possibilité choisie
     */
    public Integer[] choosePurchasePossibility(List<Integer[]> purchaseChoice);



    /**
     * Permet de choisir l'effet guildes des scientifiques a la fin de la partie
     * @param wonderBoard la wonderboard du joueur
     * @return le type selectionner
     */
    public ScientificType useScientificsGuildEffect(WonderBoard wonderBoard);


    /**
     * Choisi une carte au hasard dans les carte defaussé
     * @param deck
     * @return la carte choisie au hasard.
     */
    public int chooseCard(Deck deck);

    //TODO changer et diminuer les paramettre le joueur pouras faire des requet si il en a besoin.
//    /**
//     * Le joueur dois choisir l'action qu'il veut faire pour ce tour
//     * @return l'action choisie.
//     */
//    public Action chooseAction(Deck currentDeck);
//
//    /**
//     * Le joueur dois choisir si il veut faire du commerce pour construire
//     * si il veut pas il renvoie -1 sinon il renvoie l'index voulue
//     * @param purchaseChoice : la liste de possibilité d'achat
//     * @return la possibilité choisie
//     */
//    public int choosePurchasePossibility(List<Integer[]> purchaseChoice);
//
//
//
//    /**
//     * Le joueur dois choisir l'effet guildes des scientifiques a la fin de la partie
//     * @return le type selectionner
//     */
//    public ScientificType useScientificsGuildEffect();
//
//
//    /**
//     * Le joueur dois choisir une carte au hasard dans les carte defaussé
//     * @param deffause la defausse
//     * @return la carte choisie.
//     */
//    public int chooseCard(Deck deffause);
}
