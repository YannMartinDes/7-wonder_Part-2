package log;

/**
 * Logger gere l'affichage des messages du serveur
 */
public class GameLogger
{
    /** Verbose mode: Permet d'activer ou non les messages de debogugages */
    public static boolean verbose = true;

    /** Aucune instanciation possible */
    private GameLogger () {}

    /**
     * Affiche un message de facon standard.
     * @param msg le message a afficher
     */
    public static void put (String msg)
    {
        if (GameLogger.verbose)
            System.out.println(msg);
    }

    /**
     * Log affiche un message de log
     * @param msg le message a afficher
     */
    public static void log (String msg)
    {
        GameLogger.put(ConsoleColors.colorize("[*] " + msg, ConsoleColors.ANSI_CYAN));
    }
    public static void log(String msg, String color){
        GameLogger.put(ConsoleColors.colorize("[*] " + msg, color));
    }
    /**
     * Log affiche un message de log
     * @param msg le message a afficher
     */
    public static void logSpaceBefore (String msg)
    {
        GameLogger.log("\n[*] " + msg);
    }
    public static void logSpaceBefore(String msg, String color)
    {
        GameLogger.log("");
        GameLogger.log( msg, color);
    }

    /**
     * Log affiche un message de log
     * @param msg le message a afficher
     */
    public static void logSpaceAfter (String msg)
    {
        GameLogger.log(msg + "\n[*] ");
    }
    public static void logSpaceAfter(String msg, String color) {
        GameLogger.log(msg, color);
        GameLogger.log("");
    }
    /**
     * Error affiche un message d'erreur
     * @param msg le message a afficher
     */
    public static void error (String msg)
    {
        GameLogger.put(ConsoleColors.colorize("[E] " + msg, ConsoleColors.ANSI_RED));
    }

    /**
     * Important affiche un message important
     * @param msg le message a afficher
     */
    public static void important (String msg)
    {
        GameLogger.put(ConsoleColors.colorize("[!] " + msg, ConsoleColors.ANSI_YELLOW));
    }
}