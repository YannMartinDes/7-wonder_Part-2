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
import commun.wonderboard.WonderStep;
import log.GameLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import servergame.player.PlayerController;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        deck.addCard(new Card("test1",CardType.CIVIL_BUILDING,new CoinEffect(5),1,new CoinCost(0)));//DiscardCard
        deck.addCard(new Card("test2",CardType.CIVIL_BUILDING,new CoinEffect(7),1,new CoinCost(5)));//CoinCost
        deck.addCard(new Card("test3",CardType.CIVIL_BUILDING,new CoinEffect(30),1,new CoinCost(20)));//trop cher CoinCost
        deck.addCard(new Card("test4",CardType.CIVIL_BUILDING,new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD,1))),1,new MaterialCost(new Material(MaterialType.STONE,1))));//MaterialCost
        deck.addCard(new Card("test5",CardType.CIVIL_BUILDING,new MilitaryEffect(1),1,new MaterialCost(new Material(MaterialType.ORES,1))));//trop cher MaterialCost

        sizeDeck = deck.getLength();

        wonderBoard = new WonderBoard("testWonder",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,1))));
        wonderBoard.addCoin(5);//8 pièces au total car 3 de base.
    }

    @Test
    public void chooseActionTest(){

        Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(new Action(ActionType.DISCARD,0, false));
        playerController.chooseAction(deck,10,new EffectList());

        assertFalse(playerController.getAction().isPlayJoker() );
        assertTrue(playerController.getAction().getIndexOfCard()== 0 );
        assertTrue(playerController.getAction().getActionType() == ActionType.DISCARD);

    }

    //-----------------------------------PlayActionTests------------------------------------------//

    @Test
    public void discardActionTest ()
    {
        int index = 0;
        Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(new Action(ActionType.DISCARD,index, false));

        assertEquals(0,discardDeck.getLength());//Rien dans la défausse.
        assertEquals(sizeDeck,deck.getLength());
        assertEquals(0,wonderBoard.getBuilding().getLength());

        Card playedCard = deck.getCard(index);

        playerController.chooseAction(deck, 0, new EffectList());
        playerController.playAction(deck,wonderBoard);

        assertTrue(playerController.getFinalAction().isDiscardCard());
        assertEquals(playerController.getFinalAction().getCoinEarned() , 3);

        playerController.finishAction("tes1",wonderBoard,discardDeck,null,null);

        assertEquals(1,discardDeck.getLength());
        assertEquals(playedCard,discardDeck.getCard(0));//Elle se retrouve dans la défausse.
        assertEquals(sizeDeck-1,deck.getLength()); // elle n'est plus dans le deck
        assertEquals(0,wonderBoard.getBuilding().getLength()); // elle n'est pas ajouter à la liste des batiment
        assertEquals(11,wonderBoard.getCoin()); //8+3 coin gagner grace à l'action Discard Card
    }

    @Test
    public void buildCoin() {
        int index = 1;
        Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(new Action(ActionType.BUILD, index, false));

        assertEquals(0, discardDeck.getLength());//Rien dans la défausse.
        assertEquals(0, wonderBoard.getBuilding().getLength());
        assertEquals(sizeDeck, deck.getLength());

        Card playedCard = deck.getCard(index);
        assertEquals(playedCard.getCostCard().getCoinCost(), 5); // cette carte coute 5 coin
        assertEquals(playedCard.getCardEffect().getNumberOfCoin(), 7); // elle fait gagner 7 coin au joueur


        playerController.chooseAction(deck, wonderBoard.getCoin(), wonderBoard.getAllEffects());
        playerController.playAction(deck, wonderBoard);

        assertEquals(playerController.getFinalAction().isDiscardCard(), false);
        assertTrue(playerController.getFinalAction().isBuildCard());
        assertEquals(playerController.getFinalAction().getCoinToPay(), playedCard.getCostCard().getCoinCost());

        playerController.finishAction("test2", wonderBoard, discardDeck, null, null);
        playerController.afterAction("test2", wonderBoard, null, null, discardDeck);

        assertEquals(0, discardDeck.getLength());
        assertEquals(sizeDeck - 1, deck.getLength());
        assertEquals(1, wonderBoard.getBuilding().getLength());
        assertEquals(playedCard, wonderBoard.getBuilding().getCard(0));
        assertEquals(8 + playedCard.getCardEffect().getNumberOfCoin() - playedCard.getCostCard().getCoinCost(), wonderBoard.getCoin());
    }

    @Test
    public void discardBuildCoin() {
        //CARTE TROP CHERE

        int index = 2;
        Card playedCard = deck.getCard(index);//coute : 20 coins // fait gagner : 30 coins

        assertEquals( 0,discardDeck.getLength());
        assertEquals(0,wonderBoard.getBuilding().getLength());
        assertEquals(sizeDeck,deck.getLength());


        Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(new Action(ActionType.BUILD,index, false));
        playerController.chooseAction(deck, wonderBoard.getCoin(), wonderBoard.getAllEffects());
        playerController.playAction(deck,wonderBoard);

        assertTrue(playerController.getFinalAction().isDiscardCard());
        assertFalse(playerController.getFinalAction().isBuildCard());
        assertEquals(playerController.getFinalAction().getCoinEarned(),3);

        playerController.finishAction("test3",wonderBoard,discardDeck,null,null);
        playerController.afterAction("test3",wonderBoard,null,null,discardDeck);

        assertEquals( 1,discardDeck.getLength());
        assertEquals(playedCard,discardDeck.getCard(0));//Elle se retrouve dans la défausse.
        assertEquals(0,wonderBoard.getBuilding().getLength());//Pas de changement
        assertEquals(8+3,wonderBoard.getCoin());//+3 de la défausse.
        assertEquals(sizeDeck-1,deck.getLength());//La carte est supprimée

    }

    @Test
    public void buildMaterial() {
        int index = 3;
        Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(new Action(ActionType.BUILD, index, true));

        assertEquals(0, discardDeck.getLength());//Rien dans la défausse.
        assertEquals(0, wonderBoard.getBuilding().getLength());
        assertEquals(sizeDeck, deck.getLength());
        assertEquals(1,wonderBoard.getAllEffects().filterMaterialEffect().size()); //il n'y a que le materiel stone


        Card playedCard = deck.getCard(index);

        playerController.chooseAction(deck, wonderBoard.getCoin(), new EffectList());
        playerController.playAction(deck, wonderBoard);

        assertFalse(playerController.getFinalAction().isDiscardCard());
        assertTrue(playerController.getFinalAction().isBuildCard());
        assertEquals(playerController.getFinalAction().getCoinEarned(),0);

        playerController.finishAction("test4", wonderBoard, discardDeck, null, null);
        playerController.afterAction("test4",wonderBoard,null,null,discardDeck);

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
    public void discardBuildMaterial() {
        //CARTE TROP CHERE
        int index = 4;
        Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(new Action(ActionType.BUILD, index, false));

        assertEquals(0, discardDeck.getLength());//Rien dans la défausse.
        assertEquals(0, wonderBoard.getBuilding().getLength());
        assertEquals(sizeDeck, deck.getLength());

        Card playedCard = deck.getCard(index);

        playerController.chooseAction(deck, wonderBoard.getCoin(), new EffectList());
        playerController.playAction(deck, wonderBoard);

        assertTrue(playerController.getFinalAction().isDiscardCard());
        assertFalse(playerController.getFinalAction().isBuildCard());
        assertEquals(playerController.getFinalAction().getCoinEarned(),3);

        playerController.finishAction("test5", wonderBoard, discardDeck, null, null);
        playerController.afterAction("test5",wonderBoard,null,null,discardDeck);

        assertEquals(1, discardDeck.getLength());//Elle se retrouve dans la défausse.
        assertEquals(0, wonderBoard.getBuilding().getLength());//Pas de changement
        assertEquals(8 + 3, wonderBoard.getCoin());//+3 de la défausse.
        assertEquals(sizeDeck - 1, deck.getLength());//La carte est supprimée
        assertEquals(1,wonderBoard.getAllEffects().filterMaterialEffect().size()); // le maeriel wood a bien été ajouter
        assertEquals(MaterialType.STONE,wonderBoard.getAllEffects().filterMaterialEffect().get(0).getMaterials()[0].getType());
    }


    @Test
    public void DiscardBuildStageWonder() {
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

        Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(new Action(ActionType.BUILD_STAGE_WONDER, index, false));

        playerController.chooseAction(deck, wonderBoard.getCoin(), new EffectList());
        playerController.playAction(deck, wonderBoard);

        assertTrue(playerController.getFinalAction().getCoinEarned() == 3);
        assertTrue(playerController.getFinalAction().isDiscardCard());

        playerController.finishAction("test5", wonderBoard, discardDeck, null, null);
        playerController.afterAction("test5", wonderBoard, null, null, discardDeck);

        assertEquals(1, discardDeck.getLength());//Elle se retrouve dans la défausse.
        assertEquals(0, wonderBoard.getBuilding().getLength());//Pas de changement
        assertEquals(8 + 3, wonderBoard.getCoin());//+3 de la défausse.
        assertTrue(wonderBoard.getWonderSteps().containsAll(listWs1));
    }

        // -------------- On construit une étape de la merveille -------------//
        @Test
        public void buildStageWonder() {
            int index=0;
            List<WonderStep> listWs2 = new ArrayList<>();
            Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(new Action(ActionType.BUILD_STAGE_WONDER, index, false));

            WonderStep ws1 = new WonderStep(new MaterialCost(new Material(MaterialType.WOOD,1)), 1, new CoinEffect(1));
            WonderStep ws2 = new WonderStep(new MaterialCost(new Material(MaterialType.STONE,1)), 2, new CoinEffect(5));
            ws1.toBuild();
            listWs2.add(ws1);
            listWs2.add(ws2);
            wonderBoard.setWonderSteps(listWs2);
            assertEquals(wonderBoard.getWonderSteps().get(1).getBuilt(), false);

            playerController.chooseAction(deck, wonderBoard.getCoin(), new EffectList());
            playerController.playAction(deck, wonderBoard);

            assertFalse(playerController.getFinalAction().cantBuildStep());
            assertFalse(playerController.getFinalAction().isDiscardCard());
            assertTrue(playerController.getFinalAction().isBuildStep());
            assertEquals(wonderBoard.getCoin(), 8);
            assertTrue(wonderBoard.getWonderSteps().get(1).getEffects()[0].getNumberOfCoin() == 5); // en cas de construction de la merveille

            playerController.finishAction("test5", wonderBoard, discardDeck, null, null);
            playerController.afterAction("test5", wonderBoard, null, null, discardDeck);

            assertTrue(wonderBoard.getWonderSteps().get(1).getBuilt());
            assertEquals(wonderBoard.getWonderSteps().get(1).getConstructionMarker().getName(), "test1");
            assertEquals(sizeDeck-1, deck.size());
            assertEquals(wonderBoard.getCoin(), 8+5);// ajout du gains


        }

    //-----------------------------------FinishActionTests------------------------------------------//

    @Test
    public void coinToPayFinishActionTest() {
        playerController = new PlayerController(ai);
        FinalAction finalAction = new FinalAction();

        //COIN TO PAY ACTION
        assertEquals(8, wonderBoard.getCoin());

        finalAction.setCoinToPay(3);

        Whitebox.setInternalState(playerController, "finalAction", finalAction);
        assertTrue(playerController.getFinalAction().getCoinToPay() == 3);
        playerController.finishAction("test6", wonderBoard, null, null, null);

        assertTrue(playerController.getFinalAction().getCoinToPay() == 0);
        assertEquals(5, wonderBoard.getCoin());
    }

    @Test
    public void buildFinishActionTest() {
        playerController = new PlayerController(ai);
        FinalAction finalAction = new FinalAction();
        //BUILD ACTION
        assertEquals(0, wonderBoard.getBuilding().getLength());

        finalAction.setBuildCard(true);
        Whitebox.setInternalState(playerController, "finalAction", finalAction);
        Whitebox.setInternalState(playerController, "playedCard", new Card("test7", null, null, 0, null));
        assertTrue(playerController.getFinalAction().isBuildCard());
        playerController.finishAction("test7", wonderBoard, null, null, null);

        assertEquals(1, wonderBoard.getBuilding().getLength());
    }

    @Test
    public void discardFinishActionTest() {
        playerController = new PlayerController(ai);
        FinalAction finalAction = new FinalAction();

        assertEquals(0, discardDeck.getLength());

        finalAction.setDiscardCard(true);
        Whitebox.setInternalState(playerController, "finalAction", finalAction);
        Whitebox.setInternalState(playerController, "playedCard", new Card("test8", null, null, 0, null));
        assertTrue(playerController.getFinalAction().isDiscardCard());
        playerController.finishAction("test8", wonderBoard, discardDeck, null, null);

        assertEquals(1, discardDeck.getLength());
    }

    @Test
    public void gainCoinFinishActionTest() {
        playerController = new PlayerController(ai);
        FinalAction finalAction = new FinalAction();

        assertEquals(8,wonderBoard.getCoin());

        finalAction.setCoinEarned(3);
        Whitebox.setInternalState(playerController, "finalAction", finalAction);
        assertTrue(playerController.getFinalAction().getCoinEarned() == 3);
        playerController.finishAction("test9",wonderBoard,null,null,null);

        assertEquals(8+3,wonderBoard.getCoin());

        //Test du RESET
        assertEquals(false, finalAction.cantBuildCard());
        assertEquals(false, finalAction.isBuildCard());
        assertEquals(false,finalAction.isDiscardCard());
        assertEquals(0,finalAction.getCoinEarned());
        assertEquals(0,finalAction.getCoinToPay());
    }


    //-----------------------------------AfterActionTests------------------------------------------//
    @Test
    public void afterActionTest() {
        Whitebox.setInternalState(playerController, "playedCardIsBuild", true);

        //VOISIN
        WonderBoard leftW = new WonderBoard("testL", null);
        leftW.getBuilding().addCard(new Card("test", CardType.RAW_MATERIALS, null, 0, null));
        leftW.getBuilding().addCard(new Card("test", CardType.RAW_MATERIALS, null, 0, null));

        WonderBoard rigthW = new WonderBoard("testR", null);
        rigthW.getBuilding().addCard(new Card("test", CardType.RAW_MATERIALS, null, 0, null));

        //EFFET ADD COIN
        assertEquals(8, wonderBoard.getCoin());

        Whitebox.setInternalState(playerController, "playedCard", new Card("test", null, new CoinEffect(5), 0, null));
        playerController.afterAction("test", wonderBoard, leftW, rigthW, null);

        assertEquals(8 + 5, wonderBoard.getCoin());

        //EFFET ADD MILITARY POWER
        assertEquals(0, wonderBoard.getMilitaryPower());

        Whitebox.setInternalState(playerController, "playedCard", new Card("test", null, new MilitaryEffect(2), 0, null));
        playerController.afterAction("test", wonderBoard, leftW, rigthW, null);

        assertEquals(2, wonderBoard.getMilitaryPower());
    }

    @Test
    public void earnWithCardEffectAfterActionTestMeAndNeighbor() {
        //VOISIN
        WonderBoard leftW = new WonderBoard("testL", null);
        leftW.getBuilding().addCard(new Card("test", CardType.RAW_MATERIALS, null, 0, null));
        leftW.getBuilding().addCard(new Card("test", CardType.RAW_MATERIALS, null, 0, null));

        WonderBoard rigthW = new WonderBoard("testR", null);
        rigthW.getBuilding().addCard(new Card("test", CardType.RAW_MATERIALS, null, 0, null));

        wonderBoard.getBuilding().addCard(new Card("test", CardType.RAW_MATERIALS, null, 0, null));
        assertEquals(8,wonderBoard.getCoin());

        //EFFET EARN COIN WITH CARD

        Whitebox.setInternalState(playerController, "playedCard",  new Card("test2",CardType.COMMERCIAL_BUILDINGS,new EarnWithCardEffect(new EarnWithCard(1,0,TargetType.ME_AND_NEIGHTBOUR,CardType.RAW_MATERIALS)),0,null));
        Whitebox.setInternalState(playerController, "playedCardIsBuild",true);

        playerController.afterAction("test",wonderBoard,leftW,rigthW,discardDeck);

        assertEquals(8+ 1+1+2,wonderBoard.getCoin()); //1 par cartes grises chez le joueur et ses voisin donc 1+1+2

    }

    @Test
    public void earnWithCardEffectBothNeighbor() {
        //VOISIN
        WonderBoard leftW = new WonderBoard("testL", null);
        leftW.getBuilding().addCard(new Card("test", CardType.RAW_MATERIALS, null, 0, null));
        leftW.getBuilding().addCard(new Card("test", CardType.RAW_MATERIALS, null, 0, null));

        WonderBoard rigthW = new WonderBoard("testR", null);
        rigthW.getBuilding().addCard(new Card("test", CardType.RAW_MATERIALS, null, 0, null));

        wonderBoard.getBuilding().addCard(new Card("test", CardType.RAW_MATERIALS, null, 0, null));
        assertEquals(8,wonderBoard.getCoin());

        //EFFET EARN COIN WITH CARD

        Whitebox.setInternalState(playerController, "playedCard",  new Card("test2",CardType.COMMERCIAL_BUILDINGS,new EarnWithCardEffect(new EarnWithCard(3,0,TargetType.BOTH_NEIGHTBOUR,CardType.RAW_MATERIALS)),0,null));
        Whitebox.setInternalState(playerController, "playedCardIsBuild",true);

        playerController.afterAction("test",wonderBoard,leftW,rigthW,discardDeck);

        assertEquals(8+ (1 + 2)*3,wonderBoard.getCoin()); //1 par cartes grises chez les  voisins du joueur donc 1+2

    }

    @Test
    public void earnWithWonderStepEffectMeAndNeighbor() {
        List<WonderStep> listWs1 = new ArrayList<>();
        List<WonderStep> listWs2 = new ArrayList<>();
        List<WonderStep> listWs3 = new ArrayList<>();

        WonderBoard leftW = new WonderBoard("testL", null);
        WonderBoard rigthW = new WonderBoard("testR", null);

        WonderStep ws1=new WonderStep(new CoinCost(0),1, new CoinEffect(0));
        WonderStep ws2=new WonderStep(new CoinCost(0),2, new CoinEffect(0));
        WonderStep ws3=new WonderStep(new CoinCost(0),3, new CoinEffect(0));
        ws1.toBuild();
        ws2.toBuild();
        ws3.toBuild();
        listWs1.add(ws1);
        listWs2.addAll(listWs1);
        listWs1.add(ws2);
        listWs3.addAll(listWs1);
        listWs1.add(ws3);

        leftW.setWonderSteps(listWs1); // 3 step
        wonderBoard.setWonderSteps(listWs2); //2 steps
        rigthW.setWonderSteps(listWs3); //1 steps

        assertEquals(8,wonderBoard.getCoin());

        //EFFET EARN COIN WITH CARD

        Whitebox.setInternalState(playerController, "playedCard",  new Card("test2",CardType.COMMERCIAL_BUILDINGS,new EarnWithWonderEffect(new EarnWithWonder(TargetType.ME_AND_NEIGHTBOUR,5,0)),3,null));
        Whitebox.setInternalState(playerController, "playedCardIsBuild",true);

        playerController.afterAction("test",wonderBoard,leftW,rigthW,discardDeck);

        assertEquals(8+ (1+2+3)*5,wonderBoard.getCoin()); // 5 coins par étapes chez chaque voisin et le joeuur lui meme donc : (1+2+3)*5

    }

    @Test
    public void playWithStepsTest(){
        // ----choix d'une carte parmis les cartes défaussé---- //

        Whitebox.setInternalState(playerController, "playedStepIsBuild",true);

        WonderStep ws1=new WonderStep(new CoinCost(0),1, new CoinEffect(0));
        WonderStep ws2=new WonderStep(new CoinCost(0),2, new CoinEffect(0));
        ws1.toBuild();
        ws1.setPlayDiscardedCard(true);
        List<WonderStep> listWs = new ArrayList<>();
        listWs.add(ws1);
        listWs.add(ws2);
        Whitebox.setInternalState(playerController, "currentStep",ws1);
        assertTrue(discardDeck.getLength() == 0);
        assertTrue(wonderBoard.getBuilding().getLength() == 0);

        wonderBoard.setWonderSteps(listWs);
        discardDeck.add(new Card("test2",CardType.COMMERCIAL_BUILDINGS,new EarnWithWonderEffect(new EarnWithWonder(TargetType.ME_AND_NEIGHTBOUR,5,0)),3,null));
        Mockito.when(ai.chooseCard(Mockito.any(Deck.class))).thenReturn(0);

        assertTrue(discardDeck.getLength() == 1);

        playerController.afterAction("test",wonderBoard,null,null,discardDeck);

        assertTrue(wonderBoard.getBuilding().getLength() == 1); //carte ajouter au batiment du joueur
        assertTrue(discardDeck.getLength() == 0); //carte supprimer des carte défausser

        // ---copier une card guild chez un voisin---- //
        Whitebox.setInternalState(playerController, "currentStep",ws2);

        ws2.toBuild();
        ws2.setCopyNeighborGuild(true);
        WonderBoard leftW = new WonderBoard("testL", null);
        WonderBoard rightW = new WonderBoard("testR", null);

        leftW.getBuilding().addCard(new Card("test1", CardType.GUILD_BUILDINGS, null, 0, null));
        rightW.getBuilding().addCard(new Card("test2", CardType.GUILD_BUILDINGS, null, 0, null));

        playerController.afterAction("test",wonderBoard,leftW,rightW,discardDeck);

        assertTrue(wonderBoard.getBuilding().getLength() == 2); //carte ajouter au batiment du joueur apres copie
        assertTrue(leftW.getBuilding().getLength() == 1); //carte du voisins n'ont pas été toucher mais juste copier
        assertTrue(rightW.getBuilding().getLength() == 1);
        assertTrue(wonderBoard.getBuilding().contains(leftW.getBuilding().getCard(0))
                ||wonderBoard.getBuilding().contains(rightW.getBuilding().getCard(0)));
        // le joueur a choisi de copier l'une des deux guilde des voisins


    }

    @Test
    public void playWithStepsJokerTest() {
        // ----choix d'une carte gratuite avec le joker ---- //

        WonderStep ws1=new WonderStep(new CoinCost(0),1, new CoinEffect(0));
        ws1.toBuild();
        ws1.setHaveJoker(true);
        List<WonderStep> listWs = new ArrayList<>();
        listWs.add(ws1);

        wonderBoard.setWonderSteps(listWs);

        int index = 2; //carte a 20 coins
        Mockito.when(ai.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(new Action(ActionType.BUILD, index, true));
        Card playedCard = deck.getCard(index);

        assertEquals(0, discardDeck.getLength());//Rien dans la défausse.
        assertEquals(0, wonderBoard.getBuilding().getLength());
        assertEquals(sizeDeck, deck.getLength());

        assertEquals(playedCard.getCostCard().getCoinCost(), 20); // cette carte coute 20 coin mais avec le joker 0
        assertFalse(wonderBoard.getWonderSteps().get(0).isUsedJoker());

        assertEquals(playedCard.getCardEffect().getNumberOfCoin(), 30); // elle fait gagner 30 coin au joueur

        playerController.chooseAction(deck, wonderBoard.getCoin(), wonderBoard.getAllEffects());
        playerController.playAction(deck, wonderBoard);

        assertFalse(playerController.getFinalAction().isDiscardCard());
        assertTrue(playerController.getFinalAction().isBuildCard());
        assertTrue(playerController.getFinalAction().isCardBuiltwithJoker());

        assertNotEquals(playerController.getFinalAction().getCoinToPay(), playedCard.getCostCard().getCoinCost());
        assertEquals(playerController.getFinalAction().getCoinToPay(), 0);

        playerController.finishAction("test2", wonderBoard, discardDeck, null, null);
        playerController.afterAction("test2", wonderBoard, null, null, discardDeck);

        assertTrue(wonderBoard.getWonderSteps().get(0).isUsedJoker());
        assertEquals(wonderBoard.getCoin() , 8+30 -0 );

        assertEquals(1, wonderBoard.getBuilding().getLength());
        assertEquals(0, discardDeck.getLength());//Rien dans la défausse.
        assertEquals(sizeDeck-1, deck.getLength());
    }

}
