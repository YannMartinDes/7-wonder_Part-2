package commun.request;

import commun.action.AbstractAction;
import commun.card.Deck;
import commun.effect.ScientificType;
import log.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * Permet de verifier que l'action est correct
 */
public class RequestPlayerActionCheck implements RequestToPlayer {
    private RequestToPlayer ia;

    public RequestPlayerActionCheck(RequestToPlayer ia){
        this.ia = ia;
    }

    /**
     * Choisi une carte au hasard dans un deck et l'action qu'elle veut effectuer sur cette carte
     *
     * @param deck le deck
     * @return la carte choisie au hasard.
     */
    @Override
    public AbstractAction chooseAction(Deck deck) {
        AbstractAction action;
        boolean correct = false;

        do {
            action = ia.chooseAction(deck);
            if(action == null|| action.getIndexOfCard()<0 || action.getIndexOfCard()>=deck.size()){
                Logger.logger.error("useScientificsGuildEffect: L'Effet choisi n'est pas correcte (null)");
            }else {
                correct = true;
            }
        }while (!correct);

        return action;
    }

    /**
     * Choisi une possibilité d'achat chez les voisins selon une liste de possibilité d'achat.
     *
     * @param purchaseChoice : la liste de possibilité d'achat
     * @return la possibilité choisie
     */
    @Override
    public Integer[] choosePurchasePossibility(List<Integer[]> purchaseChoice) {
        boolean correctChoice = false;
        Integer[] choice = null;
        while(!correctChoice){
            choice = ia.choosePurchasePossibility(purchaseChoice);

            if(choice == null) return null; //le joueur renonce a l'achat

            for(int i =0; i<purchaseChoice.size(); i++){
                correctChoice = Arrays.equals(choice,purchaseChoice.get(i));
                if(correctChoice==true) return choice; //le choix est correct on le renvoie
            }
            if(!correctChoice){
                Logger.logger.error("choosePurchasePossibility: Cette solution n'est pas valide");
            }
        }
        return null;
    }

    /**
     * Permet de choisir l'effet guildes des scientifiques a la fin de la partie
     *
     * @return le type selectionner
     */
    @Override
    public ScientificType useScientificsGuildEffect() {
        ScientificType choice = null;
        boolean correct = false;
        do{
            choice= ia.useScientificsGuildEffect();
            if(choice==null){
                Logger.logger.error("useScientificsGuildEffect: L'Effet choisi n'est pas correcte (null)");
            }else {
                correct = true;
            }
        }while (!correct);
        return choice;
    }

    /**
     * Choisi une carte au hasard dans les carte defaussé
     *
     * @param deck
     * @return la carte choisie au hasard.
     */
    @Override
    public int chooseCard(Deck deck) {
        int index = -1;
        boolean correct = false;
        do {
            index = ia.chooseCard(deck);
            if(index<0 || index>=deck.size()){
                Logger.logger.error("chooseCard: L'index de la carte choisie dans la defausse n'est pas un index correct");
            }else {
                correct = true;
            }

        }while (!correct);
        return index;
    }

    //pour les test
    public RequestToPlayer getIa() {
        return ia;
    }


    @Override
    public String toString() {
        return ia.toString();
    }

}
