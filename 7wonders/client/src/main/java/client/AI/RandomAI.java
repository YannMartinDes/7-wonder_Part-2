package client.AI;

import commun.action.ActionType;
import commun.card.Deck;
import commun.action.Action;
import commun.effect.EffectList;

import java.util.Random;

/** RandomAI est une IA qui effectue uniquement des choix aléatoires */
public class RandomAI
        implements client.AI.AI
{
    private Random random;

    /** Constructeur */
    public RandomAI ()
    { this.random = new Random(); }

    /**
     * chooseAction permet de choisir une carte et de la jouer (BuildAction) ou non (DiscarAction)
     * @param deck La main courante du joueur
     * @return l'action choisie
     */
    public Action chooseAction (Deck deck,  int playerCoins, EffectList playerEffects)
    {
        boolean discardOrBuild;
        int indexCard;

        discardOrBuild = this.random.nextBoolean();
        indexCard = this.random.nextInt(deck.getLength());

        if(discardOrBuild == true)
        { return new Action(ActionType.DISCARD,indexCard); }
        else
        { return new Action(ActionType.BUILD,indexCard); }
    }
}
