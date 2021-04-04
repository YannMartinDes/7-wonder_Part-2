package servergame.inscription;

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
    private RestTemplate restTemplate= new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();


    ILogger logger = Logger.logger;

    //TODO ineject
    private int nbPlayerWaited = 5;
    private boolean inscriptionOpen = false;
    private List<ID> playerWaitList = new ArrayList<>(7);

    @PostMapping(value = "/inscription")
    public ResponseEntity inscription(@RequestBody ID id){
        Logger.logger.log("tentative de connection");
        if(!inscriptionOpen){
            Logger.logger.log("Inscription fermer");
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
        if(id.getName()==null && id.getName().isEmpty()) {
            Logger.logger.log("Valeur incorrecte");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        synchronized (playerWaitList) {
            if (playerWaitList.size() >= nbPlayerWaited) {
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
        headers.setContentType(MediaType.APPLICATION_JSON);
        sendNbPlayers(id);

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
                Thread.sleep(500);
            }
            catch (Exception e){
                logger.log(e.toString());
            }
        }
        inscriptionOpen = true;
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
            Logger.logger.log("Connection perdu avec le joueur1"+ id.getName());
            playerWaitList.remove(playerWaitList.size());

        }
    }

    /**
     * Envoi du nombre de joueur en jeu
     */
    public void sendNbPlayers(ID id)
    {
        HttpHeaders headers = new HttpHeaders();
        try {
            HttpEntity<Integer> httpEntity = new HttpEntity<>(nbPlayerWaited, headers);
            restTemplate.postForEntity(id.getUri() + "/nplayers", httpEntity,String.class);
            return;
        }
        catch (Exception e){
            Logger.logger.log("Connection perdu avec le joueur2"+ id.getName());
            playerWaitList.remove(playerWaitList.size());
        }
    }


    /**
     * Permet de voir si la parti est prete pour commencer
     * @return les inscription sont finis
     */
    public boolean readyToStart(){
        if(playerWaitList.size()==nbPlayerWaited){ //nb de joueur suffisant
            return true;
        }
        return false;
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

    public int getNbPlayerWaited() {
        return nbPlayerWaited;
    }

    public void setNbPlayerWaited(int nbPlayerWaited) {
        this.nbPlayerWaited = nbPlayerWaited;
    }
}
