package log.printing;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class PrinterSettings
{
    public static boolean IS_SAVED_AS_FILE = true;
    public static boolean IS_WRITING_ON_CONSOLE = true;

    public static PrintStream DEFAULT_STDOUT = System.out;
    public static PrintStream DEFAULT_STDERR = System.err;

    public static String OUTPUT_STDOUT = "stdout.log";
    public static String OUTPUT_STDERR = "stderr.log";

    public static FileOutputStream FILE_STDOUT;

    static {
        try {
            FILE_STDOUT = new FileOutputStream(PrinterSettings.OUTPUT_STDOUT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static FileOutputStream FILE_STDERR;

    static {
        try {
            FILE_STDERR = new FileOutputStream(PrinterSettings.OUTPUT_STDERR);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
