package commun.request;

import commun.action.Action;
import commun.card.Deck;
import commun.effect.EffectList;
import commun.effect.ScientificType;
import commun.wonderboard.WonderBoard;

import java.util.List;

/**
 * Permet de verifier que l'action est correct
 */
public class RequestPlayerActionCheck implements RequestToPlayer {
    public RequestToPlayer destination;

    public RequestPlayerActionCheck(RequestToPlayer destination){
        this.destination = destination;
    }

    /**
     * Choisi une carte au hasard dans un deck et l'action qu'elle veut effectuer sur cette carte
     *
     * @param deck
     * @param playerCoins
     * @param playerEffects
     * @return la carte choisie au hasard.
     */
    @Override
    public Action chooseAction(Deck deck, int playerCoins, EffectList playerEffects) {
        return null;
    }

    /**
     * Choisi une possibilité d'achat chez les voisins selon une liste de possibilité d'achat.
     *
     * @param purchaseChoice : la liste de possibilité d'achat
     * @return la possibilité choisie
     */
    @Override
    public Integer[] choosePurchasePossibility(List<Integer[]> purchaseChoice) {
        return new Integer[0];
    }

    /**
     * Permet de choisir l'effet guildes des scientifiques a la fin de la partie
     *
     * @param wonderBoard la wonderboard du joueur
     * @return le type selectionner
     */
    @Override
    public ScientificType useScientificsGuildEffect(WonderBoard wonderBoard) {
        return null;
    }

    /**
     * Choisi une carte au hasard dans les carte defaussé
     *
     * @param deck
     * @return la carte choisie au hasard.
     */
    @Override
    public int chooseCard(Deck deck) {
        return 0;
    }


//    /**
//     * Le joueur dois choisir l'action qu'il veut faire pour ce tour
//     * @param currentDeck
//     * @return l'action choisie.
//     */
//    @Override
//    public Action chooseAction(Deck currentDeck) {
//        //TODO check
//        return destination.chooseAction(currentDeck);
//    }
//
//    /**
//     * Le joueur dois choisir si il veut faire du commerce pour construire
//     * si il veut pas il renvoie -1 sinon il renvoie l'index voulue
//     * @param purchaseChoice : la liste de possibilité d'achat
//     * @return la possibilité choisie
//     */
//    @Override
//    public int choosePurchasePossibility(List<Integer[]> purchaseChoice) {
//        //TODO check
//        return destination.choosePurchasePossibility(purchaseChoice);
//    }
//
//    /**
//     * Le joueur dois choisir l'effet guildes des scientifiques a la fin de la partie
//     * @return le type selectionner
//     */
//    @Override
//    public ScientificType useScientificsGuildEffect() {
//        //TODO check
//        return destination.useScientificsGuildEffect();
//    }
//
//    /**
//     * Le joueur dois choisir une carte au hasard dans les carte defaussé
//     * @param deffause la defausse
//     * @return la carte choisie.
//     */
//    @Override
//    public int chooseCard(Deck deffause) {
//        //TODO check
//        return destination.chooseCard(deffause);
//    }
}
