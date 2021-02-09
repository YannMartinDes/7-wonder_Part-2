package integrationtests;

import client.AI.RandomAI;
import commun.card.Deck;
import commun.player.Player;
import commun.request.RequestPlayerActionCheck;
import log.GameLogger;
import org.junit.Test;
import servergame.card.CardManager;
import servergame.player.PlayerController;
import servergame.player.PlayerManagerImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerManagerImplIT {

    private List<PlayerController> playerControllers = new ArrayList<>();
    private PlayerManagerImpl playerManager;
    private CardManager cardManager = new CardManager(1);


    @Test
    public void chooseAction()
    {
        this.creatControllers(3);
        this.playerManager = new PlayerManagerImpl(playerControllers);
        this.playerManager.assignPlayersWonderBoard();

        assertNull(this.playerManager.getPlayerControllers().get(0).getAction());

        this.playerManager.chooseAction();
        assertNotNull(this.playerManager.getPlayerControllers().get(0).getAction());
    }

    @Test
    public void playAction()
    {
        GameLogger.verbose = false;

        this.creatControllers(3);
        this.playerManager = new PlayerManagerImpl(playerControllers);
        this.playerManager.assignPlayersWonderBoard();
        this.playerManager.assignNeightbours();
        this.playerManager.chooseAction();
        assertNotNull(this.playerManager.getPlayerControllers().get(0).getAction());
        assertFalse(this.playerManager.getPlayerControllers().get(0).getAction().isPlayedAction());
        assertFalse(this.playerManager.getPlayerControllers().get(0).getAction().isFinishAction());

        this.playerManager.playAction(new Deck());
        assertTrue(this.playerManager.getPlayerControllers().get(0).getAction().isPlayedAction());
        assertFalse(this.playerManager.getPlayerControllers().get(0).getAction().isFinishAction());
    }

    @Test
    public void finishAction()
    {
        GameLogger.verbose = false;

        this.creatControllers(3);
        this.playerManager = new PlayerManagerImpl(playerControllers);
        this.playerManager.assignPlayersWonderBoard();
        this.playerManager.assignNeightbours();
        this.playerManager.chooseAction();
        this.playerManager.playAction(new Deck());
        assertTrue(this.playerManager.getPlayerControllers().get(0).getAction().isPlayedAction());
        assertFalse(this.playerManager.getPlayerControllers().get(0).getAction().isFinishAction());

        playerManager.finishAction(new Deck());
        assertTrue(this.playerManager.getPlayerControllers().get(0).getAction().isFinishAction());
    }

    @Test
    public void assignPlayersWonderBoard()
    {
        this.creatControllers(3);
        this.playerManager = new PlayerManagerImpl(playerControllers);
        assertNull(this.playerManager.getPlayerControllers().get(0).getPlayer().getWonderBoard());

        this.playerManager.assignPlayersWonderBoard();
        assertNotNull(this.playerManager.getPlayerControllers().get(0).getPlayer().getWonderBoard());
    }

    @Test
    public void assignNeighbours() {
        this.creatControllers(3);
        this.playerManager = new PlayerManagerImpl(playerControllers);
        this.playerManager.assignPlayersWonderBoard();

        assertNull(this.playerManager.getAllPlayers().get(0).getRightNeightbour());
        assertNull(this.playerManager.getAllPlayers().get(0).getLeftNeightbour());

        this.playerManager.assignNeightbours();
        assertNotNull(this.playerManager.getAllPlayers().get(0).getRightNeightbour());
        assertNotNull(this.playerManager.getAllPlayers().get(0).getLeftNeightbour());

    }

    private void creatControllers(int nb)
    {
        this.playerControllers = new ArrayList<>(nb);
        for (int i = 0; i < nb; i++) {
            Player p = new Player("p"+(i+1), null);
            this.cardManager.createHands(3);
            p.setCurrentDeck(this.cardManager.getHand(0));
            this.playerControllers.add(new PlayerController(p,
                    new RequestPlayerActionCheck(new RandomAI())));
        }
    }

}
