package serverstat.server;


import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import log.GameLogger;
import commun.communication.*;
import serverstat.server.listeners.StatsListener;
import serverstat.server.stats.StatObjectOrchestrer;

public class Server
{
    /** Est l'objet qui represente la socket du serveur, c'est a elle que les clients communiquent */
    private final SocketIOServer server;
    private final String IP = "0.0.0.0";
    private final int PORT = 12345;
    private StatObjectOrchestrer statObjectParser;

    /*DATA BASE*/
    public Server()
    {
        this.statObjectParser = new StatObjectOrchestrer();
        Configuration configuration = new Configuration();
        configuration.setHostname(IP);
        configuration.setPort(PORT);

        GameLogger.log("Configuration créer.");

        // creation du serveur
        this.server = new SocketIOServer(configuration);
        GameLogger.log("Init Listener...");

        GameLogger.log("Server ready to start.");
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
        GameLogger.log("Server listening.");
    }

    /**
     * Permet au serveur d'arreter de listen et de se fermer
     */
    public void stopServeur () {
        server.stop();
    }

}