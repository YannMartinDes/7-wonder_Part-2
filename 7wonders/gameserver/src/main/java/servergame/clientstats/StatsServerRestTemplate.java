package servergame.clientstats;

import commun.communication.CommunicationMessages;
import commun.communication.JsonUtils;
import commun.communication.StatObject;
import log.ConsoleColors;
import log.GameLogger;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@Component
@Scope("singleton")
public class StatsServerRestTemplate {

    private JsonUtils jsonUtils;
    private RestTemplate restTemplate;
    private String URI = "";

    public StatsServerRestTemplate(){
        this.restTemplate = new RestTemplate();
        this.jsonUtils = new JsonUtils();
    }

    /** Permet d'envoyer le StatObject au serveur de statistiques
     * @param statObject Objet qui represente les statistiques */
    public void sendStats(StatObject statObject) throws IOException {
        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);

        //Convert statObject
        String toSend = this.jsonUtils.serialize(statObject);
        GameLogger.getInstance().log_socket("Envoi: (CommunicationMessages.STATS, " + toSend + ")");

        //Post HttpEntity
        HttpEntity<String> httpEntity = new HttpEntity<>(toSend, headers);
        ResponseEntity<String > response = restTemplate.postForEntity(URI +"/"+CommunicationMessages.STATS,httpEntity, String.class);
        String result = response.getBody();
    }

    /** Permet de terminer les ajouts au serveur de statistiques
     * @param times le nombre de parties envoyees au serveur */
    public void finishStats(Integer times) throws IOException {
        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);

        //Convert statObject
        String toSend = this.jsonUtils.serialize(times);
        GameLogger.getInstance().log_socket("Envoi: (CommunicationMessages.FINISHED, " + times + ")");

        //Post HttpEntity
        HttpEntity<String> httpEntity = new HttpEntity<>(toSend, headers);
        ResponseEntity<String > response = restTemplate.postForEntity(URI +"/"+ CommunicationMessages.FINISHED,httpEntity, String.class);

        String result = response.getBody();
        GameLogger.getInstance().log_socket(result);

        GameLogger.getInstance().log("Travail terminé - arrêt du client.", ConsoleColors.ANSI_CYAN_BOLD);
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }
}
