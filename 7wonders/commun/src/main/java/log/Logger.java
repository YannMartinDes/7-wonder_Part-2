package log;

/**
 * Logger
 * Classe qui represente l'utilisation du logger
 */
public final class Logger
{
    private Logger () {}

    public final static LoggerModule logger = LoggerModule.getInstance();

    public static void exit ()
    { Logger.logger.exit(); }
}
