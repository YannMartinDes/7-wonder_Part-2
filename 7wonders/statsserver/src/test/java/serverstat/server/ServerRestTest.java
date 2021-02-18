package serverstat.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import commun.communication.CommunicationMessages;
import commun.communication.JsonUtils;
import commun.communication.StatObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import serverstat.server.stats.StatObjectOrchestrer;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ServerRestTest {

    @Autowired
    StatObjectOrchestrer statObjectOrchestrer;
    StatObjectOrchestrer mockStatObjectOrchestrer;

    private JsonUtils jsonUtils = new JsonUtils();

    @Autowired
    ServerREST webController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void initTest() {
        mockStatObjectOrchestrer = spy(statObjectOrchestrer);

        // on réinjecte le spy
        ReflectionTestUtils.setField(webController, "statObjectOrchestrer", mockStatObjectOrchestrer);
    }


    @Test
    public void shouldReturnTrue() throws Exception {
        this.mockMvc.perform(post("/essai/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    public void statsReceiveTest() throws Exception{
        StatObject statObject =new StatObject();
        String jsonValue = jsonUtils.serialize(statObject);

        this.mockMvc.perform(post("/serverstats/" + CommunicationMessages.STATS).contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue)).andExpect(status().isAccepted())
                .andExpect(content().string(containsString("Object receive")));

        verify(mockStatObjectOrchestrer,times(1)).addStatObject(any());


    }

    @Test
    public void statsReceiveMultipleTest() throws Exception{
        int n = 34;
        for(int i = 0; i<n;i++) {
            StatObject statObject = new StatObject();
            String jsonValue = jsonUtils.serialize(statObject);

            this.mockMvc.perform(post("/serverstats/" + CommunicationMessages.STATS).contentType(MediaType.APPLICATION_JSON)
                    .content(jsonValue)).andExpect(status().isAccepted())
                    .andExpect(content().string(containsString("Object receive")));


        }
        verify(mockStatObjectOrchestrer, times(n)).addStatObject(any());
    }

    @Test
    public void finishReceivingStatsTest() throws Exception {

        StatObject statObject =new StatObject();
        String jsonValue = jsonUtils.serialize(statObject);

        this.mockMvc.perform(post("/serverstats/" + CommunicationMessages.STATS).contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue));

        Integer integer = 1;
        String jsonVal = objectMapper.writeValueAsString(integer);

        this.mockMvc.perform(post("/serverstats/" + CommunicationMessages.FINISHED).contentType(MediaType.APPLICATION_JSON)
                .content(jsonVal)).andExpect(status().isCreated())
                .andExpect(content().string(containsString("Finish receiving the stats")));

        verify(mockStatObjectOrchestrer, times(1)).finish(any());
    }

    @Test
    public void finishReceivingStatsEmptyTest() throws Exception {
        Integer integer = 1000;
        String jsonVal = objectMapper.writeValueAsString(integer);

        this.mockMvc.perform(post("/serverstats/" + CommunicationMessages.FINISHED).contentType(MediaType.APPLICATION_JSON)
                .content(jsonVal)).andExpect(status().isCreated())
                .andExpect(content().string(containsString("Error : no stats received")));

        verify(mockStatObjectOrchestrer, never()).finish(any());
    }

    @Test
    public void stopStatServerTest() throws Exception {
        this.mockMvc.perform(post("/serverstats/" + CommunicationMessages.STOP).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().string(containsString("")));

//        Thread.sleep(3000);//Wait for the return to be effective
//
//        // normalement, à la fin le client est éteint
//        assertThrows(org.springframework.web.client.ResourceAccessException.class, () -> mockStatObjectOrchestrer.getStatObject());
    }

}
