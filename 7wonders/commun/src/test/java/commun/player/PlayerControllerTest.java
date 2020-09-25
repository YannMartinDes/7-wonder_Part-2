package commun.player;

import client.iainterface.AI;
import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.effect.VictoryPointEffect;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class PlayerControllerTest {

    @Mock
    AI ai = Mockito.mock(AI.class);
    PlayerController playerController = new PlayerController(ai);

    @Test
    public void chooseCardFromDeckTest(){
        Deck deck = new Deck();
        for(int i=0;i<100;i++){//DECK DE 10 CARTES
            deck.addCard(new Card("test"+i, CardType.CIVIL_BUILDING,new VictoryPointEffect(i),i));
        }

        int index;
        for(int j=0;j<100;j++){
            Mockito.when(ai.chooseCardFromDeck(any(Deck.class))).thenReturn(j);
            index = playerController.chooseCardFromDeck(deck);
            assertEquals(index,j);
        }
    }
}
