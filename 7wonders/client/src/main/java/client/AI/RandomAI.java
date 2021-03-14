package client.AI;

import commun.action.AbstractAction;
import commun.action.BuildAction;
import commun.action.BuildStepAction;
import commun.action.DiscardAction;
import commun.card.Deck;
import commun.effect.ScientificType;
import commun.utils.SingletonRandom;

import java.util.List;
import java.util.Random;

/** RandomAI est une IA qui effectue uniquement des choix aléatoires */
public class RandomAI extends AI
{
    private Random random;

    /** Constructeur */
    public RandomAI ()
    { this.random = SingletonRandom.getInstance(); }

    /** Constructeur Test */
    public RandomAI (Random random)
    { this.random = random; }

    /**
     * chooseAction permet de choisir une carte et de la jouer (BuildAction) ou non (DiscarAction)
     * @param deck La main courante du joueur
     * @return l'action choisie
     */
    public AbstractAction chooseAction (Deck deck) {
        int randomAction;
        int indexCard;
        boolean playJoker;

        randomAction = this.random.nextInt(2);
        indexCard = this.random.nextInt(deck.getLength());
        playJoker = this.random.nextBoolean();

        switch (randomAction) {
            case 0:
                return new DiscardAction(indexCard);

            case 1:
                return new BuildAction(indexCard, playJoker);

            default:
                return new BuildStepAction(indexCard);
        }
    }

    @Override
    public Integer[] choosePurchasePossibility(List<Integer[]> purchaseChoice) {
        boolean wantToDiscard = random.nextBoolean();

        if(wantToDiscard){//L'IA ne veut pas acheter chez ses voisins.
            return null;
        }
        else{
            int index = random.nextInt(purchaseChoice.size());
            return purchaseChoice.get(index);
        }
    }


    @Override
    public ScientificType useScientificsGuildEffect() {
        int choice = random.nextInt(3);
        switch (choice) {
            case 0:
                return ScientificType.GEOGRAPHY;

            case 1:
                return ScientificType.GEOMETRY;

            default:
                return ScientificType.LITERATURE;
        }
    }

    @Override
    public int chooseCard(Deck deck){
        int indexCard;

        indexCard = this.random.nextInt(deck.getLength());

        return  indexCard;
    }

    @Override
    public String toString()
    { return "RandomAI"; }
}
