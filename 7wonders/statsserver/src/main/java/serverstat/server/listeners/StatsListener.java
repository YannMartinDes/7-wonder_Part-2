package serverstat.server.listeners;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import commun.communication.JsonUtils;
import commun.communication.StatObject;
import serverstat.server.stats.StatObjectOrchestrer;

/** StatsListener ecoute sur CommunicationMessages.STATS
 * Cette classe sert a additionner les statistiques */
public class StatsListener implements DataListener
{
    private StatObject statObject;
    private JsonUtils jsonUtils;
    private StatObjectOrchestrer statObjectOrchestrer;

    /** Constructeur */
    public StatsListener (StatObjectOrchestrer statObjectOrchestrer)
    {
        this.jsonUtils = new JsonUtils();
        this.statObjectOrchestrer = statObjectOrchestrer;
    }

    @Override
    public void onData (SocketIOClient client, Object data, AckRequest ackSender)
            throws Exception
    {
        //GameLogger.getInstance().log_socket("Recu: (CommunicationMessages.STATS, " + (String) data + ")");
        // Deserialiser le JSON
        this.statObject = this.jsonUtils.deserialize((String) data, StatObject.class);

        // Additionner les statistiques aux anciennes
        this.statObjectOrchestrer.addStatObject(this.statObject);
    }
}
