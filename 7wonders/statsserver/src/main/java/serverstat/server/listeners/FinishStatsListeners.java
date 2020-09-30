package serverstat.server.listeners;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import log.GameLogger;
import serverstat.server.Server;
import serverstat.server.stats.StatObjectOrchestrer;

public class FinishStatsListeners implements DataListener
{
    private StatObjectOrchestrer statObjectOrchestrer;
    private Server server;

    /** Constructeur */
    public FinishStatsListeners (StatObjectOrchestrer statObjectOrchestrer, Server server)
    {
        this.statObjectOrchestrer = statObjectOrchestrer;
        this.server = server;
    }

    @Override
    public void onData (SocketIOClient client, Object data, AckRequest ackSender)
    {
        // Arreter d'additionner les statistiques
        GameLogger.log("Recu: (CommunicationMessages.FINISHED, " + Integer.toString((Integer) data) + ")");
        this.statObjectOrchestrer.finish((Integer) data);
        this.server.stopServeur();
    }
}
