package client.AI;

import commun.action.AbstractAction;
import commun.action.BuildAction;
import commun.action.BuildStepAction;
import commun.action.DiscardAction;
import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.cost.CoinCost;
import commun.effect.ChoiceMaterialEffect;
import commun.effect.EffectList;
import commun.effect.ScientificType;
import commun.effect.VictoryPointEffect;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import commun.wonderboard.WonderBoard;
import commun.wonderboard.WonderStep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RandomAITest
{
    private RandomAI randomAI;
    private Deck currentDeck;
    private int playerCoins;
    private EffectList playerEffects;
    private Random random;
    private WonderStep[] wonderSteps= new WonderStep[3];

    @BeforeEach
    void init ()
    {
        this.random = Mockito.mock(Random.class);
        this.currentDeck = new Deck();
        this.playerCoins = 0;
        this.playerEffects = new EffectList();
        this.randomAI = new RandomAI(this.random);

    }

    @Test
    void chooseActionTestDiscardCard (){
        Card c1 = new Card("test1", CardType.CIVIL_BUILDING,new VictoryPointEffect(1),1,new CoinCost(1));
        Card c2 = new Card("test2", CardType.SCIENTIFIC_BUILDINGS,new VictoryPointEffect(1),1,new CoinCost(1));
        Card c3 = new Card("test3", CardType.RAW_MATERIALS,new VictoryPointEffect(1),1,new CoinCost(1));

        this.currentDeck.addCard(c1);
        this.currentDeck.addCard(c2);
        this.currentDeck.addCard(c3);
        Mockito.when(this.random.nextInt(Mockito.anyInt())).thenReturn(0);
        Mockito.when(this.random.nextBoolean()).thenReturn(false);

        //discardCard
        AbstractAction actionResult=this.randomAI.chooseAction(this.currentDeck, this.playerCoins, this.playerEffects);
        assertNotEquals(BuildAction.class,actionResult.getClass());
        assertNotEquals(BuildStepAction.class,actionResult.getClass());
        assertEquals(DiscardAction.class,actionResult.getClass());
        assertEquals(actionResult.getIndexOfCard(),0);

    }

    @Test
    void chooseActionTestBuildCard (){
        Card c1 = new Card("test1", CardType.CIVIL_BUILDING,new VictoryPointEffect(1),1,new CoinCost(1));
        Card c2 = new Card("test2", CardType.SCIENTIFIC_BUILDINGS,new VictoryPointEffect(1),1,new CoinCost(1));
        Card c3 = new Card("test3", CardType.RAW_MATERIALS,new VictoryPointEffect(1),1,new CoinCost(1));

        this.currentDeck.addCard(c1);
        this.currentDeck.addCard(c2);
        this.currentDeck.addCard(c3);
        Mockito.when(this.random.nextInt(Mockito.anyInt())).thenReturn(1);
        Mockito.when(this.random.nextBoolean()).thenReturn(true);

        AbstractAction actionResult=this.randomAI.chooseAction(this.currentDeck, this.playerCoins, this.playerEffects);

        assertEquals(actionResult.getIndexOfCard(),1);
        assertNotEquals(DiscardAction.class,actionResult.getClass());
        assertNotEquals(BuildStepAction.class,actionResult.getClass());
        assertEquals(BuildAction.class,actionResult.getClass());



    }


    @Test
    void chooseActionTestBuildStep (){
        Card c1 = new Card("test1", CardType.CIVIL_BUILDING,new VictoryPointEffect(1),1,new CoinCost(1));
        Card c2 = new Card("test2", CardType.SCIENTIFIC_BUILDINGS,new VictoryPointEffect(1),1,new CoinCost(1));
        Card c3 = new Card("test3", CardType.RAW_MATERIALS,new VictoryPointEffect(1),1,new CoinCost(1));

        this.currentDeck.addCard(c1);
        this.currentDeck.addCard(c2);
        this.currentDeck.addCard(c3);
        Mockito.when(this.random.nextInt(Mockito.anyInt())).thenReturn(2);
        Mockito.when(this.random.nextBoolean()).thenReturn(true);

        AbstractAction actionResult=this.randomAI.chooseAction(this.currentDeck, this.playerCoins, this.playerEffects);

        assertEquals(actionResult.getIndexOfCard(),2);
        assertNotEquals(DiscardAction.class,actionResult.getClass());
        assertNotEquals(BuildAction.class,actionResult.getClass());
        assertEquals(BuildStepAction.class,actionResult.getClass());

    }

    @Test
    void choosePurchasePossibilityTestDiscard (){
        List<Integer[]> purchaseChoice = new ArrayList<>();
        Mockito.when(this.random.nextBoolean()).thenReturn(true);

        Integer[] choose = this.randomAI.choosePurchasePossibility(purchaseChoice);
        assertEquals(null,choose);

    }

    @Test
    void choosePurchasePossibilityTest (){
        Integer[] purch1 = {0,1};
        Integer[] purch2 = {2,1};

        List<Integer[]> purchaseChoice = new ArrayList<>();
        purchaseChoice.add(purch1);
        purchaseChoice.add(purch2);

        Mockito.when(this.random.nextBoolean()).thenReturn(false);
        Mockito.when(this.random.nextInt(Mockito.anyInt())).thenReturn(1);
        Integer[] choose = this.randomAI.choosePurchasePossibility(purchaseChoice);

        assertEquals(purch2,choose);

    }

    @Test
    void useScientificsGuildEffectTest (){
        WonderBoard wonderBoard = new WonderBoard("Test" , new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,3))));
        Mockito.when(this.random.nextInt(Mockito.anyInt())).thenReturn(0);
        //Geography
        ScientificType scientificType = this.randomAI.useScientificsGuildEffect(wonderBoard);
        assertEquals(ScientificType.GEOGRAPHY, scientificType);

        Mockito.when(this.random.nextInt(Mockito.anyInt())).thenReturn(1);
        this.randomAI = new RandomAI(this.random);
        //geometry
        scientificType = this.randomAI.useScientificsGuildEffect(wonderBoard);
        assertEquals(ScientificType.GEOMETRY, scientificType);

        //Literature
        Mockito.when(this.random.nextInt(Mockito.anyInt())).thenReturn(2);
        this.randomAI = new RandomAI(this.random);

        scientificType = this.randomAI.useScientificsGuildEffect(wonderBoard);
        assertEquals(ScientificType.LITERATURE, scientificType);

    }


    @Test
    void chooseCardTest (){
        Deck deck = new Deck();
        Mockito.when(this.random.nextInt(Mockito.anyInt())).thenReturn(10);

        //Geography
        int choice = this.randomAI.chooseCard(deck);
        assertEquals(10, choice);

        assertEquals("RandomAI",this.randomAI.toString());
    }


}
