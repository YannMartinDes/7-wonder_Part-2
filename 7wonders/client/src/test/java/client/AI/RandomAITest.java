package client.AI;

import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.effect.VictoryPointEffect;
import commun.action.Action;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RandomAITest
{
    private RandomAI randomAI;

    @BeforeEach
    public void init ()
    { this.randomAI = new RandomAI(); }

    @Test
    public void chooseCardFromDeckTest ()
    {
        //Création d'un deck à 5 cartes.
        Deck deck = new Deck();
        deck.addCard(new Card("test1", CardType.CIVIL_BUILDING, new VictoryPointEffect(0),1,null));
        deck.addCard(new Card("test2", CardType.CIVIL_BUILDING, new VictoryPointEffect(0),1,null));
        deck.addCard(new Card("test3", CardType.CIVIL_BUILDING, new VictoryPointEffect(0),1,null));
        deck.addCard(new Card("test4", CardType.CIVIL_BUILDING, new VictoryPointEffect(0),1,null));
        deck.addCard(new Card("test5", CardType.CIVIL_BUILDING, new VictoryPointEffect(0),1,null));

        Action action;
        for(int i = 0; i < 100; i++)
        {
            action = this.randomAI.chooseCardFromDeck(deck);
            int index = -1;
            try {
                Method method = Action.class.getDeclaredMethod("_getIndexCard");
                method.setAccessible(true);
                index = (int) method.invoke(action);
            } catch (Exception e) {
                e.printStackTrace();
            }

            assertNotEquals(index, -1);
            //Index compris entre 0 et la longueur de la liste.
            assertEquals(index < deck.getLength(),true);
            assertEquals(index >= 0,true);
        }
    }
}
