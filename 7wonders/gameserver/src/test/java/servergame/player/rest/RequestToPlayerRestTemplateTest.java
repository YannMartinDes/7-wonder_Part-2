package servergame.player.rest;

import commun.action.AbstractAction;
import commun.action.BuildAction;
import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.communication.CommunicationMessages;
import commun.effect.ScientificType;
import commun.effect.VictoryPointEffect;
import commun.player.Player;
import commun.request.ID;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;

public class RequestToPlayerRestTemplateTest
{
    @Mock
    RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

    @Mock
    ResponseEntity<AbstractAction> responseEntity = Mockito.mock(ResponseEntity.class);
    @Mock
    ResponseEntity<ScientificType> responseEntity2 = Mockito.mock(ResponseEntity.class);
    @Mock
    ResponseEntity<Integer[]> responseEntity3 = Mockito.mock(ResponseEntity.class);
    @Mock
    ResponseEntity<Integer> responseEntity4 = Mockito.mock(ResponseEntity.class);

    @Mock
    HttpHeaders headers = Mockito.mock(HttpHeaders.class);

    RequestToPlayerRestTemplate requestToPlayerRestTemplate;
    Deck deck;
    List<Integer[]> list;
    ID id;

    @BeforeEach
    public void init ()
    {
        Mockito.when(responseEntity.getBody()).thenReturn(new BuildAction(0, true));
        Mockito.when(restTemplate.postForEntity(eq("/" + CommunicationMessages.CHOOSEACTION), any(HttpHeaders.class), any(Class.class)))
                .thenReturn(responseEntity);

        Mockito.when(responseEntity3.getBody()).thenReturn(new Integer[] {1, 3, 3, 7});
        Mockito.when(restTemplate.getForEntity(eq("/" + CommunicationMessages.CHOOSESCIENTIFICS), any(Class.class)))
                .thenReturn(responseEntity3);

        Mockito.when(responseEntity2.getBody()).thenReturn(ScientificType.GEOGRAPHY);
        Mockito.when(restTemplate.postForEntity(eq("/" + CommunicationMessages.CHOOSEPURCHASE), any(HttpHeaders.class), any(Class.class)))
                .thenReturn(responseEntity2);

        Mockito.when(responseEntity4.getBody()).thenReturn(1337);
        Mockito.when(restTemplate.postForEntity(eq("/" + CommunicationMessages.CHOOSECARD), any(HttpHeaders.class), any(Class.class)))
                .thenReturn(responseEntity4);

        id = new ID("", "");
        requestToPlayerRestTemplate = new RequestToPlayerRestTemplate(id);
        requestToPlayerRestTemplate.setHeaders(headers);
        requestToPlayerRestTemplate.setRestTemplate(restTemplate);

        deck = new Deck();
        Card card = new Card("test", CardType.CIVIL_BUILDING,new VictoryPointEffect(0),1,null);
        deck.addCard(card);

        list = new ArrayList<>();
        list.add(new Integer[] {1});
    }

    @Test
    public void chooseActionTest ()
    {
        BuildAction ba = (BuildAction) requestToPlayerRestTemplate.chooseAction(deck);
        Mockito.verify(restTemplate, Mockito.times(1)).postForEntity(anyString(), any(HttpHeaders.class), any(Class.class));
        assertEquals(ba.getIndexOfCard(), 0);
    }

    @Test
    public void choosePurchasePossibilityTest ()
    {
        Integer[] arr = requestToPlayerRestTemplate.choosePurchasePossibility(list);
        Mockito.verify(restTemplate, Mockito.times(1)).postForEntity(anyString(), any(HttpHeaders.class), any(Class.class));
        assertEquals(arr[0], 1);
        assertEquals(arr[1], 3);
        assertEquals(arr[2], 3);
        assertEquals(arr[3], 7);

    }

    @Test
    public void useScientificsGuildEffectTest ()
    {
        ScientificType st = requestToPlayerRestTemplate.useScientificsGuildEffect();
        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity(anyString(), any(Class.class));
        assertEquals(st, ScientificType.GEOGRAPHY);
    }

    @Test
    public void chooseCardTest ()
    {
        int integer = requestToPlayerRestTemplate.chooseCard(deck);
        Mockito.verify(restTemplate, Mockito.times(1)).postForEntity(anyString(), any(HttpHeaders.class), any(Class.class));
        assertEquals(integer, 1337);
    }
}
