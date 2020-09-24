package client.AI;

import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.effect.VictoryPointEffect;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomAITest {
    RandomAI randomAI = new RandomAI();

    @Test
    public void chooseCardFromDeckTest(){
        //Création d'un deck à 5 cartes.
        Deck deck = new Deck();
        deck.addCard(new Card("test1",CardType.CIVIL_BUILDING,new VictoryPointEffect(0),1));
        deck.addCard(new Card("test2",CardType.CIVIL_BUILDING,new VictoryPointEffect(0),1));
        deck.addCard(new Card("test3",CardType.CIVIL_BUILDING,new VictoryPointEffect(0),1));
        deck.addCard(new Card("test4",CardType.CIVIL_BUILDING,new VictoryPointEffect(0),1));
        deck.addCard(new Card("test5",CardType.CIVIL_BUILDING,new VictoryPointEffect(0),1));

        int index;
        for(int i = 0; i<100;i++){
            index= randomAI.chooseCardFromDeck(deck);

            //Index compris entre 0 et la longueur de la liste.
            assertEquals(index < deck.getLength(),true);
            assertEquals(index >= 0,true);
        }
    }
}
