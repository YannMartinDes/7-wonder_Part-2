package servergame.inscription;

import commun.request.ID;
import log.ILogger;
import log.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Permet de gerer les inscription des player
 */
@RestController
@Component
public class InscriptionPlayer {
    ILogger logger = Logger.logger;

    //TODO ineject
    private int nbPlayerWaited = 5;
    private boolean inscriptionOpen = false;
    private List<ID> playerWaitList = new ArrayList<>(7);

    @PostMapping(value = "/inscription")
    public ResponseEntity inscription(@RequestParam ID id){
        if(!inscriptionOpen){
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
        if(playerWaitList.size()>nbPlayerWaited) {
            return new ResponseEntity(HttpStatus.TOO_MANY_REQUESTS);
        }
        if(id.getName()==null && id.getName().isEmpty()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        playerWaitList.add(id);
        return new ResponseEntity(HttpStatus.OK);
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
