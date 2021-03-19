package client.playerRestTemplate;

import commun.request.ID;
import log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Component
@RestController
public class InscriptionRestTemplate {
    private RestTemplate restTemplate;

    @Autowired
    PlayerRestTemplate playerRestTemplate;

    private String URI;
    @Resource
    private ID id;

    public InscriptionRestTemplate(){
        restTemplate = new RestTemplate();
    }

    public void inscription(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ID> httpEntity = new HttpEntity<>(id, headers);
        ResponseEntity<String> response1 = restTemplate.postForEntity(URI + "/test", httpEntity,String.class);
        Logger.logger.log(response1.getBody());
        //Récupération de la réponse.
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(URI + "/inscription", httpEntity,String.class);
            HttpStatus status = response.getStatusCode();
            if(status!=HttpStatus.OK){
                Logger.logger.log("Impossible de s'inscrire : "+status);
                Logger.logger.log("Fin de l'application");
                System.exit(0);
            }
        }
        catch (Exception e){
            Logger.logger.log("Impossible de se connecter au serveur");
            Logger.logger.log(URI);
            Logger.logger.log(id.getUri());
            System.exit(0);
        }


        Logger.logger.log("Inscription reussite");
    }

    @PostMapping(value = "/id")
    public void initPosition(@RequestParam Integer position){
        playerRestTemplate.setPlayerID(position);
        return;
    }

    @PostMapping(value = "/nplayers")
    public void initNbPlayer(@RequestParam Integer nb){
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
