package servergame.integration;

import commun.card.Deck;
import commun.communication.CommunicationMessages;
import commun.communication.StatObject;
import commun.player.Player;
import commun.request.ID;
import commun.request.RequestToPlayer;
import commun.wonderboard.WonderBoard;
import log.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import servergame.App;
import servergame.inscription.InscriptionPlayer;
import servergame.player.rest.PlayerBoardController;
import servergame.player.rest.RequestToPlayerRestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest(classes = {App.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
public class RequestToPlayerIT {

    StatObject statObject;

    private List<RequestToPlayer> requestToPlayer;

    @SpyBean
    InscriptionPlayer inscriptionPlayer;

    @SpyBean
    PlayerBoardController playerBoardController;

    @Autowired
    Environment env;

    private RestTemplate restTemplate;

    List<ID> allPlayer;
    int nbPlayer;

    @Before
    public void connect()
    {
        Logger.logger.verbose = false;
        Logger.logger.verbose_socket = false;

        //reception des inscription
        allPlayer = inscriptionPlayer.waitInscriptionFinish();
        nbPlayer = allPlayer.size();

        //mise en place des restTemplate
        requestToPlayer = new ArrayList<>();
        for(int i = 0; i< nbPlayer;i++){
            requestToPlayer.add(Mockito.spy(new RequestToPlayerRestTemplate(allPlayer.get(i))));
        }

        //mise en place Des plateau de jeux
        playerBoardController.setPlayers(new ArrayList<>());
        for(int i = 0; i<nbPlayer;i++){
            Player player = new Player(allPlayer.get(i).getName());
            player.setWonderBoard(new WonderBoard());
            player.setCurrentDeck(new Deck());

            playerBoardController.getPlayers().add(player);
        }
    }


    @Test
    public void setNbPlayer(){

        restTemplate = Mockito.spy(new RestTemplate());
        ReflectionTestUtils.setField(inscriptionPlayer, "restTemplate", restTemplate);

        for(int i = 0; i<nbPlayer;i++) {
            //on essayer d'assigner le nombre total de joueur a chaque joueur
            ResponseEntity response = inscriptionPlayer.sendNbPlayers(allPlayer.get(i));
            //On appelle bien un post
            Mockito.verify(restTemplate, Mockito.times(1)).postForEntity(eq(allPlayer.get(i).getUri() + "/nplayers"), any(), any());
            //on dois recevoir un OK pour dire que c'est ok
            assertEquals(response.getStatusCode(), HttpStatus.OK);
        }
    }

    @Test
    public void setPlayerPosition(){

        restTemplate = Mockito.spy(new RestTemplate());
        ReflectionTestUtils.setField(inscriptionPlayer, "restTemplate", restTemplate);

        for(int i = 0; i<nbPlayer;i++) {
            //on essayer d'assigner le nombre total de joueur a chaque joueur
            ResponseEntity response = inscriptionPlayer.sendPlayerPosition(allPlayer.get(i),i);
            //On appelle bien un post
            Mockito.verify(restTemplate, Mockito.times(1)).postForEntity(eq(allPlayer.get(i).getUri() + "/id"), any(), any());
            //on dois recevoir un OK pour dire que c'est ok
            assertEquals(response.getStatusCode(), HttpStatus.OK);
        }
    }

    @After
    public void deconnectClient(){
        for(int i = 0; i<nbPlayer;i++) {
            restTemplate.delete(allPlayer.get(i).getUri()+"/"+CommunicationMessages.STOP);
        }
    }
}
