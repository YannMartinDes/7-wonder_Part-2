package servergame.engine;

import commun.card.Card;
import commun.card.Deck;
import commun.communication.StatObject;
import commun.effect.ChoiceMaterialEffect;
import commun.effect.CoinEffect;
import commun.effect.EffectList;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import commun.player.Player;
import commun.wonderboard.BattlePoint;
import commun.wonderboard.WonderBoard;
import commun.wonderboard.WonderStep;
import io.cucumber.java8.Pl;
import log.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import servergame.card.CardManager;
import servergame.player.PlayerController;
import servergame.player.PlayerManager;
import servergame.player.PlayerManagerImpl;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class GameEngineTest
{

    @Autowired
    private GameEngine gameEngine;

    private int nbPlayer;
    private List<Player> allPlayers;
    private  List<PlayerController> allPlayerControllers;
    private int nbAge;
    private int currentAge;

    private PlayerController playerController;

    private StatObject statObject;


    private PlayerManagerImpl playerManager ;


    private CardManager  cardManager ;



    @BeforeEach
    void init ()
    {
        Logger.logger.verbose = false;

        playerManager = Mockito.mock(PlayerManagerImpl.class);

        this.allPlayerControllers=new ArrayList<>();
        this.allPlayers = new ArrayList<>();

        this.nbPlayer = 3;

        for (int i = 0; i < nbPlayer ; i++) {
            Player player = Mockito.mock(Player.class);
            //Mock WonderBord
            WonderBoard wonderBoard = Mockito.mock(WonderBoard.class);
            Mockito.when(wonderBoard.getFace()).thenReturn("A");
            Mockito.when(wonderBoard.getWonderName()).thenReturn("testW");
            Mockito.when(wonderBoard.getBuilding()).thenReturn(new Deck());
            Mockito.when(wonderBoard.getAllEffects()).thenReturn(new EffectList());
            Mockito.when(wonderBoard.getBattlePoint()).thenReturn(new BattlePoint());

            //Mock players
            Mockito.when(player.getWonderBoard()).thenReturn(wonderBoard);
            Mockito.when(player.getLeftNeightbour()).thenReturn(wonderBoard);
            Mockito.when(player.getRightNeightbour()).thenReturn(wonderBoard);
            Mockito.when(player.getName()).thenReturn("test");

            this.allPlayers.add(player);
            PlayerController playerController = Mockito.mock(PlayerController.class);
            this.allPlayerControllers.add(playerController);
        }

        this.cardManager = new CardManager(this.nbPlayer);
        this.nbAge = 1;
        this.currentAge = 1;
        this.statObject =new StatObject();
        this.statObject = Mockito.spy(this.statObject);
        this.statObject.construct(nbPlayer);

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
        this.currentAge = this.nbAge + 2; // Empecher l'appel de la boucle de gameLoop()
        Mockito.when(playerManager.getNbPlayer()).thenReturn(this.nbPlayer);
        Mockito.when(playerManager.getAllPlayers()).thenReturn(this.allPlayers);

        this.gameEngine = new GameEngine( playerManager, this.cardManager, this.nbAge, this.currentAge);
        ReflectionTestUtils.setField(this.gameEngine, "statObject", this.statObject);

        //debut de la partie
        this.gameEngine.startGame();

        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("/");
        usernames.add(playerManager.getAllPlayers().get(0).getName());
        usernames.add(playerManager.getAllPlayers().get(1).getName());
        usernames.add(playerManager.getAllPlayers().get(2).getName());
        assertEquals(gameEngine.getStatObject().getUsernames(),usernames);

        //verifier que les methodes dans playerManager sont appelés
        assertEquals(3,this.gameEngine.getNbPlayer());
        verify(this.playerManager,times(1)).initPlayerView();
        verify(this.playerManager,times(1)).assignPlayersWonderBoard();
        verify(this.playerManager,times(1)).assignNeightbours();


        //verifier que les methodes dans statObject sont appelés
        verify(this.statObject).setUsernames(Mockito.any());
        verify(this.statObject).setAIUsed(Mockito.any());
        verify(this.playerManager,times(5)).getAllPlayers();
    }


    @Test
    void testGameLoop()
    {
        cardManager = Mockito.mock(CardManager.class);
        //Avant le lancement de gameLoop
        this.nbAge=2;

        Mockito.when(this.cardManager.isEndAge()).thenReturn(true); // eviter le lancement de round()
        Mockito.when(this.playerManager.getAllPlayers()).thenReturn(this.allPlayers);

        this.gameEngine = new GameEngine(this.playerManager, this.cardManager, this.nbAge, this.currentAge);
        ReflectionTestUtils.setField(this.gameEngine, "players", this.playerManager);
        ReflectionTestUtils.setField(this.gameEngine, "statObject", this.statObject);

        this.gameEngine.startGame();

        verify(this.cardManager,times(nbAge)).createHands(anyInt()); //il y a eu 2 ages
        verify(this.cardManager,times(nbAge)).isEndAge(); //il y a eu 2 ages

        verify(this.playerManager,never()).assignPlayersDeck(Mockito.any(CardManager.class)); //car cardManager est Mocker
        verify(this.playerManager,times(4*nbAge)).getAllPlayers();
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
        this.gameEngine.init(playerManager);

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

        this.gameEngine.init(playerManager);
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

        this.gameEngine.init(playerManager);
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
