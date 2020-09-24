package log;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class GameLoggerTest
{
    /* On redirige System.out (stdout) vers un ByteArrayOutputStream
	 * On verifie le contenu de output a chaque test*() */

    ByteArrayOutputStream output;
    PrintStream ps;
    PrintStream old;

    @BeforeEach
    public void start ()
    {
        output = new ByteArrayOutputStream();
        ps = new PrintStream(output);
        old = System.out;
        System.setOut(ps);
        GameLogger.verbose = true;
    }

    @Test
    public void testError ()
    {
        GameLogger.error("Test");
        assertEquals(output.toString(), ConsoleColors.ANSI_RED + "[E] Test" + ConsoleColors.ANSI_RESET + System.lineSeparator());
    }

    @Test
    public void testImportant ()
    {
        GameLogger.important("Test");
        assertEquals(output.toString(), ConsoleColors.ANSI_YELLOW + "[!] Test" + ConsoleColors.ANSI_RESET + System.lineSeparator());
    }

    @Test
    public void testLog ()
    {
        GameLogger.log("Test");
        assertEquals(output.toString(), ConsoleColors.ANSI_CYAN + "[*] Test" + ConsoleColors.ANSI_RESET + System.lineSeparator());
    }

    @Test
    public void testPut ()
    {
        /* test valide de debug */
        GameLogger.verbose = true;
        GameLogger.put("Test");
        //assertTrue(output.toString().equals("Test\r\n")); CORRIGER \r
        System.out.flush();

        /* flusher System.out ne flush par l'output */
        GameLogger.verbose = false;
        GameLogger.put("Test");
        //assertTrue(output.toString().equals("Test\r\n")); CORRIGER \r

        /* flush de l'output, ca ne vide pas le buffer */
        try {
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //assertTrue(output.toString().equals("Test\r\n")); CORRIGER \r
    }

    @AfterEach
    public void endOfTest ()
    { System.out.flush(); }

    @AfterEach
    public void exit ()
    { System.setOut(old); }
}
