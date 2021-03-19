package servergame.player.rest;

import commun.action.AbstractAction;
import commun.card.Deck;
import commun.communication.CommunicationMessages;
import commun.effect.ScientificType;
import commun.request.ID;
import commun.request.RequestToPlayer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/** Explicit class */
public class RequestToPlayerRestTemplate implements RequestToPlayer
{
    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private ID id;

    public RequestToPlayerRestTemplate (ID id)
    {
        this.restTemplate = new RestTemplate();
        this.id = id;
    }

    @Override
    public AbstractAction chooseAction (Deck deck)
    {
        ResponseEntity<AbstractAction> response;
        HttpEntity<Deck> httpEntity;

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        httpEntity = new HttpEntity<>(deck, headers);

        response = restTemplate.postForEntity(this.id.getUri() + CommunicationMessages.CHOOSEACTION, httpEntity, AbstractAction.class);

        return response.getBody();
    }

    @Override
    public Integer[] choosePurchasePossibility (List<Integer[]> purchaseChoice)
    {
        ResponseEntity<Integer[] > response;
        HttpEntity<List<Integer[]>> httpEntity;

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        httpEntity = new HttpEntity<>(purchaseChoice, headers);

        response = restTemplate.postForEntity(this.id.getUri() + CommunicationMessages.CHOOSEPURCHASE, httpEntity, Integer[].class);

        return response.getBody();
    }

    @Override
    public ScientificType useScientificsGuildEffect ()
    {
        ResponseEntity<ScientificType> response;

        response = restTemplate.getForEntity(this.id.getUri() + CommunicationMessages.CHOOSESCIENTIFICS, ScientificType.class);
        return response.getBody();
    }

    @Override
    public int chooseCard (Deck deck)
    {
        ResponseEntity<Integer> response;
        HttpEntity<Deck> httpEntity;

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        httpEntity = new HttpEntity<>(deck, headers);

        response = restTemplate.postForEntity(this.id.getUri() + CommunicationMessages.CHOOSECARD, httpEntity, Integer.class);

        return response.getBody();
    }

    public void setRestTemplate (RestTemplate restTemplate)
    { this.restTemplate = restTemplate; }

    public void setHeaders (HttpHeaders headers)
    { this.headers = headers; }
}
