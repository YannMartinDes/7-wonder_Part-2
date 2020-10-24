package servergame;

import client.AI.AI;
import client.AI.FirstAI;
import client.AI.RandomAI;
import commun.request.RequestPlayerActionCheck;
import commun.request.RequestToPlayer;
import org.junit.jupiter.api.Test;
import servergame.engine.GameEngine;
import servergame.player.PlayerController;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameInitializerTest {
    GameInitializer gameInitializer = new GameInitializer();;

    @Test
    void initGameTest() {
        GameEngine gameEngine;
        //init game avec un nombre
        for(int i = 3; i<=7; i++) {

            gameEngine = gameInitializer.initGame(i);
            assertEquals(i,gameEngine.getNbPlayer());
            //on a bien le bon nombre de joueur dans la parti
        }

        //ia generer manuellement pour les stat
        List<AI> ai = new ArrayList<>(4);
        ai.add(new RandomAI());
        ai.add(new RandomAI());
        ai.add(new FirstAI());
        ai.add(new FirstAI());
        gameEngine = gameInitializer.initGame(ai);
        assertEquals(4,gameEngine.getNbPlayer());
        //on a bien le bon nombre de joueur dans la parti
    }



    @Test
    void generateRandomAiTest() {
        for (int i = 0; i<100;i++){
            List<AI > ai = gameInitializer.generateRandomAi(i);
            for (int j = 0; j<ai.size();j++){
                assertTrue(ai!=null);//on a bien des ia
            }
            assertEquals(i,ai.size());//on a bien le bon nombre d'ia

        }
    }

    @Test
    void initControllers(){
        for (int i = 0; i<7;i++){
            List<AI > ai = gameInitializer.generateRandomAi(i);
            List<PlayerController> controllers = gameInitializer.initControllers(ai);

            assertEquals(i,controllers.size());//on a bien tout les controller
            for (int j = 0; j<controllers.size();j++){
                //on a bien des ia (sous la couche du checker)
                assertEquals(((RequestPlayerActionCheck)controllers.get(j).getAI()).getIa(),ai.get(j));
            }

        }
    }
}