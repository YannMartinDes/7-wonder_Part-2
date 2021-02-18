package log;

import log.coloring.ConsoleColors;

import java.io.PrintStream;

/**
 * LoggerSettings
 * Classe qui permet de regrouper les parametres de configuration des
 * classes Logger*
 */
public class LoggerSettings
{
    // Carriage-Return - LineFeed
    public static String CRLF = System.lineSeparator();

    public static PrintStream DEFAULT_STDOUT = System.out;
    public static PrintStream DEFAULT_STDERR = System.err;

    public static String DEFAULT_COLOR = ConsoleColors.ANSI_CYAN;

    public static String DATE_PATTERN = "dd-MM-yyyy-HH:mm:ss.SSS";
}
