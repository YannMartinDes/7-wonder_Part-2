package client.AI;

import commun.action.AbstractAction;
import commun.action.BuildAction;
import commun.action.BuildStepAction;
import commun.action.DiscardAction;
import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.effect.EffectList;
import commun.effect.ScientificType;
import commun.wonderboard.WonderBoard;

import java.util.ArrayList;
import java.util.List;

/** RandomAI est une IA qui effectue uniquement des choix aléatoires */
public class FirstAI extends AI
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
    public AbstractAction chooseAction (Deck deck, int playerCoins, EffectList playerEffects)
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
                return new BuildAction(deck.indexOf(affordableCards.get(i)), true);
            }
        }

        // Ressources : RAW_MATERIALS
        for (int i = 0; i < affordableCards.size(); i++)
        {
            if (affordableCards.get(i).getType() == CardType.RAW_MATERIALS)
            {
                return new BuildAction(deck.indexOf(affordableCards.get(i)), true);
            }
        }

        // Militaire : MILITARY_BUILDINGS
        for (int i = 0; i < affordableCards.size(); i++)
        {
            if (affordableCards.get(i).getType() == CardType.MILITARY_BUILDINGS)
            {
                return new BuildAction( deck.indexOf(affordableCards.get(i)), true);
            }
        }

        if(playerCoins < 10 ){
            return new DiscardAction( 0);
        }

        // Else
            return new BuildStepAction( 0);

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

    /**
     *
     * @param wonderBoard la wonderboard du joueur
     * @return le typeScientifique qui'il a en majorité
     */
    @Override
    public ScientificType useScientificsGuildEffect(WonderBoard wonderBoard) {
        ArrayList<ScientificType> scientificTypes = new ArrayList<>();
        scientificTypes.add(ScientificType.GEOGRAPHY);
        scientificTypes.add(ScientificType.GEOMETRY);
        scientificTypes.add(ScientificType.LITERATURE);
        ArrayList<Integer> occurByType= new ArrayList<>();
        occurByType.add(0);
        occurByType.add(0);
        occurByType.add(0);
        int occurScientificType = 0;
        for (Card card : wonderBoard.getBuilding()) {
            if (card.getCardEffect().getScientificType() == ScientificType.GEOGRAPHY ) occurByType.set(0,occurByType.get(0) + 1 );
            if (card.getCardEffect().getScientificType() == ScientificType.GEOMETRY ) occurByType.set(1,occurByType.get(1) + 1 );
            if (card.getCardEffect().getScientificType() == ScientificType.LITERATURE ) occurByType.set(2,occurByType.get(2) + 1 );
        }
        for (int i = 0 ; i < 3 ;i++){
            if (occurByType.get(i) >  occurByType.get(occurScientificType)) occurScientificType = i ;

        }
        //Else
        return scientificTypes.get(occurScientificType);
    }

    @Override
    public int chooseCard(Deck deck){
        // Matiéres premiéres
        for (Card card : deck
        ) {
            if (card.getType() == CardType.RAW_MATERIALS) {
                return deck.indexOf(card);
            }
        }
        // Scientifique
        for (Card card : deck
        ) {
            if (card.getType() == CardType.SCIENTIFIC_BUILDINGS) {
                return deck.indexOf(card);
            }
        }
        // Batiments civils
        for (Card card : deck
        ) {
            if (card.getType() == CardType.CIVIL_BUILDING) {
                return deck.indexOf(card);
            }
        }
        // Militaire
        for (Card card : deck
        ) {
            if (card.getType() == CardType.MILITARY_BUILDINGS) {
                return deck.indexOf(card);
            }
        }

        return  0;

    }

    @Override
    public String toString()
    { return "FirstAI"; }
}
