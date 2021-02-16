package log;

import java.io.PrintStream;

public class LoggerSettings
{
    public static String CRLF = System.lineSeparator();

    public static PrintStream DEFAULT_STDOUT = System.out;
    public static PrintStream DEFAULT_STDERR = System.err;

    public static String DATE_PATTERN = "dd-MM-yyyy-HH:mm:ss.SSS";
}
