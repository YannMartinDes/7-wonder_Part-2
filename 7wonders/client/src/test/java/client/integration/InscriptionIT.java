package client.integration;

import client.App;
import client.playerRestTemplate.InscriptionRestTemplate;
import client.playerRestTemplate.PlayerRestTemplate;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;


import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = {App.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
public class InscriptionIT {

    @SpyBean
    InscriptionRestTemplate inscriptionRestTemplate;

    @Autowired
    Environment environment;

    String IPClient;

    @Before
    public void connect(){
        Logger.logger.verbose = false;
        Logger.logger.verbose_socket = false;
        //on force le set de l'ip client (pour communication docker -> hors docker)
        IPClient = "http://"+environment.getProperty("IP")+":"+environment.getProperty("server.port");
        Logger.logger.log("Translate URI for IT : "+IPClient);
        ID idTest = inscriptionRestTemplate.getId();
        idTest.setUri(IPClient);
        Logger.logger.log("IP serveur de jeu : " + inscriptionRestTemplate.getURI());
    }

    @Test
    public void inscriptionSuccess(){
        RestTemplate restTemplate = Mockito.spy(new RestTemplate());
        ReflectionTestUtils.setField(inscriptionRestTemplate,"restTemplate",restTemplate );

        //on essaye de s'inscrire
        boolean isInscris = inscriptionRestTemplate.inscription();//inscription du client
        //l'inscription dois etre reussite
        //la requete est bien parti
        verify(restTemplate,Mockito.atLeast(1)).postForEntity(eq(inscriptionRestTemplate.getURI()+"/inscription"),any(),any());
        //inscription success
        assertTrue(isInscris);
        assertEquals(HttpStatus.OK,inscriptionRestTemplate.getLastResponseStatus());


    }

    @Test
    public void inscriptionEchecTimeout(){
        System.out.println(inscriptionRestTemplate.getId().getUri());
        System.out.println(IPClient+"/inscription");
        System.out.println(inscriptionRestTemplate.getId().getUri()+"/inscription");
        RestTemplate restTemplate = Mockito.spy(new RestTemplate());
        ReflectionTestUtils.setField(inscriptionRestTemplate,"restTemplate",restTemplate );

        //on essaye de s'inscrire
        boolean isInscris = inscriptionRestTemplate.inscription();//inscription du client
        //l'inscription dois avoir echouer
        //la requete est bien parti
        verify(restTemplate,Mockito.atLeast(1)).postForEntity(eq(inscriptionRestTemplate.getURI()+"/inscription"),any(),any());
        assertFalse(isInscris);
        //pas de reponse
        assertEquals(null,inscriptionRestTemplate.getLastResponseStatus());



    }

    @Test
    public void inscriptionToManyPlayerOrClose(){
        RestTemplate restTemplate = Mockito.spy(new RestTemplate());
        ReflectionTestUtils.setField(inscriptionRestTemplate,"restTemplate",restTemplate );

        //on essaye de s'inscrire
        boolean isInscris = inscriptionRestTemplate.inscription();//inscription du client

        //l'inscription dois avoir echouer
        //la requete est bien parti
        verify(restTemplate,Mockito.times(1)).postForEntity(eq(inscriptionRestTemplate.getURI()+"/inscription"),any(),any());
        assertFalse(isInscris);
        //3 choix soit trop de joueur soit la parti est fini donc pas de reponse soit la parti est deja lancer (cela depend de la vitesse d'execution)
        assertTrue(inscriptionRestTemplate.getLastResponseStatus()==null || HttpStatus.TOO_MANY_REQUESTS.equals(inscriptionRestTemplate.getLastResponseStatus()) || HttpStatus.NOT_ACCEPTABLE.equals(inscriptionRestTemplate.getLastResponseStatus()));


    }

    @Test
    public void initPositionOk() {
        RestTemplate restTemplate = Mockito.spy(new RestTemplate());
        PlayerRestTemplate playerRestTemplate = Mockito.spy(new PlayerRestTemplate());
        ReflectionTestUtils.setField(inscriptionRestTemplate,"restTemplate",restTemplate );
        ReflectionTestUtils.setField(inscriptionRestTemplate,"playerRestTemplate",playerRestTemplate );

        //on essaye de s'inscrire
        boolean isInscris = inscriptionRestTemplate.inscription();//inscription du client
        //l'inscription dois etre reussite
        //la requete est bien parti
        verify(restTemplate,Mockito.atLeast(1)).postForEntity(eq(inscriptionRestTemplate.getURI()+"/inscription"),any(),any());
        //inscription success
        assertTrue(isInscris);
        assertEquals(HttpStatus.OK,inscriptionRestTemplate.getLastResponseStatus());


        // envoi de la position par le server
        verify(playerRestTemplate,times(1)).setPlayerID(Mockito.anyInt());
        assertTrue(1 <= playerRestTemplate.getPlayerID());
        assertTrue(7 >= playerRestTemplate.getPlayerID());

    }


    @Test
    public void initNbPlayerOK() {

        RestTemplate restTemplate = Mockito.spy(new RestTemplate());
        PlayerRestTemplate playerRestTemplate = Mockito.spy(new PlayerRestTemplate());
        ReflectionTestUtils.setField(inscriptionRestTemplate,"restTemplate",restTemplate );
        ReflectionTestUtils.setField(inscriptionRestTemplate,"playerRestTemplate",playerRestTemplate );

        //on essaye de s'inscrire
        boolean isInscris = inscriptionRestTemplate.inscription();//inscription du client
        //l'inscription dois etre reussite
        //la requete est bien parti
        verify(restTemplate,Mockito.atLeast(1)).postForEntity(eq(inscriptionRestTemplate.getURI()+"/inscription"),any(),any());
        //inscription success
        assertTrue(isInscris);
        assertEquals(HttpStatus.OK,inscriptionRestTemplate.getLastResponseStatus());

        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // envoi de la position par le server
        verify(playerRestTemplate,times(1)).setNbPlayer(Mockito.anyInt());
        assertTrue(3 <= playerRestTemplate.getNbPlayer());
        assertTrue(7 >= playerRestTemplate.getNbPlayer());
    }

}
