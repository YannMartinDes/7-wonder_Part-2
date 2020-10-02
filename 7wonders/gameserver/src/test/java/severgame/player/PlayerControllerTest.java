package severgame.player;

import client.AI.AI;
import commun.action.Action;
import commun.action.ActionType;
import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.cost.CoinCost;
import commun.cost.MaterialCost;
import commun.effect.ChoiceMaterialEffect;
import commun.effect.CoinEffect;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import commun.wonderboard.WonderBoard;
import log.GameLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
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
        deck.addCard(new Card("test1",CardType.CIVIL_BUILDING,new CoinEffect(0),1,null));//DiscardCard
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
        Mockito.when(ai.chooseAction(Mockito.any(Deck.class))).thenReturn(new Action(ActionType.DISCARD,index));

        assertEquals(0,discardDeck.getLength());//Rien dans la défausse.
        assertEquals(sizeDeck,deck.getLength());
        assertEquals(0,wonderBoard.getBuilding().getLength());

        Card playedCard = deck.getCard(0);

        playerController.chooseAction(deck);
        playerController.playAction(deck,wonderBoard);
        playerController.finishAction("test",wonderBoard,discardDeck);

        assertEquals(1,discardDeck.getLength());
        assertEquals(playedCard,discardDeck.getCard(0));//Elle se retrouve dans la défausse.
        assertEquals(sizeDeck-1,deck.getLength());
        assertEquals(0,wonderBoard.getBuilding().getLength());
        assertEquals(11,wonderBoard.getCoin());
    }

    @Test
    public void BuildCoin(){
        int index = 1;
        Mockito.when(ai.chooseAction(Mockito.any(Deck.class))).thenReturn(new Action(ActionType.BUILD,index));

        assertEquals(0,discardDeck.getLength());//Rien dans la défausse.
        assertEquals(0,wonderBoard.getBuilding().getLength());
        assertEquals(sizeDeck,deck.getLength());

        Card playedCard = deck.getCard(index);

        playerController.chooseAction(deck);
        playerController.playAction(deck,wonderBoard);
        playerController.finishAction("test",wonderBoard,discardDeck);

        assertEquals(0,discardDeck.getLength());
        assertEquals(sizeDeck-1,deck.getLength());
        assertEquals(1,wonderBoard.getBuilding().getLength());
        assertEquals(playedCard,wonderBoard.getBuilding().getCard(0));
        assertEquals(6,wonderBoard.getCoin());

        //CARTE TROP CHERE
        playedCard = deck.getCard(index);
        Mockito.when(ai.chooseAction(Mockito.any(Deck.class))).thenReturn(new Action(ActionType.BUILD,index));
        playerController.playAction(deck,wonderBoard);
        playerController.finishAction("test",wonderBoard,discardDeck);

        assertEquals(1,discardDeck.getLength());
        assertEquals(playedCard,discardDeck.getCard(0));//Elle se retrouve dans la défausse.
        assertEquals(1,wonderBoard.getBuilding().getLength());//Pas de changement
        assertEquals(9,wonderBoard.getCoin());//+3 de la défausse.
        assertEquals(sizeDeck-2,deck.getLength());//La carte est supprimée

    }

    @Test
    public void BuildMaterial(){
        int index = 3;
        Mockito.when(ai.chooseAction(Mockito.any(Deck.class))).thenReturn(new Action(ActionType.BUILD,index));

        assertEquals(0,discardDeck.getLength());//Rien dans la défausse.
        assertEquals(0,wonderBoard.getBuilding().getLength());
        assertEquals(sizeDeck,deck.getLength());

        Card playedCard = deck.getCard(index);

        playerController.chooseAction(deck);
        playerController.playAction(deck,wonderBoard);
        playerController.finishAction("test",wonderBoard,discardDeck);

        assertEquals(0,discardDeck.getLength());
        assertEquals(sizeDeck-1,deck.getLength());
        assertEquals(1,wonderBoard.getBuilding().getLength());
        assertEquals(playedCard,wonderBoard.getBuilding().getCard(0));
        assertEquals(8,wonderBoard.getCoin());

        //CARTE TROP CHERE
        playedCard = deck.getCard(index);
        Mockito.when(ai.chooseAction(Mockito.any(Deck.class))).thenReturn(new Action(ActionType.BUILD,index));
        playerController.playAction(deck,wonderBoard);
        playerController.finishAction("test",wonderBoard,discardDeck);

        assertEquals(1,discardDeck.getLength());
        assertEquals(playedCard,discardDeck.getCard(0));//Elle se retrouve dans la défausse.
        assertEquals(1,wonderBoard.getBuilding().getLength());//Pas de changement
        assertEquals(11,wonderBoard.getCoin());//+3 de la défausse.
        assertEquals(sizeDeck-2,deck.getLength());//La carte est supprimée

    }


}
