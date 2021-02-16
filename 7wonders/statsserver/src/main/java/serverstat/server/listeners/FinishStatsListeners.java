package serverstat.server.listeners;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import commun.communication.CommunicationMessages;
import log.LoggerComponent;
import serverstat.server.Server;
import serverstat.server.stats.StatObjectOrchestrer;

/** FinishStatsListeners ecoute sur le message CommunicationMessages.FINISHED
 * Cette classe sert a mettre fin aux additions de statistiques */
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
        LoggerComponent.getInstance().log_socket("Recu: (CommunicationMessages.FINISHED, " + Integer.toString((Integer) data) + ")");
        this.statObjectOrchestrer.finish((Integer) data);

        // MESSAGE AU CLIENT
        client.sendEvent(CommunicationMessages.MSG, "Calculs terminés");
        client.sendEvent(CommunicationMessages.STOP, "");

        client.disconnect();
        this.server.stopServeur();
    }
}
