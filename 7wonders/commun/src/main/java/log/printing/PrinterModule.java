package log.printing;

import log.coloring.ConsoleColors;

import java.io.OutputStream;
import java.io.PrintStream;

public class PrinterModule
{
    private OutputStream stdout = PrinterSettings.DEFAULT_STDOUT;
    private OutputStream stderr = PrinterSettings.DEFAULT_STDERR;

    private StringBuilder sbout = new StringBuilder();
    private StringBuilder sberr = new StringBuilder();

    private static PrinterModule instance = null;

    /** Aucune instanciation possible */
    private PrinterModule () {}

    public static PrinterModule getInstance ()
    {
        if (instance == null)
            instance = new PrinterModule();
        return instance;
    }

    public void println (String msg, String color)
    {
        if (PrinterSettings.IS_WRITING_ON_CONSOLE)
        {
            try
            { this.stdout.write(ConsoleColors.colorize(msg, color).getBytes()); }
            catch (Exception e)
            { e.printStackTrace(); }
        }
        if (PrinterSettings.IS_SAVED_AS_FILE)
        { sbout.append(msg); }
    }

    public void exit ()
    {
        if (PrinterSettings.IS_SAVED_AS_FILE)
        {
            try
            { PrinterSettings.FILE_STDOUT.write(sbout.toString().getBytes()); }
            catch (Exception e)
            { e.printStackTrace(); }
        }
    }

    public void setStdout (PrintStream stdout)
    { this.stdout = stdout; }
}
