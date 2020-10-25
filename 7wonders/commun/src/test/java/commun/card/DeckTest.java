package commun.card;

import commun.effect.VictoryPointEffect;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeckTest {

    Deck deck = new Deck();

    @Test
    void generalTest(){
        Card card;

        for(int i =0; i<100;i++){
             card = new Card("test"+i,CardType.CIVIL_BUILDING,new VictoryPointEffect(0),1,null);

             deck.addCard(card);
             assertEquals(i+1,deck.getLength());//La taille a bien augmentée
             assertEquals(deck.getCard(i),card);//La carte est bien la bonne.

        }
        for(int i =0;i<100;i++){
            deck.removeCard(0);
            assertEquals(100-(i+1),deck.getLength());//La taille a bien été reduite.
        }

        for(int i =0;i<5;i++) {
            deck.addCard(new Card("test", null, null, 1, null, "null"));
        }
        assertEquals("[test, test, test, test, test]",deck.toString());

    }
}
