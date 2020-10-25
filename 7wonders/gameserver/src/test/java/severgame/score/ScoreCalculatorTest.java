package severgame.score;

import commun.communication.StatObject;
import commun.effect.*;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;

import commun.player.Player;
import commun.wonderboard.WonderBoard;

import commun.wonderboard.WonderStep;
import log.GameLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import commun.card.Card;
import commun.card.CardType;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import servergame.player.PlayerController;
import servergame.score.ScoreCalculator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreCalculatorTest {

    ScoreCalculator scoreCalculator= new ScoreCalculator();
    ArrayList<Player> players = new ArrayList<Player>();

    WonderBoard wonderBoard1 = new WonderBoard("Alexandria", new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.GLASS,1))));
    WonderBoard wonderBoard2 = new WonderBoard("Rhodos",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.ORES,1))));
    WonderBoard wonderBoard3 = new WonderBoard("Gizah",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,1))));
    WonderBoard wonderBoard4 = new WonderBoard("Babylon",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.CLAY,1))));

    Player player1 = new Player("Player1",wonderBoard1);
    Player player2 = new Player("Player2",wonderBoard2);
    Player player3 = new Player("Player3",wonderBoard3);
    Player player4 = new Player("Player4", wonderBoard4);

    EarnWithWonder earnWithWonder1 = new EarnWithWonder(TargetType.ME, 3 ,3);
    EarnWithWonder earnWithWonder2 = new EarnWithWonder(TargetType.ME_AND_NEIGHTBOUR, 3 ,3);

    WonderStep wonderStep = new WonderStep(null,1,new VictoryPointEffect(3));
    WonderStep wonderStep1 = new WonderStep(null,2,new EarnWithWonderEffect(earnWithWonder1));
    WonderStep wonderStep2 = new WonderStep(null,3,new EarnWithWonderEffect(earnWithWonder2));

    ArrayList<WonderStep> wonderSteps = new ArrayList<>();


    @BeforeEach
    void prepare(){
        GameLogger.verbose = false;
        wonderBoard1 = new WonderBoard("Alexandria", new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.GLASS,1))));
        wonderBoard2 = new WonderBoard("Rhodos",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.ORES,1))));
        wonderBoard3 = new WonderBoard("Gizah",new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,1))));
        wonderBoard4 = new WonderBoard("Babylon", new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.CLAY, 1))));

        wonderStep.toBuild();

        wonderSteps.add(wonderStep);
        wonderSteps.add(wonderStep1);
        wonderSteps.add(wonderStep2);

        wonderBoard1.setWonderSteps(wonderSteps);
        wonderBoard2.setWonderSteps(wonderSteps);
        wonderBoard3.setWonderSteps(wonderSteps);
        wonderBoard4.setWonderSteps(wonderSteps);


        wonderBoard1.addCardToBuilding(new Card("CivilBuilding", CardType.CIVIL_BUILDING,new VictoryPointEffect(1),1,null));
        wonderBoard2.addCardToBuilding(new Card("CivilBuilding", CardType.CIVIL_BUILDING,new VictoryPointEffect(3),1,null));
        wonderBoard3.addCardToBuilding(new Card("CivilBuilding", CardType.CIVIL_BUILDING,new VictoryPointEffect(2),1,null));
        wonderBoard4.addCardToBuilding(new Card("CommercialBuilding", CardType.COMMERCIAL_BUILDINGS, new CoinEffect(5),1,null));

        wonderBoard1.addCoin(1);
        wonderBoard2.addCoin(1);
        wonderBoard3.addCoin(1);
        wonderBoard4.addCoin(1);

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
    void getScoreTest()
    {
        assertEquals(7,scoreCalculator.getScore(player2));
        assertEquals(0,scoreCalculator.getScore(new Player("test")));

        wonderBoard2.addCardToBuilding(new Card("CivilBuilding", CardType.CIVIL_BUILDING,new VictoryPointEffect(3),1,null));
        wonderBoard2.addCardToBuilding(new Card("CivilBuilding", CardType.CIVIL_BUILDING,new VictoryPointEffect(1),1,null));
        wonderBoard4.addCardToBuilding(new Card("CommercialBuilding", CardType.COMMERCIAL_BUILDINGS, new CoinEffect(5),1,null));

        assertEquals(scoreCalculator.getScore(player2),11);
        assertEquals(scoreCalculator.getScore(player4), 4);
    }

    @Test
    void computeFinalScore()
    {
        for(Player player : players){
            assertEquals(0,player.getFinalScore());
        }
        //On calcule les score
        List<Player> ranking = scoreCalculator.computeFinalScore(players);

        //Les scores des joueurs sont bien modifiés
        assertEquals(5,players.get(0).getFinalScore());
        assertEquals(7,players.get(1).getFinalScore());
        assertEquals(6,players.get(2).getFinalScore());
        assertEquals(4,players.get(3).getFinalScore());

        //Le tableau ranking est bien classé par score
        assertEquals(7,ranking.get(0).getFinalScore());
        assertEquals(6,ranking.get(1).getFinalScore());
        assertEquals(5,ranking.get(2).getFinalScore());
        assertEquals(4,ranking.get(3).getFinalScore());
    }

    /**
     * Test s'i'l gagne les bons points avec les effets earnwithCard chez lui comme chez les voisin
     */
    @Test
    void earnWithCardScoreTest()
    {
        assertEquals(5,scoreCalculator.getScore(player1));
        int scoreBefore = scoreCalculator.getScore(player1);
        player1.setLeftNeightbour(wonderBoard2);
        player1.setRightNeightbour(wonderBoard3);

        EarnWithCard earnWithCard1 = new EarnWithCard(3,3, TargetType.ME,CardType.CIVIL_BUILDING);
        EarnWithCard earnWithCard2 = new EarnWithCard(3,3, TargetType.BOTH_NEIGHTBOUR,CardType.CIVIL_BUILDING);
        wonderBoard1.addCardToBuilding(new Card("CivilBuilding", CardType.CIVIL_BUILDING,new EarnWithCardEffect(earnWithCard1),1,null));
        wonderBoard1.addCardToBuilding(new Card("CivilBuilding", CardType.CIVIL_BUILDING,new EarnWithCardEffect(earnWithCard2),1,null));

        assertEquals(20,scoreCalculator.getScore(player1));
        assertEquals(scoreBefore + wonderBoard1.countCard(earnWithCard1.getCardType()) * earnWithCard1.getCoinEarn() +
                (wonderBoard2.countCard(earnWithCard2.getCardType()) + wonderBoard3.countCard(earnWithCard2.getCardType()))
                        * earnWithCard2.getCoinEarn(),scoreCalculator.getScore(player1));

    }

    /**
     * Test s'i'l gagne les bons points avec les effets earnwithWonder chez lui comme chez les voisin
     */
    @Test
    void  earnWithWonderScoreTest()
    {
        assertEquals(5,scoreCalculator.getScore(player1));
        int scoreBefore = scoreCalculator.getScore(player1);

        wonderBoard1.addCardToBuilding(new Card("CivilBuilding", CardType.CIVIL_BUILDING, new EarnWithWonderEffect(earnWithWonder1) ,1,null));
        wonderBoard1.addCardToBuilding(new Card("CivilBuilding", CardType.CIVIL_BUILDING, new EarnWithWonderEffect(earnWithWonder2) ,1,null));

        wonderBoard1.getWonderSteps().get(1).toBuild();
        wonderBoard1.getWonderSteps().get(2).toBuild();
        wonderBoard2.getWonderSteps().get(1).toBuild();
        wonderBoard3.getWonderSteps().get(1).toBuild();

        player1.setWonderBoard(wonderBoard1);
        player1.setLeftNeightbour(wonderBoard2);
        player1.setRightNeightbour(wonderBoard3);

        assertEquals(41,scoreCalculator.getScore(player1));
        assertEquals(scoreBefore + player1.getWonderBoard().countStepBuild()*earnWithWonder1.getVictoryPointEarn() +
                player1.getWonderBoard().countStepBuild()*earnWithWonder2.getVictoryPointEarn() +
                (wonderBoard2.countStepBuild()*earnWithWonder2.getVictoryPointEarn() +
                        wonderBoard3.countStepBuild()*earnWithWonder2.getVictoryPointEarn()),scoreCalculator.getScore(player1));

    }

    /**
     * Test s'i'l gagne les bons poits avec les effets scientific
     */
    @Test
    void computeScientificScoreTest()
    {
        assertEquals(5,scoreCalculator.getScore(player1));
        int scoreBefore = scoreCalculator.getScore(player1);
        player1.setLeftNeightbour(wonderBoard2);
        player1.setRightNeightbour(wonderBoard3);

        ScientificEffect scientificEffect1 =  new ScientificEffect(ScientificType.GEOMETRY);
        ScientificEffect scientificEffect2 =  new ScientificEffect(ScientificType.LITERATURE);
        ScientificEffect scientificEffect3 =  new ScientificEffect(ScientificType.GEOGRAPHY);
        ScientificEffect scientificEffect4 =  new ScientificEffect(ScientificType.GEOGRAPHY);


        wonderBoard1.addCardToBuilding(new Card("Scientific", CardType.SCIENTIFIC_BUILDINGS, scientificEffect1,1,null));
        wonderBoard1.addCardToBuilding(new Card("Scientific", CardType.SCIENTIFIC_BUILDINGS, scientificEffect2,1,null));
        wonderBoard1.addCardToBuilding(new Card("Scientific", CardType.SCIENTIFIC_BUILDINGS, scientificEffect3,1,null));
        wonderBoard1.addCardToBuilding(new Card("Scientific", CardType.SCIENTIFIC_BUILDINGS, scientificEffect4,1,null));

        assertEquals(18,scoreCalculator.getScore(player1));
        assertEquals(scoreBefore + 7 + 4 + 1 + 1,scoreCalculator.getScore(player1));
    }

    @Test
    void midGameStatisticsTest()
    {
        StatObject statObject = Mockito.spy(new StatObject());
        statObject.construct(4);
        ArrayList<String> userName = new ArrayList<>();
        userName.add("/");
        userName.add(player1.getName());
        userName.add(player2.getName());
        userName.add(player3.getName());
        userName.add(player4.getName());
        statObject.setUsernames(userName);

        Whitebox.setInternalState(this.scoreCalculator, "statObject", statObject);
        scoreCalculator.midGameStatistics(this.players);

        Mockito.verify(statObject,Mockito.times(10)).getStatByAge(Mockito.anyInt());
        Mockito.verify(statObject,Mockito.times(10)).getCurrentAge();
        Mockito.verify(statObject,Mockito.times(21)).getUsernames();
    }

}
