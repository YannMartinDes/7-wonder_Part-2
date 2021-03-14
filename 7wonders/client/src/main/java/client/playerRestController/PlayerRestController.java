package client.playerRestController;

import client.AI.AI;
import commun.action.AbstractAction;
import commun.card.Deck;
import commun.communication.CommunicationMessages;
import commun.communication.JsonUtils;
import commun.communication.StatObject;
import commun.effect.ScientificType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@Scope("singleton")
public class PlayerRestController {

    private JsonUtils jsonUtils;

    private AI ai;//TODO voir si autowired possible

    public PlayerRestController(AI ai){
        this.ai = ai;
    }

    @Autowired
    private ApplicationContext appContext;

    public PlayerRestController(){
        this.jsonUtils = new JsonUtils();
    }

    @PostMapping(value = CommunicationMessages.CHOOSEACTION,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public AbstractAction chooseAction(@RequestBody Deck deck){

        AbstractAction action = ai.chooseAction(deck,0, null);//TODO
        return action;
    }

    @PostMapping(value = CommunicationMessages.CHOOSEPURCHASE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public Integer[] choosePurchase(@RequestBody List<Integer[]> purchaseChoice){

        Integer[] purchaseChoose = ai.choosePurchasePossibility(purchaseChoice);
        return purchaseChoose;
    }

    @PostMapping(value = CommunicationMessages.CHOOSESCIENTIFICS,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public ScientificType chooseScientifics(){

        ScientificType scientificType = ai.useScientificsGuildEffect(null);//TODO
        return scientificType;
    }

    @PostMapping(value = CommunicationMessages.CHOOSECARD,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public int chooseCard(@RequestBody Deck deck){

        int indexChoose = ai.chooseCard(deck);
        return indexChoose;
    }

}
