package log;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

class GameLoggerTest
{
    /* On redirige System.out (stdout) vers un ByteArrayOutputStream
	 * On verifie le contenu de output a chaque test*() */

    ByteArrayOutputStream output;
    PrintStream ps;
    PrintStream old;
    GameLogger gameLogger = GameLogger.getInstance();

    @BeforeEach
    void start ()
    {
        output = new ByteArrayOutputStream();
        ps = new PrintStream(output);
        old = System.out;
        GameLogger.out = ps;
        GameLogger.verbose = true;
    }

    @Test
    void testError ()
    {
        GameLogger.getInstance().error("Test");
        assertEquals(output.toString(), ConsoleColors.ANSI_RED + "[E] Test" + ConsoleColors.ANSI_RESET + System.lineSeparator());
    }

    @Test
    void testImportant ()
    {
        GameLogger.getInstance().important("Test");
        assertEquals(output.toString(), ConsoleColors.ANSI_YELLOW + "[!] Test" + ConsoleColors.ANSI_RESET + System.lineSeparator());
    }

    @Test
    void testLog ()
    {
        GameLogger.getInstance().log("Test");
        assertEquals(output.toString(), ConsoleColors.ANSI_CYAN + "[*] Test" + ConsoleColors.ANSI_RESET + System.lineSeparator());
    }

    @Test
    void testPut ()
    {
        /* test valide de debug */
        GameLogger.verbose = true;
        GameLogger.getInstance().put("Test");
        assertTrue(output.toString().equals("Test" + System.lineSeparator()));
        System.out.flush();

        /* flusher System.out ne flush par l'output */
        GameLogger.verbose = false;
        GameLogger.getInstance().put("Test");
        assertTrue(output.toString().equals("Test" + System.lineSeparator()));

        /* flush de l'output, ca ne vide pas le buffer */
        try {
            output.flush();
        } catch (IOException e) {
            e.printStackTrace(GameLogger.err);
        }
        assertTrue(output.toString().equals("Test" + System.lineSeparator()));
    }

    @AfterEach
    void endOfTest ()
    { System.out.flush(); }

    @AfterEach
    void exit ()
    { GameLogger.out = old; }
}
