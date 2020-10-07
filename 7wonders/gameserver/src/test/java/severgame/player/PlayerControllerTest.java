package severgame.player;

import client.AI.AI;
import commun.action.Action;
import commun.action.ActionType;
import commun.action.FinalAction;
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
import log.GameLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import servergame.player.PlayerController;



import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerControllerTest {

    @Mock
    AI ai = Mockito.mock(AI.class);

    PlayerController playerController = new PlayerController(ai);
    Deck deck = new Deck();
    Deck discardDeck = new Deck();
    WonderBoard wonderBoard;
    int sizeDeck;

    @BeforeEach
    public void prepare(){
        GameLogger.verbose = false;

        discardDeck = new Deck();

        deck = new Deck();
        deck.addCard(new Card("test1",CardType.CIVIL_BUILDING,new CoinEffect(0),1,new CoinCost(0)));//DiscardCard
        deck.addCard(new Card("test2",CardType.CIVIL_BUILDING,new CoinEffect(0),1,new CoinCost(2)));//CoinCost
        deck.addCard(new Card("test3",CardType.CIVIL_BUILDING,new CoinEffect(0),1,new CoinCost(20)));//trop cher CoinCost
        deck.addCard(new Card("test4",CardType.CIVIL_BUILDING,new CoinEffect(0),1,new MaterialCost(new Material(MaterialType.STONE,1))));//MaterialCost
        deck.addCard(new Card("test5",CardType.CIVIL_BUILDING,new CoinEffect(0),1,new MaterialCost(new Material(MaterialType.WOOD,1))));//trop cher MaterialCost

        sizeDeck = deck.getLength();

        wonderBoard = new WonderBoard("testWonder",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,1))));
        wonderBoard.addCoin(5);//8 pièces au total car 3 de base.
    }

    @Test
    public void DiscardActionTest ()
    {
        int index = 0;
        Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(new Action(ActionType.DISCARD,index, true));

        assertEquals(0,discardDeck.getLength());//Rien dans la défausse.
        assertEquals(sizeDeck,deck.getLength());
        assertEquals(0,wonderBoard.getBuilding().getLength());

        Card playedCard = deck.getCard(0);

        playerController.chooseAction(deck, 0, new EffectList());
        playerController.playAction(deck,wonderBoard);
        playerController.finishAction("test",wonderBoard,discardDeck,null,null);

        assertEquals(1,discardDeck.getLength());
        assertEquals(playedCard,discardDeck.getCard(0));//Elle se retrouve dans la défausse.
        assertEquals(sizeDeck-1,deck.getLength());
        assertEquals(0,wonderBoard.getBuilding().getLength());
        assertEquals(11,wonderBoard.getCoin());
    }

    @Test
    public void BuildCoin(){
        int index = 1;
        Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(new Action(ActionType.BUILD,index, true));

        assertEquals(0,discardDeck.getLength());//Rien dans la défausse.
        assertEquals(0,wonderBoard.getBuilding().getLength());
        assertEquals(sizeDeck,deck.getLength());

        Card playedCard = deck.getCard(index);

        playerController.chooseAction(deck,0 , new EffectList());
        playerController.playAction(deck,wonderBoard);
        playerController.finishAction("test",wonderBoard,discardDeck,null,null);

        assertEquals(0,discardDeck.getLength());
        assertEquals(sizeDeck-1,deck.getLength());
        assertEquals(1,wonderBoard.getBuilding().getLength());
        assertEquals(playedCard,wonderBoard.getBuilding().getCard(0));
        assertEquals(6,wonderBoard.getCoin());

        //CARTE TROP CHERE
        playedCard = deck.getCard(index);
        Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(new Action(ActionType.BUILD,index, true));
        playerController.playAction(deck,wonderBoard);
        playerController.finishAction("test",wonderBoard,discardDeck,null,null);

        assertEquals( 0,discardDeck.getLength());
        assertEquals(playedCard,discardDeck.getCard(0));//Elle se retrouve dans la défausse.
        assertEquals(1,wonderBoard.getBuilding().getLength());//Pas de changement
        assertEquals(9,wonderBoard.getCoin());//+3 de la défausse.
        assertEquals(sizeDeck-2,deck.getLength());//La carte est supprimée

    }

    @Test
    public void BuildMaterial(){
        int index = 3;
        Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(new Action(ActionType.BUILD,index, true));

        assertEquals(0,discardDeck.getLength());//Rien dans la défausse.
        assertEquals(0,wonderBoard.getBuilding().getLength());
        assertEquals(sizeDeck,deck.getLength());

        Card playedCard = deck.getCard(index);

        playerController.chooseAction(deck, 0, new EffectList());
        playerController.playAction(deck,wonderBoard);
        playerController.finishAction("test",wonderBoard,discardDeck,null,null);

        assertEquals(0,discardDeck.getLength());
        assertEquals(sizeDeck-1,deck.getLength());
        assertEquals(1,wonderBoard.getBuilding().getLength());
        assertEquals(playedCard,wonderBoard.getBuilding().getCard(0));
        assertEquals(8,wonderBoard.getCoin());

        //CARTE TROP CHERE
        playedCard = deck.getCard(index);
        Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(new Action(ActionType.BUILD,index, true));
        playerController.playAction(deck,wonderBoard);
        playerController.finishAction("test",wonderBoard,discardDeck,null,null);

        assertEquals(1,discardDeck.getLength());
        assertEquals(playedCard,discardDeck.getCard(0));//Elle se retrouve dans la défausse.
        assertEquals(1,wonderBoard.getBuilding().getLength());//Pas de changement
        assertEquals(11,wonderBoard.getCoin());//+3 de la défausse.
        assertEquals(sizeDeck-2,deck.getLength());//La carte est supprimée

    }

    @Test
    public void finishActionTest() {
        playerController = new PlayerController(ai);
        FinalAction finalAction = new FinalAction();

        //COIN TO PAY ACTION
        assertEquals(8,wonderBoard.getCoin());

        finalAction.setCoinToPay(3);
        Whitebox.setInternalState(playerController, "finalAction", finalAction);
        playerController.finishAction("test",wonderBoard,null,null,null);

        assertEquals(5,wonderBoard.getCoin());

        //BUILD ACTION
        assertEquals(0,wonderBoard.getBuilding().getLength());

        finalAction.setBuildCard(true);
        Whitebox.setInternalState(playerController, "finalAction", finalAction);
        Whitebox.setInternalState(playerController, "playedCard", new Card("test",null,null,0,null));
        playerController.finishAction("test",wonderBoard,null,null,null);

        assertEquals(1,wonderBoard.getBuilding().getLength());

        //DISCARD ACTION
        assertEquals(0,discardDeck.getLength());

        finalAction.setDiscardCard(true);
        Whitebox.setInternalState(playerController, "finalAction", finalAction);
        Whitebox.setInternalState(playerController, "playedCard", new Card("test",null,null,0,null));
        playerController.finishAction("test",wonderBoard,discardDeck,null,null);

        assertEquals(1,discardDeck.getLength());

        //GAIN COIN
        assertEquals(5,wonderBoard.getCoin());

        finalAction.setCoinEarned(3);
        Whitebox.setInternalState(playerController, "finalAction", finalAction);
        playerController.finishAction("test",wonderBoard,null,null,null);

        assertEquals(8,wonderBoard.getCoin());

        //RESET
        assertEquals(false, finalAction.cantBuildCard());
        assertEquals(false, finalAction.isBuildCard());
        assertEquals(false,finalAction.isDiscardCard());
        assertEquals(0,finalAction.getCoinEarned());
        assertEquals(0,finalAction.getCoinToPay());
    }

    @Test
    public void afterActionTest(){
        Whitebox.setInternalState(playerController, "playedCardIsBuild", true);

        //VOISIN
        WonderBoard leftW = new WonderBoard("testL",null);
        leftW.getBuilding().addCard(new Card("test",CardType.RAW_MATERIALS,null,0,null));
        leftW.getBuilding().addCard(new Card("test",CardType.RAW_MATERIALS,null,0,null));

        WonderBoard rigthW = new WonderBoard("testR",null);
        rigthW.getBuilding().addCard(new Card("test",CardType.RAW_MATERIALS,null,0,null));

        //EFFET ADD COIN
        assertEquals(8,wonderBoard.getCoin());

        Whitebox.setInternalState(playerController, "playedCard", new Card("test",null,new CoinEffect(5),0,null));
        playerController.afterAction("test",wonderBoard,leftW,rigthW, null);

        assertEquals(13,wonderBoard.getCoin());

        //EFFET ADD MILITARY POWER
        assertEquals(0,wonderBoard.getMilitaryPower());

        Whitebox.setInternalState(playerController, "playedCard", new Card("test",null,new MilitaryEffect(2),0,null));
        playerController.afterAction("test",wonderBoard,leftW,rigthW,null);

        assertEquals(2,wonderBoard.getMilitaryPower());

        //EFFET EARN COIN WITH CARD
        assertEquals(13,wonderBoard.getCoin());

        Whitebox.setInternalState(playerController, "playedCard", new Card("test",null,new EarnWithCardEffect(new EarnWithCard(2 , 0 ,TargetType.RIGHT_NEIGHTBOUR,CardType.RAW_MATERIALS)),0,null));
        playerController.afterAction("test",wonderBoard,leftW,rigthW,null );

        assertEquals(13,wonderBoard.getCoin());
    }
}
