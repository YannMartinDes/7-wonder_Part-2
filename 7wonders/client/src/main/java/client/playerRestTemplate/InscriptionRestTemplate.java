package client.playerRestTemplate;

import client.utils.CommunicationUtils;
import commun.request.ID;
import log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Component
@RestController
public class InscriptionRestTemplate {
    private RestTemplate restTemplate;

    @Autowired
    PlayerRestTemplate playerRestTemplate;

    @Autowired
    CommunicationUtils communicationUtils;

    @Value("${gameServer.uri:}")
    private String URI;

    @Resource(name = "id")
    private ID id;
    private int nbTray = 3;
    private HttpStatus lastResponseStatus;

    public InscriptionRestTemplate(){
        restTemplate = new RestTemplate();
    }

    public boolean inscription(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ID> httpEntity = new HttpEntity<>(id, headers);

        //Récupération de la réponse.
        try {
            ResponseEntity<?> response = restTemplate.postForEntity(URI + "/inscription", httpEntity,String.class);
            lastResponseStatus = response.getStatusCode();

            // Gestion d'un nom de joueur deja pris
            while (lastResponseStatus == HttpStatus.IM_USED)
            {
                String playerName = communicationUtils.generatePlayerName();

                Logger.logger.log("Mon nouveau nom : " + playerName);

                this.id.setName(playerName);
                httpEntity = new HttpEntity<>(id, headers);
                response = restTemplate.postForEntity(URI + "/inscription", httpEntity, String.class);
                lastResponseStatus = response.getStatusCode();
            }
            if(lastResponseStatus != HttpStatus.OK){
                Logger.logger.log("Impossible de s'inscrire : "+lastResponseStatus);
                Logger.logger.log("Fin de l'application");
                return false;
            }
            if(lastResponseStatus == HttpStatus.OK) {
                Logger.logger.log("Inscription reussite");
            }
        }
        catch (HttpClientErrorException httpException){
            lastResponseStatus = httpException.getStatusCode();
            Logger.logger.log("Impossible de s'inscrire : "+lastResponseStatus);
            Logger.logger.log(URI);
            Logger.logger.log(id.getUri());
            Logger.logger.log("Fin de l'application");
            return false;
        }
        catch (Exception e){
            Logger.logger.log("Impossible de se connecter au serveur");
            return false;
        }


        Logger.logger.log("Inscription reussite");
        return true;
    }

    @PostMapping(value = "/id")
    @ResponseStatus(HttpStatus.OK)
    public void initPosition(@RequestBody Integer position){
        playerRestTemplate.setPlayerID(position);
        Logger.logger.log("["+id.getName()+ "] Le Server m'a affecté à la position : "+position);
        return;
    }

    @PostMapping(value = "/nplayers")
    @ResponseStatus(HttpStatus.OK)
    public void initNbPlayer(@RequestBody Integer nb){
        playerRestTemplate.setNbPlayer(nb);
        return;
    }


    public PlayerRestTemplate getPlayerRestTemplate() {
        return playerRestTemplate;
    }

    public void setPlayerRestTemplate(PlayerRestTemplate playerRestTemplate) {
        this.playerRestTemplate = playerRestTemplate;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public HttpStatus getLastResponseStatus() {
        return lastResponseStatus;
    }
}
