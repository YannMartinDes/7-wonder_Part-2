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

    //Pour get un plateau
    public static final String BOARD = "BOARD";

    //URI Serveur stats
    public static final String SERVERSTATS = "serverstats";

    //URI pour chooseAction
    public static final String CHOOSEACTION = "/ask-action/action";
    //URI pour choosePurchasePossibility
    public static final String CHOOSEPURCHASE = "/ask-action/purchase";
    //URI pour useScientificsGuildEffect
    public static final String CHOOSESCIENTIFICS = "/ask-action/scientifics";
    //URI pour chooseCard
    public static final String CHOOSECARD = "/ask-action/card";
}