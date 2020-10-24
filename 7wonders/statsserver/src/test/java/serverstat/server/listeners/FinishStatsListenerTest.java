package serverstat.server.listeners;

import com.corundumstudio.socketio.SocketIOClient;
import commun.communication.StatObject;
import log.GameLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import serverstat.server.Server;
import serverstat.server.stats.StatObjectOrchestrer;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyInt;

public class FinishStatsListenerTest {

    @Mock
    StatObjectOrchestrer statObjectOrchestrer = Mockito.mock(StatObjectOrchestrer.class);
    @Mock
    Server server = Mockito.mock(Server.class);
    @Mock
    SocketIOClient socketIOClient = Mockito.mock(SocketIOClient.class);

    FinishStatsListeners finishStatsListeners;

    @BeforeEach
    public void init() throws IOException {
        GameLogger.verbose_socket = false;

        finishStatsListeners = new FinishStatsListeners(statObjectOrchestrer,server);
    }

    @Test
    public void listenerTest(){

        finishStatsListeners.onData(socketIOClient,1,null);

        Mockito.verify(statObjectOrchestrer,Mockito.times(1)).finish(anyInt());
        Mockito.verify(server,Mockito.times(1)).stopServeur();
        Mockito.verify(socketIOClient,Mockito.times(1)).disconnect();
    }
}
