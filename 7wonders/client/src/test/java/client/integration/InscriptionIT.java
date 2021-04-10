package client.integration;

import client.App;
import client.playerRestTemplate.InscriptionRestTemplate;
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


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;


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
        //l'inscription dois avoir echouer reussite
        //la requete est bien parti
        verify(restTemplate,Mockito.atLeast(1)).postForEntity(eq(inscriptionRestTemplate.getURI()+"/inscription"),any(),any());
        assertFalse(isInscris);
        //pas de reponse
        assertEquals(null,inscriptionRestTemplate.getLastResponseStatus());



    }

    @Test
    public void inscriptionSoManyPlayerOrClose(){
        RestTemplate restTemplate = Mockito.spy(new RestTemplate());
        ReflectionTestUtils.setField(inscriptionRestTemplate,"restTemplate",restTemplate );

        //on essaye de s'inscrire
        boolean isInscris = inscriptionRestTemplate.inscription();//inscription du client

        //l'inscription dois avoir echouer reussite
        //la requete est bien parti
        verify(restTemplate,Mockito.times(1)).postForEntity(eq(inscriptionRestTemplate.getURI()+"/inscription"),any(),any());
        assertFalse(isInscris);
        //on a trop de joueur inscris ou l'inscription c'est fermer (l'inscription ce ferme pas longtemps après avoir tout les joueur)
        assertTrue(HttpStatus.TOO_MANY_REQUESTS.equals(inscriptionRestTemplate.getLastResponseStatus()) || HttpStatus.NOT_ACCEPTABLE.equals(inscriptionRestTemplate.getLastResponseStatus()));


    }



}
