package client.integration;

import client.App;
import client.playerRestTemplate.InscriptionRestTemplate;
import client.playerRestTemplate.PlayerRestTemplate;

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

import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;


import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Ceci est un test d'integration, il permet de verifier que l'inscription du client à une partie
 * s'effectue avec succée
 */
@SpringBootTest(classes = {App.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
public class InscriptionIT {

    @SpyBean
    InscriptionRestTemplate inscriptionRestTemplate;

    @Autowired
    Environment environment;

    String IPClient;

    @BeforeEach
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

    /**
     * Ce test permet verifier que lorsque le client essais de s'inscrire à une partie
     * et que toute les conditions sont valide, l'inscription passe avec succée.
     */
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

    /**
     * Ce test permet verifier que lorsque le client essais de s'inscrire à une partie
     * et que le server n'est pas lancer, l'inscription ne passe pas.
     */
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

    /**
     * Ce test permet verifier que lorsque le client essais de s'inscrire à une partie
     *  et que le server a deja 7 jouers inscrit, l'inscription ne passe pas.
     */
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

    /**
     * Ce test permet verifier que lorsque le client est inscrit à une partie
     *   le server lui renvois bien sa position dans le jeu
     */
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


    /**
     * Ce test permet verifier que lorsque le client est inscrit à une partie
     *  le server lui renvois bien le nombre des joueurs dans le jeu
     */
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


        // envoi de la position par le server (quand la parti ce lance
        verify(playerRestTemplate,timeout(20000).times(1)).setNbPlayer(Mockito.anyInt());
        assertTrue(3 <= playerRestTemplate.getNbPlayer());
        assertTrue(7 >= playerRestTemplate.getNbPlayer());
    }

}
