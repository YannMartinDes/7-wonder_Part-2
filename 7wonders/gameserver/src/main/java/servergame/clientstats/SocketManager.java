package servergame.clientstats;

import commun.communication.CommunicationMessages;
import commun.communication.JsonUtils;
import commun.communication.StatObject;
import io.socket.client.*;
import log.GameLogger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Socket manager s'occupe de la gestion de paquets recus par le serveur
 * et renvoie les choix de l'IA au serveur
 * Chaque SocketManager s'occupe d'une IA
 */
public class SocketManager
{
    private Socket socket;
    private JsonUtils jsonUtils;

    /**
     * Constructeur
     * @param URI URL du serveur, exemple: http://10.0.2.2:8000
     */
    public SocketManager (String URI)
    {
        this.jsonUtils = new JsonUtils();

        try {
            this.socket = IO.socket(URI);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.socket.connect();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SocketManager (Socket socket, JsonUtils jsonUtils)
    {
        this.socket = socket;
        this.jsonUtils = jsonUtils;
        this.socket.connect();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Permet d'envoyer le StatObject au serveur de statistiques
     * @param statObject Objet qui represente les statistiques */
    public void send (StatObject statObject)
            throws IOException
    {
        String toSend;

        toSend = this.jsonUtils.serialize(statObject);
        GameLogger.log("Envoi: (CommunicationMessages.STATS, " + toSend + ")");
        this.socket.emit(CommunicationMessages.STATS, toSend);
    }

    /** Permet de terminer les ajouts au serveur de statistiques
     * @param times le nombre de parties envoyees au serveur */
    public void finish (Integer times)
    {
        GameLogger.log("Envoi: (CommunicationMessages.FINISHED, " + Integer.toString(times) + ")");
        this.socket.emit(CommunicationMessages.FINISHED, times);
        this.socket.disconnect();
    }

    /* Getters */

    public JsonUtils getSocketUtils ()
    { return this.jsonUtils; }

    public Socket getSocket()
    { return this.socket; }
}