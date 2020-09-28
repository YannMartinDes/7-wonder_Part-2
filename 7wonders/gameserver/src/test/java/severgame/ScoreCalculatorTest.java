package severgame;

import servergame.player.Player;
import commun.wonderboard.WonderBoard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import commun.card.Card;
import commun.card.CardType;
import commun.effect.VictoryPointEffect;
import servergame.ScoreCalculator;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreCalculatorTest {

    ScoreCalculator scoreCalculator= new ScoreCalculator();

    WonderBoard wonderBoard1 = new WonderBoard("Alexandria");
    WonderBoard wonderBoard2 = new WonderBoard("Rhodos");
    WonderBoard wonderBoard3 = new WonderBoard("Gizah");


    @AfterEach
    public void clear(){
        wonderBoard1 = new WonderBoard("Alexandria");
        wonderBoard2 = new WonderBoard("Rhodos");
        wonderBoard3 = new WonderBoard("Gizah");
    }

    /**
     * Ce test permet de verifier que la methode getScore calcule le bon score
     */
    @Test
    public void getScoreTest()
    {

        ArrayList<WonderBoard> wonderBoards = new ArrayList<WonderBoard>();
        wonderBoard2.addCardToBuilding(new Card("CivilBuilding", CardType.CIVIL_BUILDING,new VictoryPointEffect(3),1,null));
        wonderBoard2.addCardToBuilding(new Card("CivilBuilding", CardType.CIVIL_BUILDING,new VictoryPointEffect(1),1,null));

        assertEquals(scoreCalculator.getScore(wonderBoard2),4);

    }



    /**
     * Ce test nous permet de verifier si la carte se créer correctement.
     */
    @Test
    public void WinnerTest()
    {

        ArrayList<Player> players = new ArrayList<Player>();

        wonderBoard1.addCardToBuilding(new Card("CivilBuilding", CardType.CIVIL_BUILDING,new VictoryPointEffect(1),1,null));
        wonderBoard2.addCardToBuilding(new Card("CivilBuilding", CardType.CIVIL_BUILDING,new VictoryPointEffect(3),1,null));
        wonderBoard3.addCardToBuilding(new Card("CivilBuilding", CardType.CIVIL_BUILDING,new VictoryPointEffect(1),1,null));

        Player player1 = new Player("Player1",wonderBoard1);
        Player player2 = new Player("Player2",wonderBoard2);
        Player player3 = new Player("Player3",wonderBoard3);

        players.add(player1);
        players.add(player2);
        players.add(player3);

        Player theWinner = scoreCalculator.winner(players);

        assertEquals(theWinner.getName(), "Player2");
    }

    /**
     * Ce test permet de verifier que le classement des joueurs est le bon
     */
    @Test
    public void rankingTest()
    {
        ArrayList<Player> players = new ArrayList<Player>();

        wonderBoard1.addCardToBuilding(new Card("CivilBuilding", CardType.CIVIL_BUILDING,new VictoryPointEffect(1),1,null));
        wonderBoard2.addCardToBuilding(new Card("CivilBuilding", CardType.CIVIL_BUILDING,new VictoryPointEffect(3),1,null));
        wonderBoard3.addCardToBuilding(new Card("CivilBuilding", CardType.CIVIL_BUILDING,new VictoryPointEffect(2),1,null));

        Player player1 = new Player("Player1",wonderBoard1);
        Player player2 = new Player("Player2",wonderBoard2);
        Player player3 = new Player("Player3",wonderBoard3);

        players.add(player1);
        players.add(player2);
        players.add(player3);

        Map<Integer,Player> ranking = scoreCalculator.ranking(players);

        assertEquals(ranking.get(1),player2);
        assertEquals(ranking.get(2),player3);
        assertEquals(ranking.get(3),player1);
    }

}
