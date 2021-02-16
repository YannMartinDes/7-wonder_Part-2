package serverstat.server.listeners;

import commun.communication.JsonUtils;
import commun.communication.StatObject;
import log.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import serverstat.server.stats.StatObjectOrchestrer;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;

class StatsListenerTest {

    StatsListener statsListener;
    StatObject statObject;
    JsonUtils jsonUtils = new JsonUtils();
    String json;
    @Mock
    StatObjectOrchestrer statObjectOrchestrer = Mockito.mock(StatObjectOrchestrer.class);

    @BeforeEach
    void init() throws IOException {
        Logger.logger.verbose_socket = false;
        statObject = new StatObject();
        statsListener = new StatsListener(statObjectOrchestrer);
        json = jsonUtils.serialize(statObject);
    }

    @Test
    void listenerTest(){
        try {
            statsListener.onData(null,json,null);

            Mockito.verify(statObjectOrchestrer,Mockito.times(1)).addStatObject(any(StatObject.class));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
