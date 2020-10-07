package client.AI;

import commun.action.ActionType;
import commun.card.CardType;
import commun.card.Deck;
import commun.action.Action;
import commun.effect.EffectList;
import commun.wonderboard.WonderBoard;
import commun.wonderboard.WonderStep;

import java.util.List;

/** RandomAI est une IA qui effectue uniquement des choix aléatoires */
public class FirstAI implements client.AI.AI
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
    @Override
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
                return new Action(ActionType.BUILD, deck.indexOf(affordableCards.get(i)), true);
            }
        }

        // Ressources : RAW_MATERIALS
        for (int i = 0; i < affordableCards.size(); i++)
        {
            if (affordableCards.get(i).getType() == CardType.RAW_MATERIALS)
            {
                return new Action(ActionType.BUILD, deck.indexOf(affordableCards.get(i)), true);
            }
        }

        // Militaire : MILITARY_BUILDINGS
        for (int i = 0; i < affordableCards.size(); i++)
        {
            if (affordableCards.get(i).getType() == CardType.MILITARY_BUILDINGS)
            {
                return new Action(ActionType.BUILD, deck.indexOf(affordableCards.get(i)), true);
            }
        }

        if(playerCoins < 10 ){
            return new Action(ActionType.DISCARD, 0, false);
        }

        // Else
            return new Action(ActionType.BUILD_STAGE_WONDER, 0, false);

    }

    @Override
    public Integer[] choosePurchasePossibility(List<Integer[]> purchaseChoice) {
        int index = 0;//index du choix
        int cost = purchaseChoice.get(0)[0] + purchaseChoice.get(0)[1];//Prix à payer
        for(int i= 1; i< purchaseChoice.size();i++ ){
            Integer[] tab = purchaseChoice.get(i);
            if((tab[0] + tab[1]) < cost){//On a trouver un choix moins chere
                index = i;
                cost = (tab[0] + tab[1]);
            }
        }
        return purchaseChoice.get(index);//La possibilité la moins chere.
    }


    //todo : mettre une strat qui lui permet de choisir la meilleur carte parmi les defaussés
    @Override
    public int chooseCard(Deck deck){
        int indexCard = 0;
        return  indexCard;
    }
}
