package servergame.clientstats;

import commun.communication.CommunicationMessages;
import commun.communication.JsonUtils;
import commun.communication.StatObject;
import log.Logger;
import log.coloring.ConsoleColors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@Scope("singleton")
public class StatsServerRestTemplate {

    private RestTemplate restTemplate;
    private String URI = "";
    private HttpHeaders headers;
    private HttpEntity<Integer> httpEntity;
    private ResponseEntity<String> response;

    private boolean serverResponse = true;

    public StatsServerRestTemplate(){
        this.restTemplate = new RestTemplate();
    }

    public StatsServerRestTemplate(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    /** Permet d'envoyer le StatObject au serveur de statistiques
     * @param statObject Objet qui represente les statistiques */
    public void sendStats(StatObject statObject)
    {

        if(!serverResponse) return;//Can't connect to stat server

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);

        //Convert statObject
        //String toSend = this.jsonUtils.serialize(statObject);
        //Logger.logger.log_socket("Envoi: (CommunicationMessages.STATS, " + toSend + ")");

        //Post HttpEntity
        HttpEntity<StatObject> httpEntity = new HttpEntity<>(statObject, headers);

        try{
            response = restTemplate.postForEntity(URI +"/"+CommunicationMessages.STATS,httpEntity, String.class);
        }
        catch(Exception e){
            serverResponse = false; //Can't connect
            Logger.logger.error("Impossible de se connecter au serveur de stats");
        }
    }

    /** Permet de terminer les ajouts au serveur de statistiques
     * @param times le nombre de parties envoyees au serveur */
    public void finishStats(Integer times)
    {

        // create headers
        this.headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);

        //Convert statObject
        Logger.logger.log_socket("Envoi: (CommunicationMessages.FINISHED, " + times + ")");

        //Post HttpEntity
        this.httpEntity = new HttpEntity<>(times, headers);

        try{

            response = restTemplate.postForEntity(URI +"/"+ CommunicationMessages.FINISHED,httpEntity, String.class);

            String result = response.getBody();
            Logger.logger.log_socket(result);

            Logger.logger.log("Travail terminé - arrêt du client.", ConsoleColors.ANSI_CYAN_BOLD);

            restTemplate.delete(URI+"/"+CommunicationMessages.STOP);
        }
        catch(Exception e){
            serverResponse = false; //Can't connect
            Logger.logger.error("Impossible de se connecter au serveur de stats");
        }
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public boolean getResponse () { return this.serverResponse; }

    public void setServerResponse (boolean b) { this.serverResponse = b; }
}
