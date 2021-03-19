package serverstat.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.stefanbirkner.systemlambda.SystemLambda;
import commun.communication.CommunicationMessages;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        String  jsonValue = objectMapper.writeValueAsString(statObject);

        this.mockMvc.perform(post("/"+CommunicationMessages.SERVERSTATS + "/" + CommunicationMessages.STATS).contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue)).andExpect(status().isAccepted())
                .andExpect(content().string(containsString("Object receive")));

        verify(mockStatObjectOrchestrer,times(1)).addStatObject(any());
    }

    @Test
    public void statsReceiveMultipleTest() throws Exception{
        int n = 34;
        for(int i = 0; i<n;i++) {
            StatObject statObject = new StatObject();
            String jsonValue = objectMapper.writeValueAsString(statObject);

            this.mockMvc.perform(post("/"+CommunicationMessages.SERVERSTATS + "/" +  CommunicationMessages.STATS).contentType(MediaType.APPLICATION_JSON)
                    .content(jsonValue)).andExpect(status().isAccepted())
                    .andExpect(content().string(containsString("Object receive")));


        }
        verify(mockStatObjectOrchestrer, times(n)).addStatObject(any());
    }

    @Test
    public void statsReceiveBadParamTest() throws Exception{

        String  badValue = "im a bad value";

        this.mockMvc.perform(post("/"+CommunicationMessages.SERVERSTATS + "/" + CommunicationMessages.STATS).contentType(MediaType.APPLICATION_JSON)
                .content(badValue)).andExpect(status().is(400));

        verify(mockStatObjectOrchestrer,never()).addStatObject(any());
    }


    @Test
    public void finishReceivingStatsTest() throws Exception {

        StatObject statObject =new StatObject();
        String jsonValue = objectMapper.writeValueAsString(statObject);
        System.out.println(jsonValue);

        this.mockMvc.perform(post("/"+CommunicationMessages.SERVERSTATS + "/" +  CommunicationMessages.STATS).contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue));

        Integer integer = 1;
        String jsonVal = objectMapper.writeValueAsString(integer);

        this.mockMvc.perform(post("/"+CommunicationMessages.SERVERSTATS + "/" + CommunicationMessages.FINISHED).contentType(MediaType.APPLICATION_JSON)
                .content(jsonVal)).andExpect(status().isCreated())
                .andExpect(content().string(containsString("Finish receiving the stats")));

        verify(mockStatObjectOrchestrer, times(1)).finish(any());
    }

    @Test
    public void finishReceivingStatsEmptyTest() throws Exception {
        Integer integer = 1000;
        String jsonVal = objectMapper.writeValueAsString(integer);

        this.mockMvc.perform(post("/"+CommunicationMessages.SERVERSTATS + "/" +  CommunicationMessages.FINISHED).contentType(MediaType.APPLICATION_JSON)
                .content(jsonVal)).andExpect(status().isCreated())
                .andExpect(content().string(containsString("Error : no stats received")));

        verify(mockStatObjectOrchestrer, never()).finish(any());
    }


    @Test
    public void finishReceivingStatsBadValueTest() throws Exception{
        String  badValue = "im a bad value";

        this.mockMvc.perform(post("/"+CommunicationMessages.SERVERSTATS + "/" +  CommunicationMessages.FINISHED).contentType(MediaType.APPLICATION_JSON)
                .content(badValue)).andExpect(status().is(400));

        verify(mockStatObjectOrchestrer,never()).finish(any());
    }

    @Test
    public void stopStatServerTest() throws Exception {


        int status = SystemLambda.catchSystemExit(() -> {
            this.mockMvc.perform(post("/"+CommunicationMessages.SERVERSTATS + "/" + CommunicationMessages.STOP).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isAccepted())
                    .andExpect(content().string(containsString("")));

            Thread.sleep(5000);//Wait for the return to be effective

            // normalement, à la fin le client est éteint
            //assertThrows(org.springframework.web.client.ResourceAccessException.class, () -> mockStatObjectOrchestrer.getStatObject());

        });
        assertEquals(0,status);

    }

}
