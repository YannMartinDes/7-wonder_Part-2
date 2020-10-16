package severgame;

import commun.communication.StatObject;
import commun.effect.ChoiceMaterialEffect;
import commun.effect.CoinEffect;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;

import commun.player.Player;
import commun.wonderboard.WonderBoard;

import log.GameLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import commun.card.Card;
import commun.card.CardType;
import commun.effect.VictoryPointEffect;
import servergame.score.ScoreCalculator;

import java.util.ArrayList;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreCalculatorTest {

    ScoreCalculator scoreCalculator= new ScoreCalculator(new StatObject());
    ArrayList<Player> players = new ArrayList<Player>();

    WonderBoard wonderBoard1 = new WonderBoard("Alexandria", new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.GLASS,1))));
    WonderBoard wonderBoard2 = new WonderBoard("Rhodos",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.ORES,1))));
    WonderBoard wonderBoard3 = new WonderBoard("Gizah",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,1))));
    WonderBoard wonderBoard4 = new WonderBoard("Babylon",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.CLAY,1))));

    Player player1 = new Player("Player1",wonderBoard1);
    Player player2 = new Player("Player2",wonderBoard2);
    Player player3 = new Player("Player3",wonderBoard3);
    Player player4 = new Player("Player4", wonderBoard4);

    @BeforeEach
    public void prepare(){
        GameLogger.verbose = false;
        wonderBoard1 = new WonderBoard("Alexandria", new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.GLASS,1))));
        wonderBoard2 = new WonderBoard("Rhodos",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.ORES,1))));
        wonderBoard3 = new WonderBoard("Gizah",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,1))));
        wonderBoard4 = new WonderBoard("Babylon", new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.CLAY, 1))));

        wonderBoard1.addCardToBuilding(new Card("CivilBuilding", CardType.CIVIL_BUILDING,new VictoryPointEffect(1),1,null));
        wonderBoard2.addCardToBuilding(new Card("CivilBuilding", CardType.CIVIL_BUILDING,new VictoryPointEffect(3),1,null));
        wonderBoard3.addCardToBuilding(new Card("CivilBuilding", CardType.CIVIL_BUILDING,new VictoryPointEffect(2),1,null));
        wonderBoard4.addCardToBuilding(new Card("CommercialBuilding", CardType.COMMERCIAL_BUILDINGS, new CoinEffect(5),1,null));

        player1 = new Player("Player1",wonderBoard1);
        player2 = new Player("Player2",wonderBoard2);
        player3 = new Player("Player3",wonderBoard3);
        player4 = new Player("Player4",wonderBoard4);

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
    }

    /**
     * Ce test permet de verifier que la methode getScore calcule le bon score
     */
    @Test
    public void getScoreTest()
    {
        wonderBoard2.addCardToBuilding(new Card("CivilBuilding", CardType.CIVIL_BUILDING,new VictoryPointEffect(3),1,null));
        wonderBoard2.addCardToBuilding(new Card("CivilBuilding", CardType.CIVIL_BUILDING,new VictoryPointEffect(1),1,null));
        wonderBoard4.addCardToBuilding(new Card("CommercialBuilding", CardType.COMMERCIAL_BUILDINGS, new CoinEffect(5),1,null));

        assertEquals(scoreCalculator.getScore(player2),8);
        assertEquals(scoreCalculator.getScore(player4), 1);
    }

    @Test
    public void computeFinalScore(){
        for(Player player : players){
            assertEquals(0,player.getFinalScore());
        }
        //On calcule les score
        List<Player> ranking = scoreCalculator.computeFinalScore(players);

        //Les scores des joueurs sont bien modifiés
        assertEquals(2,players.get(0).getFinalScore());
        assertEquals(4,players.get(1).getFinalScore());
        assertEquals(3,players.get(2).getFinalScore());
        assertEquals(1,players.get(3).getFinalScore());

        //Le tableau ranking est bien classé par score
        assertEquals(4,ranking.get(0).getFinalScore());
        assertEquals(3,ranking.get(1).getFinalScore());
        assertEquals(2,ranking.get(2).getFinalScore());
        assertEquals(1,ranking.get(3).getFinalScore());
    }

}
