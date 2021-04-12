package client.integration;


import client.AI.RandomAI;
import client.App;
import client.playerRestController.PlayerRestController;
import client.playerRestTemplate.InscriptionRestTemplate;
import client.playerRestTemplate.PlayerRestTemplate;
import com.github.stefanbirkner.systemlambda.SystemLambda;
import commun.card.Deck;
import commun.player.Player;
import commun.request.ID;
import log.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * Ceci est un test d'integration, il permet de verifier que le client peut jouer une partie
 * tout en communiquant avec le server tout au long
 */
@SpringBootTest(classes = {App.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
public class PlayGameIT {

    RestTemplate restTemplate = new RestTemplate();

    @SpyBean
    InscriptionRestTemplate inscriptionRestTemplate;

    @SpyBean
    PlayerRestController playerRestController;

    @SpyBean
    PlayerRestTemplate requestGame;

    @Autowired
    Environment environment;

    RandomAI myAI;

    String IPClient;

    @BeforeEach
    public void connect() throws Exception {
        Logger.logger.verbose = false;
        Logger.logger.verbose_socket = false;

        //on change l'id du joueur pour que le docker puisse envoyer des requete (entre docker et l'exterieur)
        IPClient = "http://"+environment.getProperty("IP")+":"+environment.getProperty("server.port");
        Logger.logger.log("Translate URI for IT : "+IPClient);
        ID idTest = inscriptionRestTemplate.getId();
        idTest.setUri(IPClient);

        Logger.logger.log("IP serveur de jeu : " + inscriptionRestTemplate.getURI());

        //creation de l'ia spy
        myAI = Mockito.spy(new RandomAI());
        myAI.setRequestGame(requestGame);
        playerRestController.setAi(myAI);



    }

    //nb de carte par age
    int card;
    Integer age;
    /**
        Ce test a pour but de voir que le joueur peut recuperer les information des tout les
        joueur au tour de la table et possede le bon nombre de carte dans ca main
     */
    @Test
    public void canCheckAllPlayerBoard() throws Exception {

        card = 7;
        age = 1;

        //on surcharge la methode choose action pour faire des operation de test
        Mockito.doAnswer(invocationOnMock -> {
            //on verifie que l'on peut recuperer tout les plateau
            List<Player> allPlayer = myAI.getAllPlayers();
            assertEquals(requestGame.getNbPlayer(),allPlayer.size()); //on a bien tout les plateau

            Deck deck = invocationOnMock.getArgument(0);
            //on a bien le bon nombre de carte dans notre main
            assertEquals(card,deck.size());
            //et on est bien au bon age
            assertEquals(age,deck.get(0).getAge());
            card-=1;
            if(card==1){ //on vas changer d'age
                card = 7; //pour le prochain age on auras de nouveau 7 carte
                age++;
            }

            //pour chaque player il peut recuperer les information
            //dont il a le droit
            for(Player player : allPlayer){
                //il peut recuperer le nom du joueur
                assertTrue(player.getName()!=null && !player.getName().isEmpty());
                //il peut recuperer la wonderboard
                assertTrue(player.getWonderBoard()!=null);
                //il peut recuperer les building construit
                assertTrue(player.getWonderBoard().getBuilding()!=null);

                //il ne peut pas recuperer les deck des player
                assertNull(player.getCurrentDeck());
            }

            //quand il recupere sont propre plateau avec getMe:
            Player me = myAI.getMe();
            //il a bien le bon plateau
            assertTrue(me.getName().equals(inscriptionRestTemplate.getId().getName()));

            //on invoke la vraie methode pour que notre ia joue normalement
            return invocationOnMock.callRealMethod();
        }).when(myAI).chooseAction(any(Deck.class));

        //normalement le serveur nous a dit de nous fermer a la fin
        //on bloque la fin de l'application
        int status = SystemLambda.catchSystemExit(() -> {
            //on ce connecte au serveur de jeu
            boolean success = inscriptionRestTemplate.inscription();
            if (!success) {
                throw new Exception("bad inscription");
            }

            //la method dois etre appeller 18 fois (6 tour par age avec 3 age)
            Mockito.verify(myAI, Mockito.timeout(20000).times(18)).chooseAction(any(Deck.class));

            //on attend que le jeu ce termine
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //on a plus de connexion au jeu car il c'est terminer
            assertThrows(Exception.class, () -> myAI.getAllPlayers());
        });
        //normalement le serveur nous a dit de nous fermer a la fin
        assertEquals(0,status);
    }


}
