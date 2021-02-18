package severgame.player;

import commun.card.Deck;
import commun.effect.ChoiceMaterialEffect;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import commun.player.Player;
import commun.wonderboard.WonderBoard;
import log.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import servergame.card.CardManager;
import servergame.player.PlayerController;
import servergame.player.PlayerManagerImpl;
import servergame.player.PlayerView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;

/**
 * Gere le test de playerManagerImpl, de playerManager et de PlayerView
 */
class PlayerManagerImplTest {

    @Mock
    PlayerController p1 = Mockito.mock(PlayerController.class);
    @Mock
    PlayerController p2 = Mockito.mock(PlayerController.class);
    @Mock
    PlayerController p3 = Mockito.mock(PlayerController.class);
    @Mock
    PlayerController p4 = Mockito.mock(PlayerController.class);

    @Mock
    Player p = Mockito.mock(Player.class);
    @Mock
    CardManager cardManager = Mockito.mock(CardManager.class);

    PlayerManagerImpl playerManager;

    @BeforeEach
    void init(){
        Logger.logger.verbose = false;
        ArrayList<PlayerController> playerControllers = new ArrayList<>();
        playerControllers.add(p1);
        playerControllers.add(p2);
        playerControllers.add(p3);
        playerControllers.add(p4);

        playerManager = new PlayerManagerImpl(playerControllers);

        Mockito.when(p1.getPlayer()).thenReturn(p);
        Mockito.when(p2.getPlayer()).thenReturn(p);
        Mockito.when(p3.getPlayer()).thenReturn(p);
        Mockito.when(p4.getPlayer()).thenReturn(p);

        Mockito.doNothing().when(p).information();
        Mockito.when(p.getName()).thenReturn("");
        Mockito.when(p.getWonderBoard()).thenReturn(new WonderBoard("test", new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,1)))));

        Mockito.when(cardManager.getHand(anyInt())).thenReturn(new Deck());
    }

    @Test
    void getPlayerTest(){
        List<Player> players = playerManager.getAllPlayers();

        assertEquals(4,players.size());
        Mockito.verify(p1,Mockito.times(1)).getPlayer();
        Mockito.verify(p2,Mockito.times(1)).getPlayer();
        Mockito.verify(p3,Mockito.times(1)).getPlayer();
        Mockito.verify(p4,Mockito.times(1)).getPlayer();

        assertEquals(4,playerManager.getNbPlayer());
    }

    @Test
    void ActionsTest() {

        playerManager.chooseAction();

        Mockito.verify(p1, Mockito.times(1)).chooseAction();
        Mockito.verify(p2, Mockito.times(1)).chooseAction();
        Mockito.verify(p3, Mockito.times(1)).chooseAction();
        Mockito.verify(p4, Mockito.times(1)).chooseAction();

        playerManager.finishAction(new Deck());

        Mockito.verify(p1, Mockito.times(1)).finishAction(new Deck());
        Mockito.verify(p2, Mockito.times(1)).finishAction(new Deck());
        Mockito.verify(p3, Mockito.times(1)).finishAction(new Deck());
        Mockito.verify(p4, Mockito.times(1)).finishAction(new Deck());

        playerManager.informations();

        Mockito.verify(p1, Mockito.times(1)).getPlayer();
        Mockito.verify(p2, Mockito.times(1)).getPlayer();
        Mockito.verify(p3, Mockito.times(1)).getPlayer();
        Mockito.verify(p4, Mockito.times(1)).getPlayer();
        Mockito.verify(p, Mockito.times(4)).information();

        playerManager.initPlayerView();
        Mockito.verify(p1, Mockito.times(1)).setPlayerView(any(PlayerView.class));
        Mockito.verify(p2, Mockito.times(1)).setPlayerView(any(PlayerView.class));
        Mockito.verify(p3, Mockito.times(1)).setPlayerView(any(PlayerView.class));
        Mockito.verify(p4, Mockito.times(1)).setPlayerView(any(PlayerView.class));
    }

    @Test
    void assignTest() {

        playerManager.assignPlayersDeck(cardManager);

        Mockito.verify(p,Mockito.times(4)).setCurrentDeck(any(Deck.class));

        playerManager.assignNeightbours();

        Mockito.verify(p,Mockito.times(4)).setLeftNeightbour(any(WonderBoard.class));
        Mockito.verify(p,Mockito.times(4)).setRightNeightbour(any(WonderBoard.class));

        playerManager.assignPlayersWonderBoard();
        Mockito.verify(p,Mockito.times(4)).setWonderBoard(any(WonderBoard.class));
    }

    @Test
    void neightbourTest(){
        assertEquals(p,playerManager.getLeftNeighbours(p));

        assertEquals(p,playerManager.getRightNeighbours(p));
    }
}
