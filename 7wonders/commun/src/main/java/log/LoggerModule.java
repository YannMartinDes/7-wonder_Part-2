package log;

import log.coloring.ConsoleColors;
import log.printing.Printer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/** LoggerModule gere l'affichage des messages du serveur */
public class LoggerModule
        implements ILogger
{
    /** Verbose mode: Permet d'activer ou non les messages de debogugages */
    public static boolean verbose = true;
    public static boolean verbose_socket = true;

    private static LoggerModule instance = null;

    /** Aucune instanciation possible */
    private LoggerModule () {}

    /** Instance */
    public static LoggerModule getInstance ()
    {
        if (instance == null)
            instance = new LoggerModule();
        return instance;
    }

    public void exit ()
    { Printer.exit(); }

    /**
     * Affiche un message de facon standard.
     * @param msg le message a afficher
     */
    public void put (String msg, String color)
    {
        if (this.verbose)
            Printer.printer.println(this.concat(this.prefix(), msg), color);
    }

    public void put_socket (String msg, String color)
    {
        if (this.verbose_socket)
            Printer.printer.println(this.concat(this.prefix(), msg), color);
    }

    /**
     * concat() permet de concatener 2 Strings
     * @param first la premiere String
     * @param second la seconde
     * @return la concatenation des deux strings
     */
    private String concat (String first, String second)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(first);
        sb.append(second);
        sb.append(LoggerSettings.CRLF);
        return sb.toString();
    }

    private String prefix ()
    {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(LoggerSettings.DATE_PATTERN, new Locale("fr", "FR"));

        String date = simpleDateFormat.format(new Date());

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(date);
        sb.append("] ");
        return sb.toString();
    }

    /**
     * Log affiche un message de log
     * @param msg le message a afficher
     */
    public void log (String msg)
    { this.put(msg, LoggerSettings.DEFAULT_COLOR); }

    public void log(String msg, String color)
    { this.put(msg, color); }

    public void log_socket (String msg)
    { this.put_socket(msg, LoggerSettings.DEFAULT_COLOR); }

    /**
     * Log affiche un message de log
     * @param msg le message a afficher
     */
    public void logSpaceBefore (String msg)
    { this.logSpaceBefore(msg, LoggerSettings.DEFAULT_COLOR); }

    public void logSpaceBefore (String msg, String color)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(LoggerSettings.CRLF);
        sb.append(prefix());
        sb.append(msg);
        this.log(sb.toString(), color);
    }

    /**
     * Log affiche un message de log
     * @param msg le message a afficher
     */
    public void logSpaceAfter (String msg)
    { this.logSpaceAfter(msg, LoggerSettings.DEFAULT_COLOR); }

    public void logSpaceAfter (String msg, String color)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(msg);
        sb.append(LoggerSettings.CRLF);
        sb.append(prefix());
        this.log(sb.toString(), color);
    }
    /**
     * Error affiche un message d'erreur
     * @param msg le message a afficher
     */
    public void error (String msg)
    { this.put(msg, ConsoleColors.ANSI_RED); }

    /**
     * Important affiche un message important
     * @param msg le message a afficher
     */
    public void important (String msg)
    { this.put(msg, ConsoleColors.ANSI_YELLOW); }
}