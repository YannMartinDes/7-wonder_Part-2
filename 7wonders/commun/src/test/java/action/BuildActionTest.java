package action;

import commun.action.AbstractAction;
import commun.action.BuildAction;
import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.cost.CoinCost;
import commun.cost.MaterialCost;
import commun.effect.*;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import commun.wonderboard.WonderBoard;
import commun.wonderboard.WonderStep;
import io.cucumber.java8.De;
import log.GameLogger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class BuildActionTest
{

    Deck discard;
    Deck currentDeck;
    WonderBoard wonderBoard;
    WonderBoard leftN;
    WonderBoard rightN;
    AbstractAction action;

    @BeforeEach
    public void init(){
        GameLogger.verbose = false;
        discard = new Deck();
        currentDeck = new Deck();

        wonderBoard = new WonderBoard("wTest",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD,1))));
        leftN = new WonderBoard("wLTest",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,1))));
        rightN = new WonderBoard("wRTest",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.CLAY,1))));

        currentDeck.addCard(new Card("card1", CardType.CIVIL_BUILDING,new EarnWithWonderEffect(new EarnWithWonder(TargetType.BOTH_NEIGHTBOUR,2,2)),1,null));
    }

    @Test
    public void alreadyInWonderBuilding(){
        action = new BuildAction(0,false);
        wonderBoard.getBuilding().addCard(new Card("card1",null,null,0,null));


        assertEquals(0,discard.getLength());
        assertEquals(1,wonderBoard.getBuilding().getLength());
        assertEquals(1,currentDeck.getLength());

        action.playAction("test",currentDeck,wonderBoard,discard,leftN,rightN);

        //CARTE IDENTIQUES DEFAUSSE
        assertEquals(1,discard.getLength());
        assertEquals(1,wonderBoard.getBuilding().getLength());
        assertEquals(0,currentDeck.getLength());
    }

    @Test
    public void freeCard(){
        action = new BuildAction(1,false);
        Card card = new Card("card2",CardType.MANUFACTURED_PRODUCTS,new CoinEffect(0),0,null);
        currentDeck.addCard(card);

        assertEquals(3,wonderBoard.getCoin());
        assertEquals(0,discard.getLength());
        assertEquals(0,wonderBoard.getBuilding().getLength());
        assertEquals(2,currentDeck.getLength());

        action.playAction("test",currentDeck,wonderBoard,discard,leftN,rightN);
        action.finishAction("test",wonderBoard,discard,leftN,rightN,card,null);

        //CARTE GRATUITE ACHETEE
        assertEquals(0,discard.getLength());
        assertEquals(1,wonderBoard.getBuilding().getLength());
        assertEquals(3,wonderBoard.getCoin());
        assertEquals(1,currentDeck.getLength());
    }

    @Test
    public void costCardMoney(){
        action = new BuildAction(1,true);
        Card card = new Card("card2",CardType.MANUFACTURED_PRODUCTS,new CoinEffect(0),0,new CoinCost(2));
        currentDeck.addCard(card);

        assertEquals(3,wonderBoard.getCoin());
        assertEquals(0,discard.getLength());
        assertEquals(0,wonderBoard.getBuilding().getLength());
        assertEquals(2,currentDeck.getLength());

        action.playAction("test",currentDeck,wonderBoard,discard,leftN,rightN);
        action.finishAction("test",wonderBoard,discard,leftN,rightN,card,null);

        //PAYE ET ACHETE
        assertEquals(0,discard.getLength());
        assertEquals(1,wonderBoard.getBuilding().getLength());
        assertEquals(1,wonderBoard.getCoin());
        assertEquals(1,currentDeck.getLength());


        //-------------------------

        action = new BuildAction(1,true);
        card = new Card("card3",CardType.MANUFACTURED_PRODUCTS,new CoinEffect(0),0,new CoinCost(2));
        currentDeck.addCard(card);

        assertEquals(1,wonderBoard.getCoin());
        assertEquals(0,discard.getLength());
        assertEquals(1,wonderBoard.getBuilding().getLength());
        assertEquals(2,currentDeck.getLength());

        action.playAction("test",currentDeck,wonderBoard,discard,leftN,rightN);
        action.finishAction("test",wonderBoard,discard,leftN,rightN,card,null);

        //PAYE MAIS PAS ACHETE
        assertEquals(1,discard.getLength());
        assertEquals(1,wonderBoard.getBuilding().getLength());
        assertEquals(4,wonderBoard.getCoin());//DEFAUSSE DE LA CARTE
        assertEquals(1,currentDeck.getLength());


        //------- Joker ----
        action = new BuildAction(1,true);
        WonderStep wonderStep = new WonderStep(null,1,null);
        wonderStep.setHaveJoker(true);
        wonderStep.toBuild();
        wonderBoard.getWonderSteps().add(wonderStep);

        card = new Card("card4",CardType.MANUFACTURED_PRODUCTS,new CoinEffect(0),0,new CoinCost(9999));
        currentDeck.addCard(card);

        assertEquals(4,wonderBoard.getCoin());
        assertEquals(1,discard.getLength());
        assertEquals(1,wonderBoard.getBuilding().getLength());
        assertEquals(2,currentDeck.getLength());

        action.playAction("test",currentDeck,wonderBoard,discard,leftN,rightN);
        action.finishAction("test",wonderBoard,discard,leftN,rightN,card,null);

        //JOKER PERMET D'ACHETER
        assertEquals(1,discard.getLength());
        assertEquals(2,wonderBoard.getBuilding().getLength());
        assertEquals(4,wonderBoard.getCoin());//DEFAUSSE DE LA CARTE
        assertEquals(1,currentDeck.getLength());
    }


    @Test
    public void MaterialCost(){
        action = new BuildAction(1,false);
        //PEUT ACHETER
        Card card = new Card("card2",CardType.MANUFACTURED_PRODUCTS,new CoinEffect(0),0,new MaterialCost(new Material(MaterialType.WOOD,1)));
        currentDeck.addCard(card);

        assertEquals(3,wonderBoard.getCoin());
        assertEquals(0,discard.getLength());
        assertEquals(0,wonderBoard.getBuilding().getLength());
        assertEquals(2,currentDeck.getLength());

        action.playAction("test",currentDeck,wonderBoard,discard,leftN,rightN);
        action.finishAction("test",wonderBoard,discard,leftN,rightN,card,null);

        //CARTE ACHETEE
        assertEquals(0,discard.getLength());
        assertEquals(1,wonderBoard.getBuilding().getLength());
        assertEquals(3,wonderBoard.getCoin());
        assertEquals(1,currentDeck.getLength());

        //------------- CARTE TROP CHERE MAIS VOISIN PEUT AIDER ------

        action = new BuildAction(1,false);
        //PEUT ACHETER
        card = new Card("card3",CardType.MANUFACTURED_PRODUCTS,new CoinEffect(0),0,new MaterialCost(new Material(MaterialType.WOOD,1), new Material(MaterialType.STONE,1), new Material(MaterialType.CLAY,1)));
        currentDeck.addCard(card);
        wonderBoard.addCoin(4);

        assertEquals(7,wonderBoard.getCoin());
        assertEquals(0,discard.getLength());
        assertEquals(1,wonderBoard.getBuilding().getLength());
        assertEquals(2,currentDeck.getLength());

        assertEquals(3,leftN.getCoin());
        assertEquals(3,rightN.getCoin());
        assertEquals(0,action.getTradePossibility().size());

        action.playAction("test",currentDeck,wonderBoard,discard,leftN,rightN);

        assertEquals(0,discard.getLength());
        assertEquals(1,action.getTradePossibility().size());

        action.nextAction("test",currentDeck,wonderBoard,discard,leftN,rightN,new Integer[]{2,2});
        action.finishAction("test",wonderBoard,discard,leftN,rightN,card,null);

        //CARTE ACHETEE EN PAYANT LES VOISINS
        assertEquals(0,discard.getLength());
        assertEquals(2,wonderBoard.getBuilding().getLength());
        assertEquals(3,wonderBoard.getCoin());
        assertEquals(1,currentDeck.getLength());
        assertEquals(5,leftN.getCoin());
        assertEquals(5,rightN.getCoin());

        //-------- CARTE TROP TROP TROP CHER
        action = new BuildAction(1,false);
        //PEUT ACHETER
        card = new Card("card4",CardType.MANUFACTURED_PRODUCTS,new CoinEffect(0),0,new MaterialCost(new Material(MaterialType.WOOD,10), new Material(MaterialType.STONE,10), new Material(MaterialType.CLAY,10)));
        currentDeck.addCard(card);
        wonderBoard.addCoin(4);

        assertEquals(7,wonderBoard.getCoin());
        assertEquals(0,discard.getLength());
        assertEquals(2,wonderBoard.getBuilding().getLength());
        assertEquals(2,currentDeck.getLength());

        assertEquals(5,leftN.getCoin());
        assertEquals(5,rightN.getCoin());
        assertEquals(0,action.getTradePossibility().size());

        action.playAction("test",currentDeck,wonderBoard,discard,leftN,rightN);

        assertEquals(1,discard.getLength());
        assertEquals(0,action.getTradePossibility().size());

        action.finishAction("test",wonderBoard,discard,leftN,rightN,card,null);

        //CARTE TROP CHERE
        assertEquals(1,discard.getLength());
        assertEquals(2,wonderBoard.getBuilding().getLength());
        assertEquals(10,wonderBoard.getCoin());
        assertEquals(1,currentDeck.getLength());
        assertEquals(5,leftN.getCoin());
        assertEquals(5,rightN.getCoin());


        //----- LE JOKER C'EST COOL
        action = new BuildAction(1,true);
        //PEUT ACHETER
        card = new Card("card5",CardType.MANUFACTURED_PRODUCTS,new CoinEffect(0),0,new MaterialCost(new Material(MaterialType.WOOD,999), new Material(MaterialType.STONE,999), new Material(MaterialType.CLAY,999)));
        currentDeck.addCard(card);
        WonderStep wonderStep = new WonderStep(null,1,new CoinEffect(0));
        wonderStep.setHaveJoker(true);
        wonderStep.toBuild();
        wonderBoard.getWonderSteps().add(wonderStep);

        assertEquals(10,wonderBoard.getCoin());
        assertEquals(1,discard.getLength());
        assertEquals(2,wonderBoard.getBuilding().getLength());
        assertEquals(2,currentDeck.getLength());

        assertEquals(5,leftN.getCoin());
        assertEquals(5,rightN.getCoin());
        assertEquals(0,action.getTradePossibility().size());
        action.playAction("test",currentDeck,wonderBoard,discard,leftN,rightN);

        assertEquals(1,discard.getLength());
        assertEquals(0,action.getTradePossibility().size());

        action.finishAction("test",wonderBoard,discard,leftN,rightN,card,null);

        //CARTE TROP CHERE MAIS JOKER
        assertEquals(1,discard.getLength());
        assertEquals(3,wonderBoard.getBuilding().getLength());
        assertEquals(10,wonderBoard.getCoin());
        assertEquals(1,currentDeck.getLength());
        assertEquals(5,leftN.getCoin());
        assertEquals(5,rightN.getCoin());
    }

    @Test
    public void FinishActionTest(){
        //---- COIN EFFECT
        action = new BuildAction(1,false);
        Card card = new Card("card2",CardType.MANUFACTURED_PRODUCTS,new CoinEffect(5),0,null);
        currentDeck.addCard(card);

        assertEquals(3,wonderBoard.getCoin());
        assertEquals(0,wonderBoard.getBuilding().getLength());

        action.playAction("test",currentDeck,wonderBoard,discard,leftN,rightN);
        action.finishAction("test",wonderBoard,discard,leftN,rightN,card,null);

        assertEquals(8,wonderBoard.getCoin());
        assertEquals(1,wonderBoard.getBuilding().getLength());

        //----- MILLITARY POWER
        action = new BuildAction(1,false);
        card = new Card("card3",CardType.MANUFACTURED_PRODUCTS,new MilitaryEffect(5),0,null);
        currentDeck.addCard(card);

        assertEquals(0,wonderBoard.getMilitaryPower());
        assertEquals(1,wonderBoard.getBuilding().getLength());

        action.playAction("test",currentDeck,wonderBoard,discard,leftN,rightN);
        action.finishAction("test",wonderBoard,discard,leftN,rightN,card,null);

        assertEquals(5,wonderBoard.getMilitaryPower());
        assertEquals(2,wonderBoard.getBuilding().getLength());

        //----- EARN WITH WONDER
        WonderStep wonderStep = new WonderStep(null,1,null);
        wonderStep.toBuild();
        wonderBoard.getWonderSteps().add(wonderStep);
        leftN.getWonderSteps().add(wonderStep);
        rightN.getWonderSteps().add(wonderStep);

        action = new BuildAction(1,false);
        card = new Card("card4",CardType.MANUFACTURED_PRODUCTS,new EarnWithWonderEffect(new EarnWithWonder(TargetType.ME_AND_NEIGHTBOUR,2,4)),0,null);
        currentDeck.addCard(card);

        assertEquals(2,wonderBoard.getBuilding().getLength());
        assertEquals(8,wonderBoard.getCoin());

        action.playAction("test",currentDeck,wonderBoard,discard,leftN,rightN);
        action.finishAction("test",wonderBoard,discard,leftN,rightN,card,null);

        assertEquals(14,wonderBoard.getCoin());
        assertEquals(3,wonderBoard.getBuilding().getLength());

        //----- EARN WITH CARD
        action = new BuildAction(1,false);
        card = new Card("card5",CardType.MANUFACTURED_PRODUCTS,new EarnWithCardEffect(new EarnWithCard(2,2,TargetType.ME_AND_NEIGHTBOUR, CardType.MANUFACTURED_PRODUCTS)),0,null);
        currentDeck.addCard(card);

        assertEquals(14,wonderBoard.getCoin());
        assertEquals(3,wonderBoard.getBuilding().getLength());

        action.playAction("test",currentDeck,wonderBoard,discard,leftN,rightN);
        action.finishAction("test",wonderBoard,discard,leftN,rightN,card,null);
        
        assertEquals(22,wonderBoard.getCoin());
        assertEquals(4,wonderBoard.getBuilding().getLength());
    }
}
