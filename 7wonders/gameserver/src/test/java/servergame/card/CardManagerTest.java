package servergame.card;

import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.effect.VictoryPointEffect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CardManagerTest {

    @Autowired
    CardManager cardManager;

    @Test
    void createHandsTest ()
    {
        for (int i = 3; i <= 7; i++)
        {
            this.createHandsTestBody(i);
        }
    }

    void createHandsTestBody(int nbPlayer){
        this.cardManager.setNumberPlayer(nbPlayer);

        cardManager.createHands(1);//AGE1

        int nbCardByPlayer = Math.min(new CardFactory().AgeOneCards(7).getLength() / nbPlayer, 7); //on distribue un maximume de carte a chaque personne

        assertEquals(nbPlayer, cardManager.getHands().size());//On a 4 joueurs (fixe pour le moment).

        for(int i = 0; i < nbPlayer; i++){
            //// (pour le moment moins de 7 car pas toutes les carte sont implementer)
            assertEquals(nbCardByPlayer, cardManager.getHand(i).getLength());//on a bien le meme nombre de carte pour chaque joueur
            assertTrue(cardManager.getHand(i).getLength() <= 7);//Dans le jeu les main commence avec 7 carte au maximum
        }
    }


    @Test
    void rotateHandsTest(){
        Deck deck1 = new Deck();
        deck1.addCard(new Card("test1", CardType.CIVIL_BUILDING,new VictoryPointEffect(0),1,null));

        Deck deck2 = new Deck();
        deck2.addCard(new Card("test2", CardType.CIVIL_BUILDING,new VictoryPointEffect(0),1,null));

        Deck deck3 = new Deck();
        deck3.addCard(new Card("test3", CardType.CIVIL_BUILDING,new VictoryPointEffect(0),1,null));

        Deck deck4 = new Deck();
        deck4.addCard(new Card("test4", CardType.CIVIL_BUILDING,new VictoryPointEffect(0),1,null));

        ArrayList<Deck> decks = new ArrayList<Deck>();
        decks.add(deck1);
        decks.add(deck2);
        decks.add(deck3);
        decks.add(deck4);

        //SENS ANTI HORAIRE
        cardManager.rotateHands(false, decks);

        assertEquals(decks.get(0).getCard(0).getName(), "test2");
        assertEquals(decks.get(1).getCard(0).getName(), "test3");
        assertEquals(decks.get(2).getCard(0).getName(), "test4");
        assertEquals(decks.get(3).getCard(0).getName(), "test1");

        for(int i = 0; i <3; i++){//ON FINIS LE TOUR COMPLET.
            cardManager.rotateHands(false, decks);
        }

        assertEquals(decks.get(0).getCard(0).getName(), "test1");
        assertEquals(decks.get(1).getCard(0).getName(), "test2");
        assertEquals(decks.get(2).getCard(0).getName(), "test3");
        assertEquals(decks.get(3).getCard(0).getName(), "test4");

        //SENS HORAIRE

        cardManager.rotateHands(true, decks);


        assertEquals(decks.get(0).getCard(0).getName(), "test4");
        assertEquals(decks.get(1).getCard(0).getName(), "test1");
        assertEquals(decks.get(2).getCard(0).getName(), "test2");
        assertEquals(decks.get(3).getCard(0).getName(), "test3");

        for(int i = 0; i <3; i++){//ON FINIS LE TOUR COMPLET.
            cardManager.rotateHands(true, decks);
        }

        assertEquals(decks.get(0).getCard(0).getName(), "test1");
        assertEquals(decks.get(1).getCard(0).getName(), "test2");
        assertEquals(decks.get(2).getCard(0).getName(), "test3");
        assertEquals(decks.get(3).getCard(0).getName(), "test4");
    }
}
