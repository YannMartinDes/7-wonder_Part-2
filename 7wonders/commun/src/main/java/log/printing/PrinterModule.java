package log.printing;

import log.coloring.ConsoleColors;

import java.io.OutputStream;
import java.io.PrintStream;

public class PrinterModule
    implements IPrinter
{
    // Standard output and error
    private OutputStream stdout;
    private OutputStream stderr;

    // String builder for output and error
    private StringBuilder sbout;
    private StringBuilder sberr;

    private static PrinterModule instance = null;

    /** Aucune instanciation possible */
    private PrinterModule ()
    {
        this.stdout = PrinterSettings.DEFAULT_STDOUT;
        this.stderr = PrinterSettings.DEFAULT_STDERR;

        this.sbout = new StringBuilder();
        this.sberr = new StringBuilder();
    }

    public static PrinterModule getInstance ()
    {
        if (instance == null)
            instance = new PrinterModule();
        return instance;
    }

    /** println() permet d'ecrire sur l'output standard un message avec une couleur */
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

    /** exit() permet de quitter l'application proprement en ecrivant dans le fichier de log */
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
