package serverstat.server;


import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import log.GameLogger;
import commun.communication.*;
import serverstat.server.listeners.StatsListener;
import serverstat.server.stats.StatObjectOrchestrer;

public class Server
{
    /** Est l'objet qui represente la socket du serveur, c'est a elle que les clients communiquent */
    private final SocketIOServer server;
    private final String IP = "127.0.0.1";
    private final int PORT = 1234;
    private StatObjectOrchestrer statObjectParser;

    /*DATA BASE*/
    public Server()
    {
        this.statObjectParser = new StatObjectOrchestrer();
        Configuration configuration = new Configuration();
        configuration.setHostname(IP);
        configuration.setPort(PORT);

        GameLogger.log("Configuration créée");

        // creation du serveur
        this.server = new SocketIOServer(configuration);
        GameLogger.log("Initialisation des listeners..");
        this.initializeListeners();

        GameLogger.log("Le serveur est prêt");

    }

    public void initializeListeners ()
    {
        this.server.addEventListener(CommunicationMessages.STATS, String.class, new StatsListener(this.statObjectParser));
    }


    /**
     * Permet au serveur de commencer a listen des clients
     */
    public void startServer () {

        server.start();
        GameLogger.log("Serveur sur écoute.");
    }

    /**
     * Permet au serveur d'arreter de listen et de se fermer
     */
    public void stopServeur () {
        server.stop();
    }

}