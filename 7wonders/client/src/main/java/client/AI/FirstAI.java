package client.AI;

import commun.action.ActionType;
import commun.card.CardType;
import commun.card.Deck;
import commun.action.Action;
import commun.effect.EffectList;
import commun.wonderboard.WonderBoard;

/** RandomAI est une IA qui effectue uniquement des choix aléatoires */
public class FirstAI
        implements client.AI.AI
{
    private WonderBoard wonderBoard;

    /** Constructeur */
    public FirstAI ()
    {
        this.wonderBoard = new WonderBoard("Wonderboard", null);
    }

    /**
     * chooseAction permet de choisir une carte et de la jouer (BuildAction) ou non (DiscarAction)
     * @param deck La main courante du joueur
     * @return l'action choisie
     */
    public Action chooseAction (Deck deck, int playerCoins, EffectList playerEffects)
    {
        boolean discardOrBuild = false;
        int indexOfCard;
        Deck affordableCards;

        EffectList cardEffects = new EffectList();
        affordableCards = new Deck();
        for (int i = 0; i < deck.getLength(); i++)
        {
            cardEffects.add(deck.get(i).getCardEffect());
        }
        for (int i = 0; i < deck.getLength(); i++)
        {
            // Si la carte est achetable:
            // Si assez d'argent OU
            // Si assez de ressources
            if (deck.get(i).getCostCard() != null && deck.get(i).getCostCard().canBuyCard(playerCoins) ||
                deck.get(i).getCostCard() != null && deck.get(i).getCardEffect() != null && deck.get(i).getCostCard().canBuyCard(playerEffects))
            {
                affordableCards.add(deck.get(i));
            }
        }

        /* Priorite = P
        * P1 : Points de victoire
        * P2 : Ressources
        * P3 : Militaire
        * Else : Defausse
        * */

        // Points de victoire: CIVIL_BUILDING
        for (int i = 0; i < affordableCards.size(); i++)
        {
            if (affordableCards.get(i).getType() == CardType.CIVIL_BUILDING)
            {
                return new Action(ActionType.BUILD, deck.indexOf(affordableCards.get(i)));
            }
        }

        // Ressources : RAW_MATERIALS
        for (int i = 0; i < affordableCards.size(); i++)
        {
            if (affordableCards.get(i).getType() == CardType.RAW_MATERIALS)
            {
                return new Action(ActionType.BUILD, deck.indexOf(affordableCards.get(i)));
            }
        }

        // Militaire : MILITARY_BUILDINGS
        for (int i = 0; i < affordableCards.size(); i++)
        {
            if (affordableCards.get(i).getType() == CardType.MILITARY_BUILDINGS)
            {
                return new Action(ActionType.BUILD, deck.indexOf(affordableCards.get(i)));
            }
        }

        // Else
        return new Action(ActionType.DISCARD, 0);
    }
}
