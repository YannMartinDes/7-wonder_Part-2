package severgame.clientstats;

import commun.communication.JsonUtils;
import commun.communication.StatObject;
import io.socket.client.Socket;
import log.GameLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import servergame.clientstats.SocketManager;

import java.io.IOException;

import static org.mockito.Matchers.anyString;


class SocketManagerTest {
    @Mock
    Socket socket = Mockito.mock(Socket.class);
    @Mock
    JsonUtils jsonUtils = Mockito.mock(JsonUtils.class);

    SocketManager socketManager;
    StatObject statObject;

    @BeforeEach
    void init(){
        GameLogger.verbose_socket = false;
        try
        {
            socketManager = new SocketManager("http://127.0.0.1:1335");
        } finally {

        }
        socketManager = new SocketManager(socket,jsonUtils);
        statObject = new StatObject();
    }

    @Test
    void socketManagerTest() throws IOException {
        socketManager.send(statObject);

        Mockito.verify(socket,Mockito.times(1)).emit(anyString(),anyString());

        socketManager.finish(5);

        Mockito.verify(socket,Mockito.times(2)).emit(anyString(),anyString());
    }
}
