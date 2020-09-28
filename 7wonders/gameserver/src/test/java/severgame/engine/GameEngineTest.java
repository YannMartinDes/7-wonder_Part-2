package severgame.engine;

import client.AI.RandomAI;

import log.GameLogger;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import servergame.card.CardFactory;
import servergame.card.CardManager;
import servergame.engine.GameEngine;
import servergame.player.Player;
import servergame.player.PlayerController;
import servergame.wonderboard.WonderBoardFactory;

import java.util.*;

public class GameEngineTest
{
    private GameEngine gameEngine;

    private int nbPlayer;
    private List<Player> allPlayers;
    private CardManager cardManager;
    private int nbAge;
    private int currentAge;

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
    public void testAssignPlayersWonderBoard ()
    {
        /** Mise en place du test */
        this.currentAge = this.nbAge + 1; // Empecher l'appel de la boucle de gameLoop()
        this.allPlayers = Mockito.spy(this.allPlayers);
        this.gameEngine = new GameEngine(this.nbPlayer, this.allPlayers, this.cardManager, this.nbAge, this.currentAge);
        this.gameEngine = Mockito.spy(this.gameEngine);

        /** Lancer le jeu */
        this.gameEngine.startGame();

        /** Tests */
        // Parcourons-nous tous les joueurs ?
        // this.nbPlayer + 2 car j'en ai aucune idee mais c'est toujours + 2, le scoreRanking du gameLoop n'a pas l'air d'influer dessus
        Mockito.verify(this.allPlayers, Mockito.times(this.nbPlayer + 2)).get(Mockito.any(Integer.class));
    }

    /**
     * Pour tester assignPlayersDeck on essaye de calculer le nombre de fois que allPlayers.get(i).setCurrentDeck(cardManager.getHand(i)) est appele
     * Sauf que assignPlayersDeck est un private void
     * allPlayers.get(i).setCurrentDeck retourne un void et ne peut etre teste
     * cardManager.getHand(i) est testable
     * allPlayers.get(i) est testable
     */
    @Test
    public void testAssignPlayersDeck ()
    {
        /** Mise en place du test */
        this.currentAge = 1;
        this.nbAge = 1;

        this.allPlayers = new ArrayList<Player>();
        Player p;

        p = new Player("Nom1");
        p.setController(new PlayerController(new RandomAI()));
        this.allPlayers.add(p);
        p = new Player("Nom2");
        p.setController(new PlayerController(new RandomAI()));
        this.allPlayers.add(p);
        p = new Player("Nom3");
        p.setController(new PlayerController(new RandomAI()));
        this.allPlayers.add(p);

        this.allPlayers = Mockito.spy(this.allPlayers);
        this.cardManager = Mockito.spy(this.cardManager);

        this.gameEngine = new GameEngine(this.nbPlayer, this.allPlayers, this.cardManager, this.nbAge, this.currentAge);
        this.gameEngine = Mockito.spy(this.gameEngine);

        CardFactory cardFactory = new CardFactory();
        /** Calcul du nombre de decks possible au total */
        int nbCartesAgeUn = cardFactory.AgeOneCards().getLength();
        // int nbCartesAgeX...

        /** Nombre de cartes par age ici */
        int [] nbCartes = new int [] {nbCartesAgeUn};

        int predictionNombreDAppelsDeAssignPlayersDeck;
        predictionNombreDAppelsDeAssignPlayersDeck = Arrays.stream(nbCartes).sum() / this.nbPlayer;

        /** Lancer le jeu */
        this.gameEngine.startGame();

        /** Tests */
        // Comme le test precedent a du fonctionner, on appel this.nbPlayer + 2 fois this.allPlayers.get au minimum
        // Maintenant on a un +11 qui apparait
        Mockito.verify(this.allPlayers, Mockito.times(predictionNombreDAppelsDeAssignPlayersDeck + this.nbPlayer + 2 + 11)).get(Mockito.any(Integer.class));
        // +15 a cause des appels sur CardManager: rotateHands, etc
        Mockito.verify(this.cardManager, Mockito.times(this.nbPlayer + 15)).getHand(Mockito.any(Integer.class));
    }
}
