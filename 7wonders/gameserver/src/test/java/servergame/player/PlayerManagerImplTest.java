package servergame.player;

import commun.player.Player;
import commun.wonderboard.WonderBoard;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import servergame.GameInitializer;
import servergame.card.CardManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerManagerImplTest {
    PlayerManagerImpl playerManager;

    @Test
    void getPlayerControllers() {
        for(int i = 3;i<7;i++) {
            playerManager = (PlayerManagerImpl) new GameInitializer().initGame(i).getPlayers();
            assertEquals(i,playerManager.getPlayerControllers().size()); // on a bien un bon nombre de joueur
        }
    }

    @Test
    void getAllPlayers() {
        for(int i = 3;i<7;i++) {
            playerManager = (PlayerManagerImpl) new GameInitializer().initGame(i).getPlayers();
            assertEquals(i,playerManager.getAllPlayers().size()); // on a bien un bon nombre de joueur
        }
    }

    @Test
    void getNbPlayer() {
        for(int i = 3;i<7;i++) {
            playerManager = (PlayerManagerImpl) new GameInitializer().initGame(i).getPlayers();
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
            PlayerController controller = new Mockito().mock(PlayerController.class);
            Player player = new Player(null,new WonderBoard(null,null));
            Mockito.when(controller.getPlayer()).thenReturn(player);
            playerControllers.add(new Mockito().mock(PlayerController.class));
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
            CardManager cardManager =new CardManager(j);
            cardManager.createHands(1);
            //init
            List<PlayerController> playerControllers = mockController(j);
            playerManager = new PlayerManagerImpl(playerControllers);

//            playerManager.assignPlayersDeck(cardManager);
//            // tout les choose action doive etre appeler 1 fois et le deck a ca position doit lui etre assigner
//            for (int i = 0; i <= j; i++) {
//                PlayerController controller = playerControllers.get(i);
//                Mockito.verify(controller.getPlayer(), Mockito.times(1)).setCurrentDeck(cardManager.getHand(i));
//            }
        }
    }

    @Test
    void assignNeightbours() {
        for(int j = 3;j<=7;j++) {
            //init
            List<PlayerController> playerControllers = mockController(j);
            playerManager = new PlayerManagerImpl(playerControllers);

            //playerManager.assignNeightbours();
            // on dois assigner a chaque joueur le voisin de droite et de gauche selon ca possition dans la liste des joueur
            for (int i = 0; i <= j; i++) {
                PlayerController controller = playerControllers.get(i); //TODO corriger
//                Mockito.verify(controller.getPlayer(), Mockito.times(1)).setLeftNeightbour(Mockito.any());
//                Mockito.verify(controller.getPlayer(), Mockito.times(1)).setRightNeightbour(Mockito.any());
            }
        }
    }

}