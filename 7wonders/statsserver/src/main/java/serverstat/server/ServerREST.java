package serverstat.server;

import commun.communication.CommunicationMessages;
import commun.communication.JsonUtils;
import commun.communication.StatObject;
import log.GameLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import serverstat.server.stats.StatObjectOrchestrer;

import java.io.IOException;

@RestController
@Scope("session")
public class ServerREST {


    private StatObject statObject;
    private JsonUtils jsonUtils;

    @Autowired
    private StatObjectOrchestrer statObjectOrchestrer;

    public ServerREST(){
        this.jsonUtils = new JsonUtils();
    }

    @PostMapping(value = "/serverstats/" + CommunicationMessages.STATS,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String StatsReceive(@RequestBody String data) throws IOException, ClassNotFoundException {
        //GameLogger.getInstance().log_socket("Recu: (CommunicationMessages.STATS, " + (String) data + ")");
        // Deserialiser le JSON
        this.statObject = this.jsonUtils.deserialize((String) data, StatObject.class);

        // Additionner les statistiques aux anciennes
        this.statObjectOrchestrer.addStatObject(this.statObject);

        return "Object receive";
    }

    @PostMapping(value = "/serverstats/" + CommunicationMessages.FINISHED,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String FinishReceivingStats(@RequestBody String data) throws IOException, ClassNotFoundException{
        GameLogger.getInstance().log_socket("Recu: (CommunicationMessages.FINISHED, " + data + ")");
        this.statObjectOrchestrer.finish(Integer.parseInt(data));

        return "Finish receiving the stats";
    }
}