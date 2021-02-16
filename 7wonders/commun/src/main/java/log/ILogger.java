package log;

import java.io.IOException;

public interface ILogger
{
    void put (String msg, String color) throws IOException;
    void put_socket (String msg, String color) throws IOException;
    void log (String msg);
    void log(String msg, String color);
    void log_socket (String msg);
    void logSpaceBefore (String msg);
    void logSpaceBefore(String msg, String color);
    void logSpaceAfter (String msg);
    void logSpaceAfter(String msg, String color);
    void error (String msg);
    void important (String msg);
}
