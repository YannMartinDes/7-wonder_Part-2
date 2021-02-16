package log;

import log.coloring.ConsoleColors;
import log.printing.Printer;
import log.printing.PrinterSettings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggerModuleTest
{
    /* On redirige System.out (stdout) vers un ByteArrayOutputStream
	 * On verifie le contenu de output a chaque test*() */

    ByteArrayOutputStream output;
    PrintStream ps;
    PrintStream old;

    @BeforeEach
    void start ()
    {
        output = new ByteArrayOutputStream();
        ps = new PrintStream(output);
        old = System.out;
        Printer.printer.setStdout(ps);
        Logger.logger.verbose = true;
        LoggerSettings.DATE_PATTERN = "";
    }

    @Test
    void testError ()
    {
        Logger.logger.error("Test");
        assertEquals(output.toString(), ConsoleColors.ANSI_RED + "[] Test" + LoggerSettings.CRLF + ConsoleColors.ANSI_RESET);
    }

    @Test
    void testImportant ()
    {
        Logger.logger.important("Test");
        assertEquals(output.toString(), ConsoleColors.ANSI_YELLOW + "[] Test" + LoggerSettings.CRLF + ConsoleColors.ANSI_RESET);
    }

    @Test
    void testLog ()
    {
        Logger.logger.log("Test");
        assertEquals(output.toString(), LoggerSettings.DEFAULT_COLOR + "[] Test" + LoggerSettings.CRLF + ConsoleColors.ANSI_RESET);
    }

    @Test
    void testPut ()
    {
        // test valide de debug
        Logger.logger.verbose = true;
        Logger.logger.put("Test", LoggerSettings.DEFAULT_COLOR);
        assertTrue(output.toString().equals(LoggerSettings.DEFAULT_COLOR + "[] Test" + LoggerSettings.CRLF + ConsoleColors.ANSI_RESET));
        PrinterSettings.DEFAULT_STDOUT.flush();

        // flusher System.out ne flush par l'output
        Logger.logger.verbose = false;
        Logger.logger.put("Test", LoggerSettings.DEFAULT_COLOR);
        assertTrue(output.toString().equals(LoggerSettings.DEFAULT_COLOR + "[] Test" + LoggerSettings.CRLF + ConsoleColors.ANSI_RESET));

        // flush de l'output, ca ne vide pas le buffer
        try {
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(output.toString().equals(LoggerSettings.DEFAULT_COLOR + "[] Test" + LoggerSettings.CRLF + ConsoleColors.ANSI_RESET));
    }

    @AfterEach
    void endOfTest ()
    { PrinterSettings.DEFAULT_STDOUT.flush(); }

    @AfterEach
    void exit ()
    { Printer.printer.setStdout(old); }
}
