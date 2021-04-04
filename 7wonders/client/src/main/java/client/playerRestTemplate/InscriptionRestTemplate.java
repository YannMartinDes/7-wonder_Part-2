package client.playerRestTemplate;

import client.utils.CommunicationUtils;
import commun.request.ID;
import log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    public InscriptionRestTemplate(){
        restTemplate = new RestTemplate();
    }

    public void inscription(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ID> httpEntity = new HttpEntity<>(id, headers);

        //Récupération de la réponse.
        try {
            ResponseEntity<?> response = restTemplate.postForEntity(URI + "/inscription", httpEntity,String.class);
            HttpStatus status = response.getStatusCode();

            // Gestion d'un nom de joueur deja pris
            while (status == HttpStatus.IM_USED)
            {
                String playerName = communicationUtils.generatePlayerName();

                Logger.logger.log("Mon nouveau nom: " + playerName);

                this.id.setName(playerName);
                httpEntity = new HttpEntity<>(id, headers);
                response = restTemplate.postForEntity(URI + "/inscription", httpEntity, String.class);
                status = response.getStatusCode();
            }

            if(status != HttpStatus.OK){
                Logger.logger.log("Impossible de s'inscrire : "+status);
                Logger.logger.log("Fin de l'application");
                System.exit(0);
            }
        }
        catch (HttpClientErrorException httpException){
            HttpStatus status= httpException.getStatusCode();
            Logger.logger.log("Impossible de s'inscrire : "+status);
            Logger.logger.log(URI);
            Logger.logger.log(id.getUri());
            Logger.logger.log("Fin de l'application");
            System.exit(0);
        }
        catch (Exception e){
            Logger.logger.log("Impossible de se connecter au serveur");
            System.exit(0);
        }


        Logger.logger.log("Inscription reussite");
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

}
