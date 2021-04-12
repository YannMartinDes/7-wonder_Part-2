package servergame;

import commun.request.ID;
import commun.request.RequestPlayerActionCheck;
import commun.request.RequestToPlayer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import servergame.engine.GameEngine;
import servergame.inscription.InscriptionPlayer;
import servergame.player.PlayerController;
import servergame.player.PlayerManager;
import servergame.player.rest.RequestToPlayerRestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class GameInitializerTest {

    InscriptionPlayer inscriptionPlayer = Mockito.mock(InscriptionPlayer.class);

    @Autowired
    GameInitializer gameInitializer = new GameInitializer();;

    @Autowired
    GameEngine gameEngine;

    @Autowired
    PlayerManager pm;

    @Test
    void initGameTest() {

        //init game avec un nombre
        for(int i = 3; i<=7; i++) {
            List<ID> ids = new ArrayList<>();
            for (int j = 0; j <i ; j++) {
                ids.add(new ID("","player :"+j));
            }
            Mockito.when(inscriptionPlayer.waitInscriptionFinish()).thenReturn(ids);
            ReflectionTestUtils.setField(gameInitializer, "inscriptionPlayer", inscriptionPlayer);
            gameInitializer.initGame();
            gameEngine.init(pm);
            assertEquals(i,gameEngine.getNbPlayer());
            //on a bien le bon nombre de joueur dans la parti
        }
    }

    @Test
    void initControllersTest()
    {
        for (int i = 0; i<7;i++){
            List<ID> ids = new ArrayList<>();
            for (int j = 0; j <i ; j++) {
                ids.add(new ID("","1"));
            }

            List<RequestToPlayerRestTemplate> requestToPlayerRestTemplates = gameInitializer.playerBuilder(ids);

            List<PlayerController> controllers = gameInitializer.initControllers(requestToPlayerRestTemplates);
            pm.init(controllers);

            assertEquals(i,controllers.size());//on a bien tout les controller
            for (int j = 0; j<controllers.size();j++){
                //on a bien des ia (sous la couche du checker)
                assertEquals(((RequestPlayerActionCheck)controllers.get(j).getAI()).getIa(),requestToPlayerRestTemplates.get(j));
            }

        }
    }

    @Test
    void playerBuilderTest()
    {
        for (int i = 1; i<7;i++) {
            List<ID> ids = new ArrayList<>();
            for (int j = 0; j <i ; j++) {
                ids.add(new ID("","1"));
            }

            List<RequestToPlayerRestTemplate> requestToPlayerRestTemplates = gameInitializer.playerBuilder(ids);
            assertEquals(requestToPlayerRestTemplates.size(), i);
            for (int j = 0; j <i ; j++) {
                assertEquals(ReflectionTestUtils.getField(requestToPlayerRestTemplates.get(j), "id"), ids.get(j));

            }
        }
    }
}