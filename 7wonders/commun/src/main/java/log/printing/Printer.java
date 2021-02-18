package log.printing;

public class Printer
{
    private Printer () {}

    public final static PrinterModule printer = PrinterModule.getInstance();

    public static void exit ()
    { Printer.printer.exit(); }
}
