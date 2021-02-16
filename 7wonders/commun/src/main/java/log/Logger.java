package log;

public final class Logger
{
    private Logger () {}

    public final static LoggerModule logger = LoggerModule.getInstance();

    public static void exit ()
    { Logger.logger.exit(); }
}
