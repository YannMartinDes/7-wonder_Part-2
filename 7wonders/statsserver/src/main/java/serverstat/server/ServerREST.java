package serverstat.server;

import commun.communication.CommunicationMessages;
import commun.communication.JsonUtils;
import commun.communication.StatObject;
import log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import serverstat.server.stats.StatObjectOrchestrer;

import javax.annotation.PostConstruct;
import java.io.IOException;

@RestController
@Scope("singleton")
public class ServerREST {

    private JsonUtils jsonUtils;

    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private StatObjectOrchestrer statObjectOrchestrer;

    public ServerREST(){
        this.jsonUtils = new JsonUtils();
    }

    @PostMapping(value = "/serverstats/" + CommunicationMessages.STATS,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public String statsReceive(@RequestBody StatObject data) throws IOException, ClassNotFoundException {
        //GameLogger.getInstance().log_socket("Recu: (CommunicationMessages.STATS, " + (String) data + ")");
        // Deserialiser le JSON
        //this.statObject = this.jsonUtils.deserialize((String) data, StatObject.class);

        // Additionner les statistiques aux anciennes
        this.statObjectOrchestrer.addStatObject(data);

        return "Object receive";
    }

    @PostMapping(value = "/serverstats/" + CommunicationMessages.FINISHED,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String finishReceivingStats(@RequestBody Integer data) {
        Logger.logger.log_socket("Recu: (CommunicationMessages.FINISHED, " + data + ")");

        if(this.statObjectOrchestrer.getStatObject() == null){
            return "Error : no stats received";
        }

        this.statObjectOrchestrer.finish(data);
        return "Finish receiving the stats";
    }

    @RequestMapping(value = "/serverstats/" + CommunicationMessages.STOP)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void stopStatServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);//Wait for the return to be effective

                    //Exit différé
                    System.exit(0);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();//start pour run en parallèle
        return;//Needed for the http response
    }


    /**
     * Permet au serveur de commencer a listen des clients
     */
    @PostConstruct
    public void startServer () {
        Logger.logger.log("Serveur sur écoute.");
    }


    // juste pour le MockMvcTest, sans param (test)
    @PostMapping("/essai/")
    public boolean getValue() {
        return true;
    }
}