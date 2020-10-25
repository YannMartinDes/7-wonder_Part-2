package servergame;

import client.AI.AI;
import client.AI.FirstAI;
import client.AI.RandomAI;
import client.AI.SecondAI;
import commun.player.Player;
import commun.utils.SingletonRandom;
import servergame.engine.GameEngine;
import servergame.player.PlayerController;
import servergame.player.PlayerManagerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameInitializer {
    String[] names = new String[]{"Sardoche", "Paf le chien", "AngryNerd", "Alan Turing", "Hamilton", "Chuck Norris", "Furious Kid"};
    Random random = SingletonRandom.getInstance();
    //nombre d'ia implementer
    private final int NUMBER_IA = 3;

    public GameEngine initGame(int numberPlayer){
        return initGame(generateRandomAi(numberPlayer));
    }

    public GameEngine initGame(List<AI> listAi){
        List<PlayerController> controllers = initControllers(listAi);
        return new GameEngine(new PlayerManagerImpl(controllers));
    }


    /**
     * Permet de generer une list d'ia random
     * @param number le nombre d'ia a generer
     * @return une list d'ia random
     */
    protected List<AI> generateRandomAi(int number){
        ArrayList<AI> listAi = new ArrayList<>(number);
        for(int i = 0; i<number; i++){
            int selected = random.nextInt(NUMBER_IA);
            AI randomAi = null;
            switch (selected){
                case 0:
                    randomAi = new RandomAI();
                    break;
                case 1:
                    randomAi = new FirstAI();
                    break;
                case 2 :
                    randomAi = new SecondAI();
                    break;
            }
            listAi.add(randomAi);
        }
        return listAi;
    }

    /**
     * Permet d'initialiser la list des controller
     * @param listAi les ia que l'on veut pouvoir gerer
     * @return la list des controller
     */
    protected List<PlayerController> initControllers(List<AI> listAi){
        ArrayList<PlayerController> allPlayers = new ArrayList<PlayerController>();
        for(int i = 0; i<listAi.size(); i++){
            PlayerController controller = new PlayerController(new Player(names[i]),listAi.get(i));
            listAi.get(i).setRequestGame(controller);
            allPlayers.add(controller);
        }
        return allPlayers;
    }
}
