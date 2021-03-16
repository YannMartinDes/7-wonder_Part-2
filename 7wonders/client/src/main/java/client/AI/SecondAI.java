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
import commun.player.Player;

import java.util.ArrayList;
import java.util.List;

public class SecondAI extends AI
{

    @Override
    public AbstractAction chooseAction(Deck deck) {
        Player me = super.getMe();

        Deck affordableCards = buyableCard(deck,me.getWonderBoard().getCoin(),me.getWonderBoard().getAllEffects() );

        int age = deck.get(0).getAge();

        if (age == 1){
            // Matières premières
            for (Card card: affordableCards
                 ) {
                if(card.getType() == CardType.RAW_MATERIALS){
                    return new BuildAction(deck.indexOf(card), true);
                }
            }
            // Bâtiments commerciaux
            for (Card card: affordableCards
            ) {
                if(card.getType() == CardType.COMMERCIAL_BUILDINGS){
                    return new BuildAction(deck.indexOf(card), true);
                }
            }
            // Produits manufacturés
            for (Card card: affordableCards
            ) {
                if(card.getType() == CardType.MANUFACTURED_PRODUCTS){
                    return new BuildAction(deck.indexOf(card), true);
                }
            }
            return new BuildStepAction( 0);
        }
        if (age == 2) {
            // Militaire
            for (Card card : affordableCards
            ) {
                if (card.getType() == CardType.MILITARY_BUILDINGS) {
                    return new BuildAction(deck.indexOf(card), true);
                }
            }
            //Construire une etape
            for (Card card : affordableCards
            ) {
                if (card.getType() == CardType.MANUFACTURED_PRODUCTS || card.getType() == CardType.RAW_MATERIALS) {
                    return new BuildStepAction(deck.indexOf(card));

                }
            }
            // Points de victoire
            for (Card card : affordableCards
            ) {
                if (card.getType() == CardType.CIVIL_BUILDING) {
                    return new BuildAction(deck.indexOf(card), true);
                }
            }
            return new BuildStepAction(0);
        }
        if (age == 3){
            //1er choix :  Points de victoire
            for(Card card: affordableCards ) {
                if(card.getType() == CardType.CIVIL_BUILDING){
                    return new BuildAction(deck.indexOf(card), true);
                }
            }
            //2er choix : Guild
            for (Card card: affordableCards
            ) {
                if(card.getType() == CardType.GUILD_BUILDINGS){
                    return new BuildAction(deck.indexOf(card), true);
                }
            }
            //3er choix : Scientifique
            for (Card card: affordableCards
            ) {
                if(card.getType() == CardType.SCIENTIFIC_BUILDINGS){
                    return new BuildAction(deck.indexOf(card), true);
                }
            }
            return new DiscardAction( 0);
        }
        return new DiscardAction( 0);
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
     * @return le typeScientifique qui manque à la collection qu'il posséde
     */

    @Override
    public ScientificType useScientificsGuildEffect() {
        ArrayList<ScientificType> scientificTypes = new ArrayList<>();
        scientificTypes.add(ScientificType.GEOGRAPHY);
        scientificTypes.add(ScientificType.GEOMETRY);
        scientificTypes.add(ScientificType.LITERATURE);
        ArrayList<Integer> occurByType= new ArrayList<>();
        occurByType.add(0);
        occurByType.add(0);
        occurByType.add(0);

        for (Card card : super.getMe().getWonderBoard().getBuilding()) {
            if (card.getCardEffect().getScientificType() == ScientificType.GEOGRAPHY ) occurByType.set(0,occurByType.get(0) + 1 );
            if (card.getCardEffect().getScientificType() == ScientificType.GEOMETRY ) occurByType.set(1,occurByType.get(1) + 1 );
            if (card.getCardEffect().getScientificType() == ScientificType.LITERATURE ) occurByType.set(2,occurByType.get(2) + 1 );
        }
        for (int i = 0 ; i < 3 ;i++){
            if (occurByType.get(i)> 0 && occurByType.get((i+1)%3)> 0 ) return scientificTypes.get((i+2)%3);
            if (occurByType.get(i) > occurByType.get((i+1)%3) && occurByType.get((i+1)%3)  > occurByType.get((i+2)%3))   return scientificTypes.get((i+2)%3);
        }
        //Else
        return ScientificType.GEOMETRY;

    }

    @Override
    public int chooseCard(Deck deck) {
        // Points de victoire
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
        // Scientifique
        for (Card card : deck
        ) {
            if (card.getType() == CardType.SCIENTIFIC_BUILDINGS) {
                return deck.indexOf(card);
            }
        }
        return  0;
    }

    private Deck buyableCard(Deck deck,int playerCoins,EffectList playerEffects){
        Deck affordableCards = new Deck();
        for (Card card : deck)
        {
            // Si la carte est achetable:

            if (card.getCostCard() != null && card.getCostCard().canBuyCard(playerCoins) ||
                    card.getCostCard() != null && card.getCardEffect() != null && card.getCostCard().canBuyCard(playerEffects))
            {
                affordableCards.add(card);
            }
        }
        return affordableCards;
    }
    @Override
    public String toString()
    { return "SecondAI"; }
}
