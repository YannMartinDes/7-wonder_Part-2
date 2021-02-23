package severgame.player;

import client.AI.AI;
import client.AI.RandomAI;
import commun.action.AbstractAction;
import commun.action.BuildAction;
import commun.action.BuildStepAction;
import commun.action.DiscardAction;
import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.communication.StatObject;
import commun.cost.CoinCost;
import commun.cost.MaterialCost;
import commun.effect.*;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import commun.player.Player;
import commun.wonderboard.WonderBoard;
import commun.wonderboard.WonderStep;
import log.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import servergame.player.PlayerController;
import servergame.player.PlayerManagerImpl;
import servergame.player.PlayerView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerControllerTest {

    private Player player1 ;
    private Player player2 ;
    private Player player3 ;

    @Mock
    AbstractAction action ;
    AI ai = Mockito.mock(AI.class);
    PlayerController playerController  ;
    Deck deck = new Deck();
    Deck discardDeck = new Deck();
    WonderBoard wonderBoard;
    WonderBoard wonderBoard2;
    WonderBoard wonderBoard3;

    int sizeDeck;
    StatObject statObject;


    @BeforeEach
    void prepare(){
        Logger.logger.verbose = false;

        wonderBoard = new WonderBoard("testWonder",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,1))));
        wonderBoard2 = new WonderBoard("testWonder",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,1))));
        wonderBoard3 = new WonderBoard("testWonder",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,1))));

        player1 = new Player("player1",wonderBoard);
        player2 = new Player("player2",wonderBoard2);
        player3 = new Player("player3",wonderBoard3);

        this.playerController = new PlayerController(player1,ai);
        discardDeck = new Deck();

        deck = new Deck();
        deck.addCard(new Card("test1",CardType.CIVIL_BUILDING,new CoinEffect(5),1,new CoinCost(0)));//DiscardCard
        deck.addCard(new Card("test2",CardType.CIVIL_BUILDING,new CoinEffect(7),1,new CoinCost(5)));//CoinCost
        deck.addCard(new Card("test3",CardType.CIVIL_BUILDING,new CoinEffect(30),1,new CoinCost(20)));//trop cher CoinCost
        deck.addCard(new Card("test4",CardType.CIVIL_BUILDING,new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD,1))),1,new MaterialCost(new Material(MaterialType.STONE,1))));//MaterialCost
        deck.addCard(new Card("test5",CardType.CIVIL_BUILDING,new MilitaryEffect(1),1,new MaterialCost(new Material(MaterialType.ORES,1))));//trop cher MaterialCost
        sizeDeck = deck.getLength();

        player1.setCurrentDeck(deck);
        player1.setLeftNeightbour(wonderBoard2);
        player1.setRightNeightbour(wonderBoard3);

        wonderBoard.addCoin(5);//8 pièces au total car 3 de base.

    }

    @Test
    void chooseActionTest(){
        List<PlayerController> controllers = new ArrayList<>();
        controllers.add(new PlayerController(player2,null));
        controllers.add(playerController);
        controllers.add(new PlayerController(player3,null));

        PlayerView playerView = new PlayerManagerImpl(controllers);
        this.playerController.setPlayerView(playerView);
        int index = 0;
        this.action = new DiscardAction(index);
        Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(this.action);
        assertNull(this.playerController.getAction());

        playerController.chooseAction();
        assertEquals(this.action,playerController.getAction());
        assertEquals(player1,this.playerController.getPlayer());
        assertEquals(player1,this.playerController.getMe());
        assertEquals(player2,this.playerController.getLeftNeighbours());
        assertEquals(player3,this.playerController.getRightNeighbours());
        assertEquals(playerView.getAllPlayers(),this.playerController.getAllPlayers());


    }

    //-----------------------------------PlayActionTests------------------------------------------//


    @Test
    void getTradePossibility ()
    {
        int index = 0;
        this.action = new BuildAction(index,false);
        this.action = Mockito.spy(this.action);
        List<Integer[]> l = new ArrayList<>();
        Integer[] e = new Integer[1];
        l.add(e);
        Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(this.action);
        Mockito.when(action.getTradePossibility()).thenReturn(l);
        assertEquals(0,discardDeck.getLength());//Rien dans la défausse.
        assertEquals(sizeDeck,deck.getLength());
        assertEquals(0,wonderBoard.getBuilding().getLength());

        playerController.chooseAction(); //discard
        playerController.playAction(discardDeck);
        Mockito.verify(this.action).nextAction(Mockito.anyString(),Mockito.any(Deck.class),Mockito.any(WonderBoard.class),Mockito.any(Deck.class),Mockito.any(WonderBoard.class),Mockito.any(WonderBoard.class),Mockito.any());

    }



    @Test
    void discardActionTest ()
    {
        int index = 0;
        this.action = new DiscardAction(index);
        this.action = Mockito.spy(this.action);

        Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(this.action);

        assertEquals(0,discardDeck.getLength());//Rien dans la défausse.
        assertEquals(sizeDeck,deck.getLength());
        assertEquals(0,wonderBoard.getBuilding().getLength());

        Card playedCard = deck.getCard(index);

        playerController.chooseAction(); //discard
        playerController.playAction(discardDeck);

        Mockito.verify(this.action).getTradePossibility();
        assertEquals(1,discardDeck.getLength());
        assertEquals(playedCard,discardDeck.getCard(0));//Elle se retrouve dans la défausse.
        assertEquals(sizeDeck-1,deck.getLength()); // elle n'est plus dans le deck
        assertEquals(0,wonderBoard.getBuilding().getLength()); // elle n'est pas ajouter à la liste des batiment
        assertEquals(8+3,wonderBoard.getCoin()); //8+3 coin gagner grace à l'action Discard Card
    }


    @Test
    void buildCoin() {
        int index = 1;
        this.action =  new BuildAction(index,false);
        this.action = Mockito.spy(action);

        ReflectionTestUtils.setField(this.playerController, "statObject", this.statObject);
        ReflectionTestUtils.setField(this.playerController, "action", this.action);

        assertEquals(0, discardDeck.getLength());//Rien dans la défausse.
        assertEquals(0, wonderBoard.getBuilding().getLength());
        assertEquals(sizeDeck, deck.getLength());

        Card playedCard = deck.getCard(index);
        assertEquals(playedCard.getCostCard().getCoinCost(), 5); // cette carte coute 5 coin
        assertEquals(playedCard.getCardEffect().getNumberOfCoin(), 7); // elle fait gagner 7 coin au joueur

        playerController.playAction(discardDeck);
        assertEquals(playedCard, playerController.getAction().getPlayedCard() );

        playerController.finishAction(discardDeck);

        Mockito.verify(action).getTradePossibility();
        Mockito.verify(action).logAction(Mockito.anyString(),Mockito.any(WonderBoard.class),Mockito.any(Deck.class),Mockito.any(WonderBoard.class),Mockito.any(WonderBoard.class));

        assertEquals(0, discardDeck.getLength());
        assertEquals(sizeDeck - 1, deck.getLength());
        assertEquals(1, wonderBoard.getBuilding().getLength());
        assertEquals(playedCard, wonderBoard.getBuilding().getCard(0));
        assertEquals(8 + playedCard.getCardEffect().getNumberOfCoin() - playedCard.getCostCard().getCoinCost(), wonderBoard.getCoin());
    }

    @Test
    void discardAction() {
        //CARTE TROP CHERE
        int index = 2;
        this.action =new BuildAction(index ,false);
        Card playedCard = deck.getCard(index);//coute : 20 coins // fait gagner : 30 coins

        assertEquals( 0,discardDeck.getLength());
        assertEquals(0,wonderBoard.getBuilding().getLength());
        assertEquals(sizeDeck,deck.getLength());


        Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(action);
        playerController.chooseAction();
        playerController.playAction(discardDeck);
        assertEquals(playedCard, playerController.getAction().getPlayedCard() );
        playerController.finishAction(discardDeck);

        assertEquals( 1,discardDeck.getLength());
        assertEquals(sizeDeck-1,deck.getLength());//La carte est supprimée
        assertEquals(playedCard,discardDeck.getCard(0));//Elle se retrouve dans la défausse.


    }

    @Test
    void buildMaterial() {
        int index = 3;
        this.action = new BuildAction(index,false);
        Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(action);

        assertEquals(0, discardDeck.getLength());//Rien dans la défausse.
        assertEquals(0, wonderBoard.getBuilding().getLength());
        assertEquals(sizeDeck, deck.getLength());
        assertEquals(1,wonderBoard.getAllEffects().filterMaterialEffect().size()); //il n'y a que le materiel stone

        Card playedCard = deck.getCard(index);

        playerController.chooseAction();
        playerController.playAction(discardDeck);
        playerController.finishAction( discardDeck);

        assertEquals(0, discardDeck.getLength());
        assertEquals(sizeDeck - 1, deck.getLength());
        assertEquals(1, wonderBoard.getBuilding().getLength());
        assertEquals(playedCard, wonderBoard.getBuilding().getCard(0));
        assertEquals(8, wonderBoard.getCoin()); //n'as pas ni augmenter ni diminuer
        assertEquals(2,wonderBoard.getAllEffects().filterMaterialEffect().size()); // le maeriel wood a bien été ajouter
        assertEquals(MaterialType.STONE,wonderBoard.getAllEffects().filterMaterialEffect().get(0).getMaterials()[0].getType());
        assertEquals(MaterialType.WOOD,wonderBoard.getAllEffects().filterMaterialEffect().get(1).getMaterials()[0].getType());

    }

    @Test
    void discardBuildMaterial() {
        //CARTE TROP CHERE
        int index = 4;
        this.action = new BuildAction(index,false);
        Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(action);

        assertEquals(0, discardDeck.getLength());//Rien dans la défausse.
        assertEquals(0, wonderBoard.getBuilding().getLength());
        assertEquals(sizeDeck, deck.getLength());

        Card playedCard = deck.getCard(index);

        playerController.chooseAction();
        playerController.playAction(discardDeck);
        playerController.finishAction( discardDeck);

        assertEquals(1, discardDeck.getLength());//Elle se retrouve dans la défausse.
        assertEquals(sizeDeck - 1, deck.getLength());//La carte est supprimée
        assertEquals(1,wonderBoard.getAllEffects().filterMaterialEffect().size()); // le materiel wood a bien été ajouter
        assertEquals(MaterialType.STONE,wonderBoard.getAllEffects().filterMaterialEffect().get(0).getMaterials()[0].getType());
    }


    @Test
    void DiscardBuildStepWonder() {
        // -------------- Toutes les étapes ont deja était construite -------------//
        List<WonderStep> listWs1 = new ArrayList<>();
        int index = 0;

        WonderStep ws1 = new WonderStep(new CoinCost(0), 1, new CoinEffect(0));
        WonderStep ws2 = new WonderStep(new CoinCost(0), 2, new CoinEffect(0));
        WonderStep ws3 = new WonderStep(new CoinCost(0), 3, new CoinEffect(0));
        ws1.toBuild();
        ws2.toBuild();
        ws3.toBuild();
        listWs1.add(ws1);
        listWs1.add(ws2);
        listWs1.add(ws3);

        wonderBoard.setWonderSteps(listWs1);
        this.action = new BuildStepAction(index);
        Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(this.action);

        playerController.chooseAction();
        playerController.playAction(discardDeck);
        playerController.finishAction(discardDeck);

        assertEquals(1, discardDeck.getLength());//Elle se retrouve dans la défausse.
        assertEquals(0, wonderBoard.getBuilding().getLength());//Pas de changement
        assertEquals(8 + 3, wonderBoard.getCoin());//+3 de la défausse.
        assertTrue(wonderBoard.getWonderSteps().containsAll(listWs1));
    }

        // -------------- On construit une étape de la merveille -------------//
        @Test
        void buildStageWonder() {
            int index=0;
            List<WonderStep> listWs2 = new ArrayList<>();
            this.action = new BuildStepAction(index);
            Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(this.action);

            WonderStep ws1 = new WonderStep(new MaterialCost(new Material(MaterialType.WOOD,1)), 1, new CoinEffect(1));
            WonderStep ws2 = new WonderStep(new MaterialCost(new Material(MaterialType.STONE,1)), 2, new CoinEffect(5));
            ws1.toBuild();
            listWs2.add(ws1);
            listWs2.add(ws2);
            wonderBoard.setWonderSteps(listWs2);
            assertEquals(wonderBoard.getWonderSteps().get(1).getBuilt(), false);

            playerController.chooseAction();
            playerController.playAction(discardDeck);

            assertEquals(wonderBoard.getCoin(), 8);
            assertTrue(wonderBoard.getWonderSteps().get(1).getEffects()[0].getNumberOfCoin() == 5); // en cas de construction de la merveille

            playerController.finishAction( discardDeck);

            assertTrue(wonderBoard.getWonderSteps().get(1).getBuilt());
            assertEquals(wonderBoard.getWonderSteps().get(1).getConstructionMarker().getName(), "test1");
            assertEquals(sizeDeck-1, deck.size());
            assertEquals(wonderBoard.getCoin(), 8+5);// ajout du gains


        }

    //-----------------------------------FinishActionTests------------------------------------------//

    @Test
    void finishActionTest() {
        this.action = new BuildStepAction(0);
        this.action = Mockito.spy(this.action);
        this.playerController = Mockito.spy(this.playerController);
        Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(this.action);

        Mockito.doNothing().when(this.action).finishAction(Mockito.anyString(),Mockito.any(WonderBoard.class),Mockito.any(Deck.class),Mockito.any(WonderBoard.class),Mockito.any(WonderBoard.class),Mockito.any(Card.class),Mockito.any(RandomAI.class));
        this.playerController.chooseAction();
        playerController.playAction(discardDeck);

        this.playerController.finishAction(discardDeck);
        Mockito.verify(this.action).finishAction(Mockito.anyString(),Mockito.any(WonderBoard.class),
                Mockito.any(Deck.class),Mockito.any(WonderBoard.class),Mockito.any(WonderBoard.class),Mockito.any(Card.class),Mockito.any());
    }

    @Test
    void testUseScientificsGuildEffect(){
        ScientificType scientificType = ScientificType.GEOMETRY;
        Mockito.when(ai.useScientificsGuildEffect(Mockito.any(WonderBoard.class))).thenReturn(scientificType);
        playerController.useScientificsGuildEffect(wonderBoard);
        Mockito.verify(this.ai).useScientificsGuildEffect(Mockito.any(WonderBoard.class));
        assertEquals(this.playerController.useScientificsGuildEffect(wonderBoard),scientificType);

    }

    @Test
    void testEndActionStatistics()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        this.statObject = new StatObject();
        this.statObject.construct(1);
        this.action = new DiscardAction(0);
        this.statObject = Mockito.spy(this.statObject);

        //DiscardAction
        Method method = PlayerController.class.getDeclaredMethod("endActionStatistics", String.class);
        method.setAccessible(true);
        ReflectionTestUtils.setField(this.playerController, "action", this.action);
        ReflectionTestUtils.setField(this.playerController, "statObject", this.statObject);

        /*-- run de endActionStatistics --*/
        method.invoke(this.playerController, player1.getName());
        /*-- Test --*/
        Mockito.verify(this.statObject,Mockito.times(2)).getUsernames();
        Mockito.verify(this.statObject).getStatByAge(Mockito.anyInt());

    }


    @Test
    void testPlayLastCard()
    {
        this.playerController = Mockito.spy(this.playerController);
        Mockito.doNothing().when(this.playerController).chooseAction();
        Mockito.doNothing().when(this.playerController).playAction(Mockito.any(Deck.class));
        Mockito.doNothing().when(this.playerController).finishAction(Mockito.any(Deck.class));
        this.playerController.playLastCard(discardDeck);

        Mockito.verify(this.playerController).chooseAction();
        Mockito.verify(this.playerController).playAction(Mockito.any(Deck.class));
        Mockito.verify(this.playerController).finishAction(Mockito.any(Deck.class));

    }

    @Test
    void buildFinishActionTest() {

        //Build ACTION
        this.action = new BuildAction(0,false);
        this.action = Mockito.spy(this.action);
        assertEquals(8, player1.getWonderBoard().getCoin());

        ReflectionTestUtils.setField(this.action, "playedCard", new Card("test7", null, new CoinEffect(10), 1, null));
        ReflectionTestUtils.setField(this.playerController, "action", this.action);
        ReflectionTestUtils.setField(this.action, "haveBuild", true);
        playerController.finishAction(this.discardDeck);

        Mockito.verify(this.action).finishAction(Mockito.anyString(),Mockito.any(WonderBoard.class),Mockito.any(Deck.class),Mockito.any(WonderBoard.class),Mockito.any(WonderBoard.class),Mockito.any(Card.class),Mockito.any(RandomAI.class));
        assertEquals(18, player1.getWonderBoard().getCoin()); //coins ajouter au joueur

    }

    @Test
    void discardFinishActionTest() {

        //Discard ACTION
        this.action = new DiscardAction(0);
        this.action = Mockito.spy(this.action);
        assertEquals(0, this.discardDeck.size());

        ReflectionTestUtils.setField(this.action, "playedCard", new Card("test7", null, null, 0, null));
        ReflectionTestUtils.setField(playerController, "action", this.action);
        playerController.finishAction(this.discardDeck);

        Mockito.verify(this.action).finishAction(Mockito.anyString(),Mockito.any(WonderBoard.class),Mockito.any(Deck.class),Mockito.any(WonderBoard.class),Mockito.any(WonderBoard.class),Mockito.any(Card.class),Mockito.any(RandomAI.class));

    }

    @Test
    void fillStatisticsArrayTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<Integer> l = new ArrayList<>();

        usernames.add("/");
        usernames.add(player1.getName());
        usernames.add(player2.getName());
        usernames.add(player3.getName());

        this.statObject = new StatObject();
        this.statObject.construct(3);
        this.statObject.setUsernames(usernames);
        this.statObject = Mockito.spy(this.statObject);

        Method method = PlayerController.class.getDeclaredMethod("fillStatisticsArray", int.class, StatObject.class, l.getClass() );
        method.setAccessible(true);

        ReflectionTestUtils.setField(this.playerController, "action", this.action);
        ReflectionTestUtils.setField(this.playerController, "statObject", this.statObject);

        /*-- run de endActionStatistics --*/
        ArrayList<Integer> list = new ArrayList<>();
        method.invoke(this.playerController, 0,this.statObject,list);

        assertEquals(usernames.size()-1,list.size()); // -1 pour "/"
        Mockito.verify(this.statObject,Mockito.times(usernames.size())).getUsernames();

    }

}
