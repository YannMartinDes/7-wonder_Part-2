package servergame.inscription;

import commun.communication.CommunicationMessages;
import commun.request.ID;
import log.ILogger;
import log.Logger;
import log.coloring.ConsoleColors;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Permet de gerer les inscription des player
 */
@RestController
@Component
public class InscriptionPlayer {

    public final static int MIN_PLAYER_REQUIRED = 3;
    public final static int MAX_PLAYER_REQUIRED = 7;

    private RestTemplate restTemplate= new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();


    ILogger logger = Logger.logger;

    private boolean inscriptionOpen = false;
    private List<ID> playerWaitList = new ArrayList<>(7);
    private long lastInscription = 0;
    private int secondAfterLastInsc = 6;

    @PostMapping(value = "/inscription")
    public ResponseEntity inscription(@RequestBody ID id){
        Logger.logger.log("tentative de connection");
        if(!inscriptionOpen){
            Logger.logger.log("Inscription fermer");
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
        if(id.getName() == null) {
            Logger.logger.log("Valeur incorrecte");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        synchronized (playerWaitList) {
            if (playerWaitList.size() >= MAX_PLAYER_REQUIRED) {
                Logger.logger.log("Trop de joueur");
                return new ResponseEntity(HttpStatus.TOO_MANY_REQUESTS);
            }

            // Verifier si le nom est deja dans le jeu ou pas (probleme sur la comprehension de logs)
            for (ID playerID : playerWaitList) {
                if (playerID.getName().equals(id.getName())) {

                    Logger.logger.log("Le joueur qui est associe à l'URI [" + id.getUri() + "] a donne un pseudo deja dans la liste.");
                    Logger.logger.log("Re-envoie de la demande d'inscription");
                    return new ResponseEntity(HttpStatus.IM_USED);
                }
            }

            Logger.logger.log("Le joueur " + id.getName() + " a rejoint la partie ");

            playerWaitList.add(id);
            sendPlayerPosition(id);
        }
        lastInscription = System.currentTimeMillis();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/test")
    public ResponseEntity<String> test(){
        return new ResponseEntity<String>("hello world",HttpStatus.OK);
    }

    /**
     * TODO peut etre changer l'implementation avec un callback plutot que le sleep
     * Permet d'attendre que l'inscription soit finis
     * et renvoie la liste des joueurs inscrits
     * @return la liste des joueurs inscrits
     * @throws InterruptedException
     */
    public List<ID> waitInscriptionFinish(){
        while (!readyToStart()){
            try {
                Thread.sleep(100);
            }
            catch (Exception e){
                logger.log(e.toString());
            }
        }
        //fin de l'inscription
        inscriptionOpen = false;
        Logger.logger.log("Fin de l'inscription des joueur, il y a : "+playerWaitList.size()+" joueurs inscrits.");
        for(ID id : playerWaitList) {
            sendNbPlayers(id);
        }

        return playerWaitList;
    }

    /**
     * Affectation d'une position
     */
    public void sendPlayerPosition(ID id)
    {
        try {
            int position = playerWaitList.size() -1 ;

            HttpEntity<Integer> httpEntity = new HttpEntity<>(position, headers);
            restTemplate.postForEntity(id.getUri() + "/id", httpEntity,String.class);
            return;
        }
        catch (Exception e){
            Logger.logger.log("Connection perdu avec le joueur"+ id.getName());
        }
    }

    /**
     * Envoi du nombre de joueur en jeu
     */
    public void sendNbPlayers(ID id)
    {
        HttpHeaders headers = new HttpHeaders();
        try {
            HttpEntity<Integer> httpEntity = new HttpEntity<>(playerWaitList.size(), headers);
            restTemplate.postForEntity(id.getUri() + "/nplayers", httpEntity,String.class);
            return;
        }
        catch (Exception e){
            Logger.logger.log("Connection perdu avec le joueur"+ id.getName());
        }
    }

    /**
     * Permet de mettre fin au client
     */
    public void sendStopPlayer(){
        for(ID id : playerWaitList) {
            try {
                restTemplate.delete(id.getUri() + "/"+ CommunicationMessages.STOP);
            } catch (Exception e) {
                Logger.logger.log("Connection perdu avec le joueur" + id.getName());
            }
        }
    }


    /**
     * Permet de voir si la parti est prete pour commencer
     * @return les inscription sont finis
     */
    public boolean readyToStart(){
        long currentTime = System.currentTimeMillis();
        //2 condition pour que la parti ce lance:
        // #1 nb de joueur max inscrit
        // #2 avoir au moins le nb de joueur minimum et qu'il n'y a pas eu d'inscription depuis @secondAfterLastInsc secondes
        if(playerWaitList.size()==MAX_PLAYER_REQUIRED
           || (playerWaitList.size()>=MIN_PLAYER_REQUIRED && lastInscription+1000*secondAfterLastInsc<currentTime)){
            return true;
        }
        return false;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ID> getPlayerWaitList() {
        return playerWaitList;
    }

    public void setPlayerWaitList(List<ID> playerWaitList) {
        this.playerWaitList = playerWaitList;
    }

    public boolean isInscriptionOpen() {
        return inscriptionOpen;
    }

    public void setInscriptionOpen(boolean inscriptionOpen) {
        this.inscriptionOpen = inscriptionOpen;
    }

}
