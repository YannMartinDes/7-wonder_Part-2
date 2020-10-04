package severgame.engine;

import commun.card.Deck;

import commun.wonderboard.WonderBoard;
import io.cucumber.java8.De;
import log.GameLogger;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import servergame.card.CardFactory;
import servergame.card.CardManager;
import servergame.engine.GameEngine;
import servergame.player.Player;
import servergame.player.PlayerController;
import servergame.wonderboard.WonderBoardFactory;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class GameEngineTest
{
    private GameEngine gameEngine;

    private int nbPlayer;
    private List<Player> allPlayers;
    private int nbAge;
    private int currentAge;

    @Mock
    CardManager  cardManager = Mockito.mock(CardManager.class);



    /** Dependances */
    private WonderBoardFactory wonderBoardFactory;

    @BeforeEach
    public void init ()
    {
        GameLogger.verbose = false;
        this.nbPlayer = 3;
        this.allPlayers = new ArrayList<Player>();
        this.allPlayers.add(new Player("Nom1"));
        this.allPlayers.add(new Player("Nom2"));
        this.allPlayers.add(new Player("Nom3"));
        this.cardManager = new CardManager(this.nbPlayer);
        this.nbAge = 1;
        this.currentAge = 1;
    }


    /**
     * Pour tester assignPlayersWonderBoard on essaye de calculer le nombre de fois que allPlayers.get(i).setWonderBoard(wonders.get(i)) est appele
     * Sauf que assignPlayersWonderBoard est un private void
     * ArrayList<WonderBoard> wonders est local et donc ne peut etre modifie
     * allPlayers.get(i).setWonderBoard retourne un void et ne peut etre teste
     * wonders.get(i) n'est pas testable
     * allPlayers.get(i) est testable
     */
    @Test
    public void testAssignPlayersWonderBoard () throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        /** Mise en place du test */
        this.allPlayers = new ArrayList<Player>();

        Player p = new Player("NomTest");

        for(int i =0;i<3;i++){
            p = new Player("Nom"+i);
            p = Mockito.spy(p);
            doNothing().when(p).chooseAction();
            doNothing().when(p).playAction(null);
            doNothing().when(p).finishAction(Mockito.any(Deck.class));
            doNothing().when(p).afterAction();
            this.allPlayers.add(p);
        }

        this.currentAge = this.nbAge + 1; // Empecher l'appel de la boucle de gameLoop()
        this.allPlayers = Mockito.spy(this.allPlayers);
        this.gameEngine = new GameEngine(this.nbPlayer, this.allPlayers, this.cardManager, this.nbAge, this.currentAge);
        this.gameEngine = Mockito.spy(this.gameEngine);

        /** Lancer assignPlayersWonderBoard */
        Method method = GameEngine.class.getDeclaredMethod("assignPlayersWonderBoard");
        method.setAccessible(true);
        method.invoke(gameEngine);

        /** Tests */
        // Parcourons-nous tous les joueurs ?
        Mockito.verify(this.allPlayers, Mockito.times(this.nbPlayer)).get(Mockito.any(Integer.class));
        /* verifier que setWonderBoard  a bien eté lancer */
        Mockito.verify(p).setWonderBoard(Mockito.any(WonderBoard.class));
    }


    @Test
    public void testRound () throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        this.allPlayers = new ArrayList<Player>();
        Player p = new Player("NomTest");

        for(int i =0;i<3;i++){
            p = new Player("Nom"+i);
            p = Mockito.spy(p);
            doNothing().when(p).chooseAction();
            doNothing().when(p).playAction(null);
            doNothing().when(p).finishAction(Mockito.any(Deck.class));
            doNothing().when(p).afterAction();
            doNothing().when(p).information();
            Mockito.when(p.getWonderBoard()).thenReturn(new WonderBoard("test",null));
            this.allPlayers.add(p);
        }

        this.cardManager = Mockito.spy(this.cardManager);

        this.gameEngine = new GameEngine(this.nbPlayer, this.allPlayers, this.cardManager, this.nbAge, this.currentAge);
        this.gameEngine = Mockito.spy(this.gameEngine);

        /* Lancer le round */
        Method method = GameEngine.class.getDeclaredMethod("round");
        method.setAccessible(true);
        method.invoke(gameEngine);

        /* verifier que rotateHands a bien eté lancer */
        Mockito.verify(this.cardManager).rotateHands(Mockito.anyBoolean());

        /* verifier que chooseAction  a bien eté lancer */
        Mockito.verify(p).chooseAction();

        /* verifier que playAction  a bien eté lancer */
        Mockito.verify(p).playAction(null);

    }


    /**
     * Pour tester assignPlayersDeck on essaye de calculer le nombre de fois que allPlayers.get(i).setCurrentDeck(cardManager.getHand(i)) est appele
     * Sauf que assignPlayersDeck est un private void
     * allPlayers.get(i).setCurrentDeck retourne un void et ne peut etre teste
     * cardManager.getHand(i) est testable
     * allPlayers.get(i) est testable
     */
    @Test
    public void testAssignPlayersDeck () throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        /** Mise en place du test */
        this.currentAge = 1;
        this.nbAge = 1;
        this.allPlayers = Mockito.spy(this.allPlayers);
        this.cardManager = Mockito.mock(CardManager.class);

        this.allPlayers = new ArrayList<Player>();

        Player p = new Player("NomTest");

        for(int i =0;i<3;i++){
            p = new Player("Nom"+i);
            p = Mockito.spy(p);
            doNothing().when(p).chooseAction();
            doNothing().when(p).playAction(null);
            doNothing().when(p).finishAction(Mockito.any(Deck.class));
            doNothing().when(p).afterAction();
            this.allPlayers.add(p);
        }

        this.allPlayers = Mockito.spy(this.allPlayers);

        this.gameEngine = new GameEngine(this.nbPlayer, this.allPlayers, this.cardManager, this.nbAge, this.currentAge);
        this.gameEngine = Mockito.spy(this.gameEngine);
        Mockito.when(cardManager.getHand(Mockito.anyInt())).thenReturn(Mockito.any(Deck.class));

        CardFactory cardFactory = new CardFactory();
        /** Calcul du nombre de decks possible au total */
        int nbCartesAgeUn = cardFactory.AgeOneCards(7).getLength();
        // int nbCartesAgeX...

        /** Nombre de cartes par age ici */
        int[] nbCartes = new int[]{nbCartesAgeUn};

        int predictionNombreDAppelsDeAssignPlayersDeck;
        predictionNombreDAppelsDeAssignPlayersDeck = Arrays.stream(nbCartes).sum() / this.nbPlayer;

        /** Lancer AssignPlayersDeck */
        Method method = GameEngine.class.getDeclaredMethod("assignPlayersDeck");
        method.setAccessible(true);
        method.invoke(gameEngine);

        /** Tests */
        //  on appel this.nbPlayer
        Mockito.verify(this.allPlayers, Mockito.times(this.nbPlayer )).get(Mockito.any(Integer.class));
        // +15 a cause des appels sur CardManager: rotateHands, etc
        Mockito.verify(this.cardManager, Mockito.times(this.nbPlayer )).getHand(Mockito.any(Integer.class));

        /* verifier que rotateHands a bien eté lancer */
        Mockito.verify(p).setCurrentDeck(Mockito.any(Deck.class));

        /* verifier que getHand a bien eté appeler */
        Mockito.verify(cardManager, Mockito.times(this.nbPlayer)).getHand(Mockito.anyInt());
    }

}
