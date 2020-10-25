package log;

import java.io.PrintStream;

/**
 * Logger gere l'affichage des messages du serveur
 */
public final class GameLogger
{
    /** Verbose mode: Permet d'activer ou non les messages de debogugages */
    public static boolean verbose = true;
    public static boolean verbose_socket = true;
    public static PrintStream out = System.out;
    public static PrintStream err = System.err;
    private static GameLogger instance = null;

    /** Aucune instanciation possible */
    private GameLogger () {}

    /** Instance */
    public static GameLogger getInstance ()
    {
        if (instance == null)
            instance = new GameLogger();
        return instance;
    }

    /**
     * Affiche un message de facon standard.
     * @param msg le message a afficher
     */
    public void put (String msg)
    {
        if (this.verbose)
            this.out.println(msg);
    }
    public void put_socket (String msg)
    {
        if (this.verbose_socket)
            this.out.println(msg);
    }

    /**
     * Log affiche un message de log
     * @param msg le message a afficher
     */
    public void log (String msg)
    {
        this.put(ConsoleColors.colorize("[*] " + msg, ConsoleColors.ANSI_CYAN));
    }
    public void log(String msg, String color){
        this.put(ConsoleColors.colorize("[*] " + msg, color));
    }
    public void log_socket (String msg)
    {
        this.put_socket(ConsoleColors.colorize("[*] " + msg, ConsoleColors.ANSI_CYAN));
    }
    /**
     * Log affiche un message de log
     * @param msg le message a afficher
     */
    public void logSpaceBefore (String msg)
    {
        this.log("\n[*] " + msg);
    }
    public void logSpaceBefore(String msg, String color)
    {
        this.log("");
        this.log( msg, color);
    }

    /**
     * Log affiche un message de log
     * @param msg le message a afficher
     */
    public void logSpaceAfter (String msg)
    {
        this.log(msg + "\n[*] ");
    }
    public void logSpaceAfter(String msg, String color) {
        this.log(msg, color);
        this.log("");
    }
    /**
     * Error affiche un message d'erreur
     * @param msg le message a afficher
     */
    public void error (String msg)
    {
        this.put(ConsoleColors.colorize("[E] " + msg, ConsoleColors.ANSI_RED));
    }

    /**
     * Important affiche un message important
     * @param msg le message a afficher
     */
    public void important (String msg)
    {
        this.put(ConsoleColors.colorize("[!] " + msg, ConsoleColors.ANSI_YELLOW));
    }
}