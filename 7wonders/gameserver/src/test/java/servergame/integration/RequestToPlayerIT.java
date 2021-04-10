package servergame.integration;

import commun.action.AbstractAction;
import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.communication.CommunicationMessages;
import commun.communication.StatObject;
import commun.cost.CoinCost;
import commun.effect.ChoiceMaterialEffect;
import commun.effect.CoinEffect;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import commun.player.Player;
import commun.request.ID;
import commun.request.RequestToPlayer;
import commun.wonderboard.WonderBoard;
import io.cucumber.java8.De;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;

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
            player.setWonderBoard(new WonderBoard("wonderBoard", new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,1)))));
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
            Mockito.verify(restTemplate, times(1)).postForEntity(eq(allPlayer.get(i).getUri() + "/nplayers"), any(), any());
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
            Mockito.verify(restTemplate, times(1)).postForEntity(eq(allPlayer.get(i).getUri() + "/id"), any(), any());
            //on dois recevoir un OK pour dire que c'est ok
            assertEquals(response.getStatusCode(), HttpStatus.OK);
        }
    }

    @Test
    public void chooseActionTest(){
        int i = 0;
        for(RequestToPlayer rtp : requestToPlayer){
            //Cast
            RequestToPlayerRestTemplate rtprt = (RequestToPlayerRestTemplate) rtp;

            restTemplate = Mockito.spy(new RestTemplate());
            ReflectionTestUtils.setField(rtprt, "restTemplate", restTemplate);

            //Creation d'un deck
            Deck deck = new Deck();
            deck.add(new Card("t", CardType.MANUFACTURED_PRODUCTS,new CoinEffect(5),1,new CoinCost(1),""));
            deck.add(new Card("t", CardType.MANUFACTURED_PRODUCTS,new CoinEffect(5),1,new CoinCost(1),""));
            deck.add(new Card("t", CardType.MANUFACTURED_PRODUCTS,new CoinEffect(5),1,new CoinCost(1),""));
            deck.add(new Card("t", CardType.MANUFACTURED_PRODUCTS,new CoinEffect(5),1,new CoinCost(1),""));

            //Info nécéssaire pour /ID
            inscriptionPlayer.sendNbPlayers(rtprt.getID());
            inscriptionPlayer.sendPlayerPosition(rtprt.getID(),i);

            //Appel de la méthode
            AbstractAction action = rtprt.chooseAction(deck);

            //On recois bien une action valide
            assertTrue(action != null);
            assertTrue(action.getIndexOfCard() >= 0 && action.getIndexOfCard() <4);

            //On appelle bien un post
            Mockito.verify(restTemplate, times(1)).postForEntity(eq(rtprt.getID().getUri() + CommunicationMessages.CHOOSEACTION), any(), any());

            //Seul ces IA cherchent à connaitre leur plateau
            if(rtprt.getID().getStrategy() == "SecondAI" || rtprt.getID().getStrategy() == "FirstAI"){
                Mockito.verify(playerBoardController,times(1)).loadBoard(anyString());
            }
            i++;
        }
    }

    @Test
    public void choosePurchasePossibilityTest(){
        for(RequestToPlayer rtp : requestToPlayer) {
            //Cast
            RequestToPlayerRestTemplate rtprt = (RequestToPlayerRestTemplate) rtp;

            restTemplate = Mockito.spy(new RestTemplate());
            ReflectionTestUtils.setField(rtprt, "restTemplate", restTemplate);

            List<Integer[]> list = new ArrayList<>();
            list.add(new Integer[]{1,1});
            list.add(new Integer[]{1,2});
            list.add(new Integer[]{2,5});
            list.add(new Integer[]{3,3});
            list.add(new Integer[]{2,2});

            Integer[] arr = rtprt.choosePurchasePossibility(list);

            //On appelle bien un post
            Mockito.verify(restTemplate, times(1)).postForEntity(eq(rtprt.getID().getUri() + CommunicationMessages.CHOOSEPURCHASE), any(), any());

            //Reponse correcte
            if(arr != null){//Elle peut ne rien choisir
                assertTrue(arr.length == 2);
            }
        }
    }

    @After
    public void deconnectClient(){
        for(int i = 0; i<nbPlayer;i++) {
            restTemplate.delete(allPlayer.get(i).getUri()+"/"+CommunicationMessages.STOP);
        }
    }
}
