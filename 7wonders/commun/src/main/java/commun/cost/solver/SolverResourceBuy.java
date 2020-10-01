package commun.cost.solver;

import commun.card.Card;
import commun.card.Deck;
import commun.effect.AddingMaterialEffet;
import commun.material.Material;
import commun.wonderboard.WonderBoard;

import java.util.ArrayList;
import java.util.List;

public class SolverResourceBuy {

    public Deck filterDeckOnRessource(Deck deck){
        Deck oneRessourceCard = new Deck();
        for(Card card : deck){
            if(card.getCardEffect().getMaterial() != null){
                oneRessourceCard.addCard(card);
            }
        }
        return oneRessourceCard;
    }

    public Deck filterDeckChoiseRessource(Deck deck){
        Deck choiceRessourceCard = new Deck();
        for(Card card : deck){
            if(card.getCardEffect().getChoiceMaterial() !=null){
                choiceRessourceCard.addCard(card);
            }
        }
        return choiceRessourceCard;
    }

//    public ArrayList<TradeChoice> computeOnRessourceTradeChoice(Deck deck){
//        Deck deckOneRessource = filterDeckOnRessource(deck);
//        for(Card card : deckOneRessource){
//            Material materialEffet = card.getCardEffect().getMaterial();
//            //TODO
//            materialEffet;
//        }
//    }


}
