package log.printing;

import java.io.PrintStream;

public interface IPrinter
{
    /** print on console and put a line feed ('\n') */
    void println (String msg, String color);

    /** modify the standard output */
    void setStdout (PrintStream stdout);
}
