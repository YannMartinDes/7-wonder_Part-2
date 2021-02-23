package severgame.engine;

import client.AI.RandomAI;
import commun.card.Deck;
import commun.communication.StatObject;
import commun.player.Player;
import commun.wonderboard.WonderBoard;
import log.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import servergame.card.CardManager;
import servergame.engine.GameEngine;
import servergame.player.PlayerController;
import servergame.player.PlayerManager;
import servergame.player.PlayerManagerImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameEngineTest
{
    private GameEngine gameEngine;

    private int nbPlayer;
    private List<Player> allPlayers;
    private  List<PlayerController> allPlayerControllers;
    private int nbAge;
    private int currentAge;
    private PlayerManager playerManager;
    private PlayerController playerController;
    private StatObject statObject;

    @Mock
    CardManager  cardManager = Mockito.mock(CardManager.class);


    @BeforeEach
    void init ()
    {
        Logger.logger.verbose = false;
        this.allPlayerControllers=new ArrayList<>();
        this.nbPlayer = 3;
        this.allPlayers = new ArrayList<>();
        this.allPlayers.add(new Player("Nom1"));
        this.allPlayers.add(new Player("Nom2"));
        this.allPlayers.add(new Player("Nom3"));
        for (Player p: this.allPlayers
             ) {
            this.playerController = new PlayerController(p,new RandomAI());
            this.playerController = Mockito.spy(this.playerController);
            this.allPlayerControllers.add(this.playerController);

        }
        this.cardManager = new CardManager(this.nbPlayer);
        this.nbAge = 1;
        this.currentAge = 1;
        this.statObject =new StatObject();
        this.statObject = Mockito.spy(this.statObject);
        this.playerManager = new PlayerManagerImpl(this.allPlayerControllers);
        this.playerManager = Mockito.spy(this.playerManager);
        this.statObject.construct(this.playerManager.getNbPlayer());

    }


    /**
     * Pour tester startGame:
     * on test si les wonderBoards on été assigner
     * si les voisins ont été assigner
     * si statObject.setUsernames a été appeler
     */
    @Test
    void testAssignmentInStartGame()
    {
        //avant le debut de la partie le joueur ne possede pas de wonderBoard
        for (Player player: playerManager.getAllPlayers()
             ) {
            assertEquals(null,player.getWonderBoard());
        }

        this.currentAge = this.nbAge + 1; // Empecher l'appel de la boucle de gameLoop()
        this.gameEngine = new GameEngine( playerManager, this.cardManager, this.nbAge, this.currentAge);
        this.gameEngine = Mockito.spy(this.gameEngine);
        ReflectionTestUtils.setField(this.gameEngine, "statObject", this.statObject);

        //debut de la partie
        this.gameEngine.startGame();

        //verifier que la methode dans playerManager est appelé
        assertEquals(3,this.gameEngine.getNbPlayer());

        //le joueur doit avoir une wonderBoard maintenant et il doit avoir un voisin
        for (Player player: gameEngine.getAllPlayers()
        ) {
            assertNotNull(player.getWonderBoard());
        }
        assertTrue(gameEngine.getAllPlayers().get(0).getRightNeightbour() == gameEngine.getAllPlayers().get(1).getWonderBoard());
        assertTrue(gameEngine.getAllPlayers().get(1).getLeftNeightbour() == gameEngine.getAllPlayers().get(0).getWonderBoard());

        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("/");
        usernames.add(playerManager.getAllPlayers().get(0).getName());
        usernames.add(playerManager.getAllPlayers().get(1).getName());
        usernames.add(playerManager.getAllPlayers().get(2).getName());

        assertEquals(gameEngine.getStatObject().getUsernames(),usernames);
        verify(this.statObject).setUsernames(Mockito.any());
        verify(this.statObject).setAIUsed(Mockito.any());
        verify(this.playerManager,times(2)).getPlayerControllers();
    }


    @Test
    void testGameLoop()
    {
        cardManager = Mockito.mock(CardManager.class);
        //Avant le lancement de gameLoop
        this.nbAge=2;
        assertTrue(currentAge == 1);
        assertEquals(0,this.cardManager.getHands().size());

        Mockito.when(this.cardManager.isEndAge()).thenReturn(true); // eviter le lancement de round()

        this.gameEngine = new GameEngine(this.playerManager, this.cardManager, this.nbAge, this.currentAge);
        ReflectionTestUtils.setField(this.gameEngine, "players", this.playerManager);
        ReflectionTestUtils.setField(this.gameEngine, "statObject", this.statObject);

        this.gameEngine = Mockito.spy(this.gameEngine);
        this.gameEngine.startGame();


        verify(this.cardManager,times(2)).createHands(anyInt()); //il y a eu 2 ages
        verify(this.playerManager,never()).assignPlayersDeck(Mockito.any(CardManager.class)); //car cardManager est Mocker
        verify(this.playerManager,times(this.nbAge*(this.gameEngine.getNbPlayer()+8)+1)).getAllPlayers();  //reset joker a bien etait lancer
        //les appel dans startGame = 3 + les appel dans GameLoop = 2 age * (nb joueur = 2 + 1 dans calcul conflit + 1 printRanking + 1 modGameStatic + 1) = 18
        verify(this.playerManager,times(4)).getPlayerControllers();  //reset joker a bien etait lancer
        assertEquals(nbAge+1 ,this.gameEngine.getCurrentAge());
        assertNotNull(this.gameEngine.getStatObject());
        assertEquals(this.gameEngine.getCardManager(), this.cardManager);
        assertEquals(this.gameEngine.getAllPlayers(), this.playerManager.getAllPlayers());
        verify(this.statObject,times(2)).incrementAge();

    }


    /**
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * On test si playManager.chooseAction / playAction / FinishAction /information ont bien été lancer
     * on test aussi si cardManager.rotateHands a été lancer
     */
    @Test
    void testRound () throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        this.cardManager = Mockito.spy(this.cardManager);
        doNothing().when(this.playerManager).chooseAction();
        doNothing().when(this.playerManager).playAction(Mockito.any(Deck.class));
        doNothing().when(this.playerManager).finishAction(Mockito.any(Deck.class));
        doNothing().when(this.playerManager).informations();
        doNothing().when(this.cardManager).rotateHands(Mockito.anyBoolean());

        this.gameEngine = new GameEngine(this.playerManager, this.cardManager, this.nbAge, this.currentAge);

        /* Lancer le round */
        Method method = GameEngine.class.getDeclaredMethod("round");
        method.setAccessible(true);
        method.invoke(gameEngine);

        /* verifier que rotateHands a bien eté lancer */
        Mockito.verify(this.cardManager).rotateHands(Mockito.anyBoolean());

        /* verifier que chooseAction  a bien eté lancer */
        Mockito.verify(this.playerManager).chooseAction();

        /* verifier que playAction  a bien eté lancer */
        Mockito.verify(this.playerManager).playAction(Mockito.any(Deck.class));

        /* verifier que playAction  a bien eté lancer */
        Mockito.verify(this.playerManager).finishAction(Mockito.any(Deck.class));

        /* verifier que playAction  a bien eté lancer */
        Mockito.verify(this.playerManager).informations();
    }

    /**
     * Verifie qu'elle renvoie le nombre de points de conflits attribués suivant l'âge
     */
    @Test
    void testGetConflictPointByAge()
    {
        this.gameEngine = new GameEngine(this.playerManager, this.cardManager, this.nbAge, this.currentAge);

        for(int i = 0 ; i<3 ; i++){
            assertEquals(i*2+1,this.gameEngine.getConflictPointsByAge(i+1));

        }
        assertEquals(-1,this.gameEngine.getConflictPointsByAge(0));
    }

    @Test
    void  testCalculateConflictPointsWon() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        WonderBoard myWonderBoard = new WonderBoard("MywonderBoard",null);
        WonderBoard leftWonderBoard = new WonderBoard("left",null);
        WonderBoard rightWonderBoard = new WonderBoard("right",null);

        myWonderBoard.addConflictPoints(3);
        leftWonderBoard.addConflictPoints(5);
        rightWonderBoard.addConflictPoints(1);

        myWonderBoard.addMilitaryPower(3); //je vais gagner 2 points de conflit
        leftWonderBoard.addMilitaryPower(2);
        rightWonderBoard.addMilitaryPower(1);

        Player player = new Player("playerTest",myWonderBoard);
        player = Mockito.spy(player);
        this.statObject.construct(3);

        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("/");
        usernames.add("x");
        usernames.add("y");
        usernames.add(player.getName());

        this.statObject.setUsernames(usernames);
        player.setLeftNeightbour(leftWonderBoard);
        player.setRightNeightbour(rightWonderBoard);

        this.playerController=new PlayerController(player,null);
        this.playerManager=new PlayerManagerImpl(List.of(this.playerController,this.playerController,this.playerController));

        this.playerManager = Mockito.spy( this.playerManager);
        when(this.playerManager.getNbPlayer()).thenReturn(3);

        this.gameEngine = new GameEngine(this.playerManager, this.cardManager, this.nbAge, this.currentAge);
        this.gameEngine = Mockito.spy(this.gameEngine);
        ReflectionTestUtils.setField(this.gameEngine, "statObject", this.statObject);

        /* Lancer calculateConflictPoints */
        Method method = GameEngine.class.getDeclaredMethod("calculateConflictPoints",Player.class,int.class);
        method.setAccessible(true);
        method.invoke(gameEngine,player,1);

        verify(player,times(this.playerManager.getNbPlayer())).getWonderBoard();
        verify(player,times(1)).getLeftNeightbour();
        verify(player,times(1)).getRightNeightbour();

        verify(this.statObject,times(1)).getStatByAge(Mockito.anyInt());
        verify(this.statObject,times(1)).getUsernames();
        verify(this.gameEngine).getConflictPointsByAge(Mockito.anyInt());
        verify(this.playerManager,atLeast(playerManager.getNbPlayer())).getNbPlayer();
        assertEquals(5,myWonderBoard.getConflictPoints()); //je gagne 2 pts de conflit


    }

    @Test
    void  testCalculateConflictPointsLost() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        WonderBoard myWonderBoard = new WonderBoard("MywonderBoard",null);
        WonderBoard leftWonderBoard = new WonderBoard("left",null);
        WonderBoard rightWonderBoard = new WonderBoard("right",null);

        myWonderBoard.addConflictPoints(3);
        leftWonderBoard.addConflictPoints(5);
        rightWonderBoard.addConflictPoints(1);

        myWonderBoard.addMilitaryPower(1); //le joueur va perdre contre ses voisin
        leftWonderBoard.addMilitaryPower(2);
        rightWonderBoard.addMilitaryPower(3);

        Player player = new Player("playerTest",myWonderBoard);
        player = Mockito.spy(player);
        this.statObject.construct(3);

        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("/");
        usernames.add("x");
        usernames.add("y");
        usernames.add(player.getName());

        this.statObject.setUsernames(usernames);
        player.setLeftNeightbour(leftWonderBoard);
        player.setRightNeightbour(rightWonderBoard);

        this.playerController=new PlayerController(player,null);
        this.playerManager=new PlayerManagerImpl(List.of(this.playerController,this.playerController,this.playerController));

        this.playerManager = Mockito.spy( this.playerManager);
        when(this.playerManager.getNbPlayer()).thenReturn(3);

        this.gameEngine = new GameEngine(this.playerManager, this.cardManager, this.nbAge, this.currentAge);
        this.gameEngine = Mockito.spy(this.gameEngine);
        ReflectionTestUtils.setField(this.gameEngine, "statObject", this.statObject);

        /* Lancer calculateConflictPoints */
        Method method = GameEngine.class.getDeclaredMethod("calculateConflictPoints",Player.class,int.class);
        method.setAccessible(true);
        method.invoke(gameEngine,player,1);

        verify(player,times(this.playerManager.getNbPlayer())).getWonderBoard();
        verify(player,times(1)).getLeftNeightbour();
        verify(player,times(1)).getRightNeightbour();

        verify(this.statObject,times(1)).getStatByAge(Mockito.anyInt());
        verify(this.statObject,times(1)).getUsernames();
        verify(this.gameEngine).getConflictPointsByAge(Mockito.anyInt());
        verify(this.playerManager,atLeast(playerManager.getNbPlayer())).getNbPlayer();
        assertEquals(1,myWonderBoard.getConflictPoints()); // je perd 2 points de conflit

    }

}
