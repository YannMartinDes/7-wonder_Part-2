package client.integration;

import client.AI.AI;
import client.AI.RandomAI;
import client.App;
import client.playerRestController.PlayerRestController;
import client.playerRestTemplate.InscriptionRestTemplate;
import client.playerRestTemplate.PlayerRestTemplate;
import commun.card.Deck;
import commun.player.Player;
import commun.request.ID;
import log.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;


import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

@SpringBootTest(classes = {App.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
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


    AI myAI;
    AI realRandomAI = new RandomAI();

    String IPClient;

    @Before
    public void connect() throws Exception {
        Logger.logger.verbose = true;
        Logger.logger.verbose_socket = true;

        //on change l'id du joueur pour que le docker puisse envoyer des requete (entre docker et l'exterieur)
        IPClient = "http://"+environment.getProperty("IP")+":"+environment.getProperty("server.port");
        Logger.logger.log("Translate URI for IT : "+IPClient);
        ID idTest = inscriptionRestTemplate.getId();
        idTest.setUri(IPClient);

        Logger.logger.log("IP serveur de jeu : " + inscriptionRestTemplate.getURI());

        //creation de l'ia spy
        myAI = new RandomAI();
        myAI.setRequestGame(requestGame);
        myAI = Mockito.spy(myAI);
        playerRestController.setAi(myAI);

        //on ce connecte au serveur de jeu
        boolean success = inscriptionRestTemplate.inscription();
        if(!success){
            throw new Exception("bad connexion");
        }
    }

    //nb de carte par age
    int card;
    Integer age;
    @Test
    public void canCheckAllPlayerBoard(){
        /*
        Ce test a pour but de voir que le joueur peut recuperer les information des tout les
        joueur au tour de la table et possede le bon nombre de carte dans ca main
         */
        card = 7;
        age = 1;
        doAnswer(invocationOnMock -> {
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

            return invocationOnMock.callRealMethod();
        }).when(myAI).chooseAction(any(Deck.class));
        //la method dois etre appeller 18 fois (6 tour par age avec 3 age)
        Mockito.verify(myAI,Mockito.timeout(60000).times(18)).chooseAction(any(Deck.class));

        //on attend que le je uce termine
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //on a plus de connexion au jeu car il c'est terminer
        assertThrows(Exception.class,() -> myAI.getAllPlayers());

    }


}
