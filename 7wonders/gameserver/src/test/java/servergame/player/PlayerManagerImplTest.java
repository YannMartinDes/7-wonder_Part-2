package servergame.player;

import commun.player.Player;
import commun.request.ID;
import commun.wonderboard.WonderBoard;
import log.Logger;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import servergame.GameInitializer;
import servergame.card.CardManager;
import servergame.engine.GameEngine;
import servergame.inscription.InscriptionPlayer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PlayerManagerImplTest {
    @Autowired
    GameInitializer gi;
    @Autowired
    PlayerManagerImpl playerManager;
    @Autowired
    GameEngine gameEngine;

    InscriptionPlayer inscriptionPlayer = Mockito.mock(InscriptionPlayer.class);

    @Test
    void getPlayerControllers() {
        for(int i = 3;i<7;i++) {
            List<ID> ids = new ArrayList<>();
            for (int j = 0; j <i ; j++) {
                ids.add(new ID("","player"+j));
            }
            Mockito.when(inscriptionPlayer.waitInscriptionFinish()).thenReturn(ids);
            ReflectionTestUtils.setField(gi, "inscriptionPlayer", inscriptionPlayer);
            gi.initGame();
            gameEngine.init(playerManager);
            playerManager = (PlayerManagerImpl) gameEngine.getPlayers();
            assertEquals(i, playerManager.getPlayerControllers().size()); // on a bien un bon nombre de joueur
        }
    }

    @Test
    void getAllPlayers() {
        for(int i = 3;i<7;i++) {

            List<ID> ids = new ArrayList<>();
            for (int j = 0; j <i ; j++) {
                ids.add(new ID("","player"+j));
            }
            Mockito.when(inscriptionPlayer.waitInscriptionFinish()).thenReturn(ids);
            ReflectionTestUtils.setField(gi, "inscriptionPlayer", inscriptionPlayer);
            gi.initGame();
            gameEngine.init(playerManager);
            playerManager = (PlayerManagerImpl) gameEngine.getPlayers();
            assertEquals(i,playerManager.getAllPlayers().size()); // on a bien un bon nombre de joueur
        }
    }

    @Test
    void getNbPlayer() {
        for(int i = 3;i<7;i++) {

            List<ID> ids = new ArrayList<>();
            for (int j = 0; j <i ; j++) {
                ids.add(new ID("","player"+j));
            }
            Mockito.when(inscriptionPlayer.waitInscriptionFinish()).thenReturn(ids);
            ReflectionTestUtils.setField(gi, "inscriptionPlayer", inscriptionPlayer);
            gi.initGame();
            gameEngine.init(playerManager);
            playerManager = (PlayerManagerImpl) gameEngine.getPlayers();
            assertEquals(i,playerManager.getNbPlayer());// on a bien un bon nombre de joueur
        }
    }

    /**
     * Permet de generer une list de playerController mock
     * @param nb le nb de playerController dans la list
     * @return la list
     */
    private List<PlayerController> mockController(int nb){
        List<PlayerController> playerControllers = new ArrayList<>();
        for (int i = 0; i <= nb; i++) {
            PlayerController controller = Mockito.mock(PlayerController.class);

            Player player = Mockito.spy(new Player("testPlayer"+i,new WonderBoard(null,null)));
            Mockito.doNothing().when(player).information();
            Mockito.when(controller.getPlayer()).thenReturn(player);

            playerControllers.add(controller);
        }
        return playerControllers;
    }

    @Test
    void chooseAction() {
        for(int j = 3;j<=7;j++) {
            //init
            List<PlayerController> playerControllers = mockController(j);
            playerManager = new PlayerManagerImpl(playerControllers);

            playerManager.chooseAction();
            // tout les choose action doivent etre appeler 1 fois
            for (int i = 0; i <= j; i++) {
                PlayerController controller = playerControllers.get(i);
                Mockito.verify(controller, Mockito.times(1)).chooseAction();
            }
        }
    }

    @Test
    void playAction() {
        for(int j = 3;j<=7;j++) {
            //init
            List<PlayerController> playerControllers = mockController(j);
            playerManager = new PlayerManagerImpl(playerControllers);

            playerManager.playAction(null);
            // tout les choose action doivent etre appeler 1 fois
            for (int i = 0; i <= j; i++) {
                PlayerController controller = playerControllers.get(i);
                Mockito.verify(controller, Mockito.times(1)).playAction(Mockito.any());
            }
        }
    }

    @Test
    void finishAction() {
        for(int j = 3;j<=7;j++) {
            //init
            List<PlayerController> playerControllers = mockController(j);
            playerManager = new PlayerManagerImpl(playerControllers);

            playerManager.finishAction(null);
            // tout les choose action doivent etre appeler 1 fois
            for (int i = 0; i <= j; i++) {
                PlayerController controller = playerControllers.get(i);
                Mockito.verify(controller, Mockito.times(1)).finishAction(Mockito.any());
            }
        }
    }


    @Test
    void assignPlayersDeck() {
        for(int j = 3;j<=7;j++) {
            CardManager cardManager =new CardManager(j+1);
            cardManager.createHands(1);
            //init
            List<PlayerController> playerControllers = mockController(j);
            playerManager = new PlayerManagerImpl(playerControllers);

            playerManager.assignPlayersDeck(cardManager);
            // tout les choose action doive etre appeler 1 fois et le deck a ca position doit lui etre assigner
            for (int i = 0; i <= j; i++) {
                PlayerController controller = playerControllers.get(i);
                Mockito.verify(controller.getPlayer(), Mockito.times(1)).setCurrentDeck(cardManager.getHand(i));
            }
        }
    }

    @Test
    void assignNeightbours() {
        for(int j = 3;j<=7;j++) {
            //init
            List<PlayerController> playerControllers = mockController(j);
            playerManager = new PlayerManagerImpl(playerControllers);

            playerManager.assignNeightbours();
            // on dois assigner a chaque joueur le voisin de droite et de gauche selon ca possition dans la liste des joueur
            for (int i = 0; i <= j; i++) {
                PlayerController controller = playerControllers.get(i); //TODO corriger
                Mockito.verify(controller.getPlayer(), Mockito.times(1)).setLeftNeightbour(Mockito.any());
                Mockito.verify(controller.getPlayer(), Mockito.times(1)).setRightNeightbour(Mockito.any());
            }
        }
    }

    @Test
    public void getLeftNeighboursTest(){
        //init
        List<PlayerController> playerControllers = mockController(5);
        playerManager = new PlayerManagerImpl(playerControllers);

        //CAS PARTICULIER
        Player p = playerControllers.get(0).getPlayer();
        Player target = playerControllers.get(5).getPlayer();

        assertEquals(target,playerManager.getLeftNeighbours(p));

        //REGULAR
        for(int i =1; i<6;i++){
            p = playerControllers.get(i).getPlayer();
            target = playerControllers.get(i-1).getPlayer();

            assertEquals(target,playerManager.getLeftNeighbours(p));
        }
    }

    @Test
    public void getRightNeighboursTest(){
        //init
        List<PlayerController> playerControllers = mockController(5);
        playerManager = new PlayerManagerImpl(playerControllers);

        Player p;
        Player target;

        //REGULAR
        for(int i =0; i<5;i++){
            p = playerControllers.get(i).getPlayer();
            target = playerControllers.get(i+1).getPlayer();

            assertEquals(target,playerManager.getRightNeighbours(p));
        }
    }

    @Test
    public void informationTest(){
        //init
        List<PlayerController> playerControllers = mockController(5);
        playerManager = new PlayerManagerImpl(playerControllers);

        Logger.logger.verbose = false;
        playerManager.informations();

        for (int i = 0; i <= 5; i++) {
            PlayerController controller = playerControllers.get(i);
            Mockito.verify(controller.getPlayer(), Mockito.times(1)).information();
        }
    }

    @Test
    public void assignPlayerWonderBoardTest(){
        for(int j = 3;j<=5;j++) {
            //init
            List<PlayerController> playerControllers = mockController(j+1);
            playerManager = new PlayerManagerImpl(playerControllers);

            playerManager.assignPlayersWonderBoard();
            for (int i = 0; i <= j; i++) {
                PlayerController controller = playerControllers.get(i);
                Mockito.verify(controller.getPlayer(), Mockito.times(1)).setWonderBoard(Mockito.any());
            }
        }
    }

    @Test
    public void initPlayerViewTest(){
        for(int j = 3;j<=5;j++) {
            //init
            List<PlayerController> playerControllers = mockController(j+1);
            playerManager = new PlayerManagerImpl(playerControllers);

            playerManager.initPlayerView();
            for (int i = 0; i <= j; i++) {
                PlayerController controller = playerControllers.get(i);
                Mockito.verify(controller, Mockito.times(1)).setPlayerView(Mockito.any());
            }
        }
    }
}