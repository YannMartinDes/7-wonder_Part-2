package commun.communication;

/** CommunicationMessages represente les communications entre le jeu et le serveur de statistiques */
public final class CommunicationMessages
{
    private CommunicationMessages () {}

    // Envoyer et recevoir les statistiques
    public static final String STATS = "STATS";

    // Arreter les envois de statistiques
    public static final String FINISHED = "FINISHED";

    //Message
    public static final String MSG = "MESSAGE";

    //Arret du client
    public static final String STOP = "STOP";
}