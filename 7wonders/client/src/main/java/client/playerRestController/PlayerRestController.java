package client.playerRestController;

import client.AI.AI;
import commun.action.AbstractAction;
import commun.card.Deck;
import commun.communication.CommunicationMessages;
import commun.communication.JsonUtils;
import commun.effect.ScientificType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Scope("singleton")
public class PlayerRestController {

    @Autowired
    private AI ai;//TODO voir si autowired possible

    @PostMapping(value = CommunicationMessages.CHOOSEACTION,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public AbstractAction chooseAction(@RequestBody Deck deck){

        AbstractAction action = ai.chooseAction(deck);
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

    @GetMapping(value = CommunicationMessages.CHOOSESCIENTIFICS,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public ScientificType chooseScientifics(){

        ScientificType scientificType = ai.useScientificsGuildEffect();
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

    /**
     * Permet de mettre fin au client
     */
    @DeleteMapping(value = CommunicationMessages.STOP)
    public void stopClient(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);//Wait for the return to be effective

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //Exit différé
                    System.exit(0);
                }
            }
        }).start();//start pour run en parallèle
        return;
    }


    public void setAi(AI ai) {
        this.ai = ai;
    }

    public AI getAi(){
        return this.ai;
    }
}
