package commun.player;

import commun.card.Card;
import commun.card.Deck;
import commun.wonderboard.WonderBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    Player player;
    Player leftN;
    Player rightN;

    WonderBoard wonderBoard = Mockito.mock(WonderBoard.class);

    @BeforeEach
    public void init(){
        player = new Player("Test",wonderBoard);

        leftN = new Player("Left",new WonderBoard("wL",null));
        rightN = new Player("Right",new WonderBoard("wR",null));

        player.setRightNeightbour(rightN.getWonderBoard());
        player.setLeftNeightbour(leftN.getWonderBoard());
    }


    @Test
    public void getterSetterTest(){
        assertEquals("Test",player.getName());
        assertEquals(wonderBoard,player.getWonderBoard());

        player.setFinalScore(700);
        assertEquals(700, player.getFinalScore());

        Deck deck = new Deck();
        deck.addCard(new Card("c1", null, null, 1, null, ""));
        deck.addCard(new Card("c2", null, null, 1, null, ""));

        player.setCurrentDeck(deck);
        assertEquals("c1", player.getCurrentDeck().getCard(0).getName());
        assertEquals("c2", player.getCurrentDeck().getCard(1).getName());

        assertEquals("wL",player.getLeftNeightbour().getWonderName());
        assertEquals("wR",player.getRightNeightbour().getWonderName());
    }

    @Test
    public void compareToTest(){
        leftN.setFinalScore(40);
        rightN.setFinalScore(50);
        player.setFinalScore(42);

        assertEquals(2, player.compareTo(leftN));
        assertEquals(-8, player.compareTo(rightN));
    }
}
