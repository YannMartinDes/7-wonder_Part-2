package client.AI;

import commun.action.ActionType;
import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.cost.CoinCost;
import commun.effect.EffectList;
import commun.effect.VictoryPointEffect;
import commun.action.Action;
import commun.wonderboard.WonderStep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class RandomAITest
{
    private RandomAI randomAI;
    private Deck currentDeck;
    private int playerCoins;
    private EffectList playerEffects;
    private Random random;
    private WonderStep[] wonderSteps= new WonderStep[3];

    @BeforeEach
    public void init ()
    {
        this.random = Mockito.mock(Random.class);
        this.currentDeck = new Deck();
        this.playerCoins = 0;
        this.playerEffects = new EffectList();
        this.randomAI = Mockito.mock(RandomAI.class);

    }

    @Test
    public void chooseActionTestDiscardCard (){
        Card c1 = new Card("test1", CardType.CIVIL_BUILDING,new VictoryPointEffect(1),1,new CoinCost(1));
        Card c2 = new Card("test2", CardType.SCIENTIFIC_BUILDINGS,new VictoryPointEffect(1),1,new CoinCost(1));
        Card c3 = new Card("test3", CardType.RAW_MATERIALS,new VictoryPointEffect(1),1,new CoinCost(1));

        this.currentDeck.addCard(c1);
        this.currentDeck.addCard(c2);
        this.currentDeck.addCard(c3);
        Action action = new Action(ActionType.DISCARD,2);
        Mockito.when(this.randomAI.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(action);


        Action actionResult=this.randomAI.chooseAction(this.currentDeck, this.playerCoins, this.playerEffects);
        assertEquals(actionResult,action);
        assertEquals(actionResult.getIndexOfCard(),2);
        assertNotEquals(actionResult,new Action(ActionType.DISCARD,0));
        assertNotEquals(actionResult,new Action(ActionType.DISCARD,1));
        assertNotEquals(actionResult,new Action(ActionType.BUILD,2));

    }

    @Test
    public void chooseActionTestBuildCard (){
        Card c1 = new Card("test1", CardType.CIVIL_BUILDING,new VictoryPointEffect(1),1,new CoinCost(1));
        Card c2 = new Card("test2", CardType.SCIENTIFIC_BUILDINGS,new VictoryPointEffect(1),1,new CoinCost(1));
        Card c3 = new Card("test3", CardType.RAW_MATERIALS,new VictoryPointEffect(1),1,new CoinCost(1));

        this.currentDeck.addCard(c1);
        this.currentDeck.addCard(c2);
        this.currentDeck.addCard(c3);
        Action action = new Action(ActionType.BUILD,2);
        Mockito.when(this.randomAI.chooseAction(Mockito.any(Deck.class), Mockito.any(Integer.class), Mockito.any(EffectList.class))).thenReturn(action);


        Action actionResult=this.randomAI.chooseAction(this.currentDeck, this.playerCoins, this.playerEffects);
        assertEquals(actionResult,action);
        assertEquals(actionResult.getIndexOfCard(),2);
        assertNotEquals(actionResult,new Action(ActionType.BUILD,0));
        assertNotEquals(actionResult,new Action(ActionType.BUILD,1));
        assertNotEquals(actionResult,new Action(ActionType.BUILD,2));

    }
}
