package client.AI;

import commun.action.ActionType;
import commun.card.Deck;
import commun.action.Action;
import commun.effect.EffectList;
import commun.wonderboard.WonderStep;

import java.util.List;
import java.util.Random;

/** RandomAI est une IA qui effectue uniquement des choix aléatoires */
public class RandomAI implements client.AI.AI
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
    public Action chooseAction (Deck deck, int playerCoins, EffectList playerEffects) {
        int randomAction;
        int indexCard;

        randomAction = this.random.nextInt(2);
        indexCard = this.random.nextInt(deck.getLength());

        switch (randomAction) {
            case 0:
                return new Action(ActionType.DISCARD, indexCard);

            case 1:
                return new Action(ActionType.BUILD, indexCard);

            default:
                return new Action(ActionType.BUILD_STAGE_WONDER, indexCard);
        }
    }
}
