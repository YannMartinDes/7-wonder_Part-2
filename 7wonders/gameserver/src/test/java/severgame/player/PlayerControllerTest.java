package severgame.player;

import client.AI.AI;
import commun.action.Action;
import commun.action.BuildAction;
import commun.action.DiscardAction;
import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.effect.VictoryPointEffect;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import servergame.player.PlayerController;


import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class PlayerControllerTest {

    @Mock
    AI ai = Mockito.mock(AI.class);
    PlayerController playerController = new PlayerController(ai);

    @Test
    public void chooseCardFromDeckTest ()
    {
        Deck deck = new Deck();
        for (int i = 0; i < 100; i++)
        { //DECK DE 10 CARTES
            deck.addCard(new Card("test"+i, CardType.CIVIL_BUILDING,new VictoryPointEffect(i),i,null));
        }

        Action action;
        for (int j = 0; j < 100; j++)
        {
            action = new BuildAction(j);
            Mockito.when(ai.chooseCardFromDeck(Mockito.any(Deck.class))).thenReturn(action);

            int index = -1;
            try {
                Method method = Action.class.getDeclaredMethod("_getIndexCard");
                method.setAccessible(true);
                index = (int) method.invoke(action);
            } catch (Exception e) {
                e.printStackTrace();
            }

            playerController.chooseCardFromDeck(deck);
            assertEquals(index,j);

            /*  */

            action = new DiscardAction(j);
            Mockito.when(ai.chooseCardFromDeck(Mockito.any(Deck.class))).thenReturn(action);

            index = -1;
            try {
                Method method = Action.class.getDeclaredMethod("_getIndexCard");
                method.setAccessible(true);
                index = (int) method.invoke(action);
            } catch (Exception e) {
                e.printStackTrace();
            }

            playerController.chooseCardFromDeck(deck);
            assertEquals(index,j);
        }
    }
}
