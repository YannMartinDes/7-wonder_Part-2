package action;

import commun.action.AbstractAction;
import commun.action.BuildStepAction;
import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.cost.MaterialCost;
import commun.effect.*;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import commun.request.RequestToPlayer;
import commun.wonderboard.WonderBoard;
import commun.wonderboard.WonderStep;
import log.GameLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

public class BuildStepActionTest {

    Deck discard;
    Deck currentDeck;
    WonderBoard wonderBoard;
    WonderBoard leftN;
    WonderBoard rightN;
    AbstractAction action;

    @Mock
    RequestToPlayer mockAI = Mockito.mock(RequestToPlayer.class);

    @BeforeEach
    public void init(){
        GameLogger.verbose = false;
        discard = new Deck();
        currentDeck = new Deck();

        wonderBoard = new WonderBoard("wTest",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD,1))));
        leftN = new WonderBoard("wLTest",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,1))));
        rightN = new WonderBoard("wRTest",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.CLAY,1))));

        //Gratuite.
        currentDeck.addCard(new Card("card1", CardType.CIVIL_BUILDING,new EarnWithWonderEffect(new EarnWithWonder(TargetType.BOTH_NEIGHTBOUR,2,2)),1,null));
    }


    @Test
    public void noMoreStep(){

        action = new BuildStepAction(0);

        wonderBoard.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD,1)),1));
        wonderBoard.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD,2)),2));
        wonderBoard.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD,3)),3));

        for(WonderStep wonderStep : wonderBoard.getWonderSteps()){
            wonderStep.toBuild();
        }

        assertEquals(null,wonderBoard.getCurrentStep());//Plus d'étape à constuire.
        assertEquals(3,wonderBoard.getCoin());
        assertEquals(0,discard.getLength());
        assertEquals(1,currentDeck.getLength());

        action.playAction("test",currentDeck,wonderBoard,discard,leftN,rightN);

        //La carte à été défaussée
        assertEquals(6,wonderBoard.getCoin());
        assertEquals(1,discard.getLength());
        assertEquals(0,currentDeck.getLength());
    }

    @Test
    public void cantBuyAtAll(){
        action = new BuildStepAction(0);

        wonderBoard.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD,9)),1));
        wonderBoard.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD,2)),2));
        wonderBoard.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD,3)),3));

        assertEquals(1,wonderBoard.getCurrentStep().getStepNumber());
        assertEquals(3,wonderBoard.getCoin());
        assertEquals(0,discard.getLength());
        assertEquals(1,currentDeck.getLength());

        action.playAction("test",currentDeck,wonderBoard,discard,leftN,rightN);

        //La carte à été défaussée car impossible de l'acheter
        assertEquals(1,wonderBoard.getCurrentStep().getStepNumber());
        assertEquals(6,wonderBoard.getCoin());
        assertEquals(1,discard.getLength());
        assertEquals(0,currentDeck.getLength());
    }

    @Test
    public void canBuy(){
        action = new BuildStepAction(0);

        wonderBoard.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD,1)),1));
        wonderBoard.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.CLAY,2)),2));
        wonderBoard.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.STONE,3)),3));

        assertEquals(1,wonderBoard.getCurrentStep().getStepNumber());
        assertEquals(3,wonderBoard.getCoin());
        assertEquals(0,discard.getLength());
        assertEquals(1,currentDeck.getLength());

        action.playAction("test",currentDeck,wonderBoard,discard,leftN,rightN);

        //La carte à été achetée
        assertEquals(2,wonderBoard.getCurrentStep().getStepNumber());
        assertEquals(true,wonderBoard.getWonderSteps().get(0).getBuilt());
        assertEquals(3,wonderBoard.getCoin());
        assertEquals(0,discard.getLength());
        assertEquals(0,currentDeck.getLength());
    }

    @Test
    public void canBuyWithNeigthbour(){
        action = new BuildStepAction(0);

        wonderBoard.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD,1),
                new Material(MaterialType.CLAY,1),new Material(MaterialType.STONE,1)),1));
        wonderBoard.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.CLAY,2)),2));
        wonderBoard.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.STONE,3)),3));

        wonderBoard.addCoin(1);

        assertEquals(1,wonderBoard.getCurrentStep().getStepNumber());
        assertEquals(4,wonderBoard.getCoin());
        assertEquals(3,leftN.getCoin());
        assertEquals(3,rightN.getCoin());
        assertEquals(0,discard.getLength());
        assertEquals(1,currentDeck.getLength());

        action.playAction("test",currentDeck,wonderBoard,discard,leftN,rightN);
        assertEquals(1,action.getTradePossibility().size());

        action.nextAction("test",currentDeck,wonderBoard,discard,leftN,rightN,new Integer[]{2,2});

        //La carte à été achetée avec les voisins.
        assertEquals(2,wonderBoard.getCurrentStep().getStepNumber());
        assertEquals(true,wonderBoard.getWonderSteps().get(0).getBuilt());
        assertEquals(5,leftN.getCoin());
        assertEquals(5,rightN.getCoin());
        assertEquals(0,wonderBoard.getCoin());
        assertEquals(0,discard.getLength());
        assertEquals(0,currentDeck.getLength());
    }


    @Test
    public void FinishActionNormalEffect(){
        action = new BuildStepAction(0);

        currentDeck.addCard(new Card("card1", CardType.CIVIL_BUILDING,new EarnWithWonderEffect(new EarnWithWonder(TargetType.BOTH_NEIGHTBOUR,2,2)),1,null));
        currentDeck.addCard(new Card("card1", CardType.CIVIL_BUILDING,new EarnWithWonderEffect(new EarnWithWonder(TargetType.BOTH_NEIGHTBOUR,2,2)),1,null));
        currentDeck.addCard(new Card("card1", CardType.CIVIL_BUILDING,new EarnWithWonderEffect(new EarnWithWonder(TargetType.BOTH_NEIGHTBOUR,2,2)),1,null));

        wonderBoard.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD,1)),1,new CoinEffect(5)));
        wonderBoard.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD,1)),2,new MilitaryEffect(5)));
        wonderBoard.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD,1)),3));
        wonderBoard.getWonderSteps().get(2).setPlayDiscardedCard(true);
        wonderBoard.getWonderSteps().add(new WonderStep(new MaterialCost(new Material(MaterialType.WOOD,1)),4));
        wonderBoard.getWonderSteps().get(3).setCopyNeighborGuild(true);

        assertEquals(3,wonderBoard.getCoin());
        assertEquals(0,wonderBoard.getMilitaryPower());

        action.playAction("test",currentDeck,wonderBoard,discard,leftN,rightN);
        action.finishAction("test",wonderBoard,discard,leftN,rightN,null,mockAI);

        //Effet appliqué
        assertEquals(8,wonderBoard.getCoin());

        action.playAction("test",currentDeck,wonderBoard,discard,leftN,rightN);
        action.finishAction("test",wonderBoard,discard,leftN,rightN,null,mockAI);

        //Effet appliqué
        assertEquals(5,wonderBoard.getMilitaryPower());


        //EFFET PIOCHE DEFAUSSE
        discard.addCard(new Card("card1", CardType.CIVIL_BUILDING,new CoinEffect(2),1,null));

        assertEquals(1,discard.getLength());
        assertEquals(8,wonderBoard.getCoin());
        assertEquals(0,wonderBoard.getBuilding().getLength());
        Mockito.when(mockAI.chooseCard(any(Deck.class))).thenReturn(0);

        action.playAction("test",currentDeck,wonderBoard,discard,leftN,rightN);
        action.finishAction("test",wonderBoard,discard,leftN,rightN,null,mockAI);

        //Effet appliqué
        assertEquals(10,wonderBoard.getCoin());//Effet de la carte appliqué aussi
        assertEquals(0,discard.getLength());
        assertEquals(1,wonderBoard.getBuilding().getLength());

        //EFFET COPIE CARTE GUILDE
        leftN.getBuilding().addCard(new Card("GuildCard",CardType.GUILD_BUILDINGS,new CoinEffect(0),3,null));

        action.playAction("test",currentDeck,wonderBoard,discard,leftN,rightN);
        action.finishAction("test",wonderBoard,discard,leftN,rightN,null,mockAI);

        //Effet appliqué et carte copiée
        assertEquals(2,wonderBoard.getBuilding().getLength());
        assertEquals("GuildCard",wonderBoard.getBuilding().getCard(1).getName());
    }
}
