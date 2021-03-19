package client.playerRestTemplate;

import commun.request.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @Resource
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

        //Récupération de la réponse.
        ResponseEntity<String>response = restTemplate.postForEntity(URI + "/inscription",headers,String.class);
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
