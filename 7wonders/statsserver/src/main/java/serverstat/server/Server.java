package serverstat.server;


import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import commun.communication.CommunicationMessages;
import log.GameLogger;
import serverstat.server.listeners.FinishStatsListeners;
import serverstat.server.listeners.StatsListener;
import serverstat.server.stats.StatObjectOrchestrer;

import java.net.InetAddress;
import java.net.UnknownHostException;

/** Server est une representation du serveur */
public class Server
{
    /** Est l'objet qui represente la socket du serveur, c'est a elle que les clients communiquent */
    private final SocketIOServer server;
    private final String IP = InetAddress.getLocalHost().getHostAddress();
    private final int PORT = 1335;
    private StatObjectOrchestrer statObjectParser;

    /*DATA BASE*/
    /** Constructeur */
    public Server() throws UnknownHostException {
        this.statObjectParser = new StatObjectOrchestrer();
        Configuration configuration = new Configuration();
        configuration.setHostname(IP);
        configuration.setPort(PORT);

        GameLogger.getInstance().log("[ip: " + this.IP + "]");
        GameLogger.getInstance().log("Configuration créée");

        // creation du serveur
        this.server = new SocketIOServer(configuration);
        GameLogger.getInstance().log("Initialisation des listeners..");
        this.initializeListeners();

        GameLogger.getInstance().log("Le serveur est prêt");

    }

    /** Permet d'initialiser tous les listeners */
    public void initializeListeners ()
    {
        this.server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                GameLogger.getInstance().log("New user connected");
            }
        });
        this.server.addEventListener(CommunicationMessages.STATS, String.class, new StatsListener(this.statObjectParser));
        this.server.addEventListener(CommunicationMessages.FINISHED, Integer.class, new FinishStatsListeners(this.statObjectParser, this));

    }

    /**
     * Permet au serveur de commencer a listen des clients
     */
    public void startServer () {

        server.start();
        GameLogger.getInstance().log("Serveur sur écoute.");
    }

    /**
     * Permet au serveur d'arreter de listen et de se fermer
     */
    public void stopServeur ()
    {
        System.exit(0);
    }

}