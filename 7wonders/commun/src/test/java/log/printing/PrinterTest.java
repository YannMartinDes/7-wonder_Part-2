package log.printing;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PrinterTest
{

    @Test
    public void exitTest()
    {
        Printer printer = new Printer();
        printer.exit();
        assertNotNull(printer.printer);
    }
}
