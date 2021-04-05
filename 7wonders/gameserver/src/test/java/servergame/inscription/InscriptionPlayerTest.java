package servergame.inscription;

import com.fasterxml.jackson.databind.ObjectMapper;
import commun.communication.CommunicationMessages;
import commun.request.ID;
import log.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InscriptionPlayerTest
{
    @Autowired
    InscriptionPlayer inscriptionPlayer;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

    @BeforeEach
    void initTest() {
        Logger.logger.verbose = false;
        inscriptionPlayer.setRestTemplate(restTemplate);
        inscriptionPlayer.setPlayerWaitList(new ArrayList<>());
        inscriptionPlayer.setNbPlayerWaited(5);
    }

    @Test
    public void inscriptionTestOK() throws Exception
    {
        inscriptionPlayer.setInscriptionOpen(true);
        inscriptionPlayer.setPlayerWaitList(new ArrayList<>());

        List<ID> myIds = new ArrayList<>();
        int nbPlayers = 3 ;
        String URI = "/test";

        for (int i = 0; i < nbPlayers ; i++)
        {
            ID id = new ID(URI,"test"+i);
            myIds.add(id);
            String jsonValue = objectMapper.writeValueAsString(id);
            this.mockMvc.perform(post("/inscription").contentType(MediaType.APPLICATION_JSON)
                    .content(jsonValue)).andExpect(status().isOk());
        }

        List<ID> ids = inscriptionPlayer.getPlayerWaitList();
        assertEquals(nbPlayers, ids.size());

        for (int i = 0; i < nbPlayers ; i++)
        {
            assertEquals(myIds.get(i).getName(), ids.get(i).getName());
            assertEquals(myIds.get(i).getUri(), ids.get(i).getUri());
        }

    }

    @Test
    public void inscriptionTestImUsed() throws Exception
    {
        inscriptionPlayer.setInscriptionOpen(true);

        ID id = new ID("/test", "test");
        String jsonValue = objectMapper.writeValueAsString(id);

        this.mockMvc.perform(post("/inscription").contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue)).andExpect(status().isOk());

        this.mockMvc.perform(post("/inscription").contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue)).andExpect(status().is(HttpStatus.IM_USED.value()));

        List<ID> ids = inscriptionPlayer.getPlayerWaitList();
        assertEquals(id.getName(), ids.get(0).getName());
        assertEquals(ids.size(), 1);
    }

    @Test
    public void inscriptionTestTooMany() throws Exception
    {
        inscriptionPlayer.setInscriptionOpen(true);

        List<ID> myIds = new ArrayList<>();
        int nbPlayers = inscriptionPlayer.getNbPlayerWaited() ; //nbMax
        String URI = "/test";

        for (int i = 0; i < nbPlayers ; i++)
        {
            ID id = new ID(URI,"test"+i);
            myIds.add(id);
            String jsonValue = objectMapper.writeValueAsString(id);

            this.mockMvc.perform(post("/inscription").contentType(MediaType.APPLICATION_JSON)
                    .content(jsonValue)).andExpect(status().isOk());
        }

        //la fois de plus qui ne doit pas passer
        ID id = new ID(URI,"testError");
        String jsonValue = objectMapper.writeValueAsString(id);

        this.mockMvc.perform(post("/inscription").contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue)).andExpect(status().is(HttpStatus.TOO_MANY_REQUESTS.value()));

        List<ID> ids = inscriptionPlayer.getPlayerWaitList();
        assertEquals(nbPlayers, ids.size());

        for (int i = 0; i < nbPlayers ; i++)
        {
            assertEquals(myIds.get(i).getName(), ids.get(i).getName());
            assertEquals(myIds.get(i).getUri(), ids.get(i).getUri());
        }

    }

    @Test
    public void inscriptionTestBadRequest() throws Exception
    {
        inscriptionPlayer.setInscriptionOpen(true);

        ID id = new ID("/test", null);
        assertTrue(id.getName() == null);
        String jsonValue = objectMapper.writeValueAsString(id);

        this.mockMvc.perform(post("/inscription").contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue)).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        List<ID> ids = inscriptionPlayer.getPlayerWaitList();
        assertEquals(ids.size(), 0);
    }

    @Test
    public void waitInscriptionFinishTest()
    {

        inscriptionPlayer.setInscriptionOpen(false);
        inscriptionPlayer.setNbPlayerWaited(0);

        List<ID> ids = inscriptionPlayer.waitInscriptionFinish();

        assertTrue(inscriptionPlayer.readyToStart());
        assertTrue(inscriptionPlayer.isInscriptionOpen());
        assertEquals(inscriptionPlayer.getPlayerWaitList().size(),ids.size());
        assertEquals(0,ids.size() );
    }

    @Test
    public void sendPlayerPositionTest()
    {
        int nbPlayers = inscriptionPlayer.getNbPlayerWaited() ;
        String URI = "/test";

        for (int i = 0; i < nbPlayers ; i++)
        {
            ID id = new ID(URI,"test"+i);
            inscriptionPlayer.sendPlayerPosition(id);
            Mockito.verify(restTemplate, Mockito.times(i+1)).postForEntity(Mockito.eq(URI+"/id"), Mockito.any(HttpEntity.class),
                    Mockito.any(Class.class));
        }
    }

    @Test
    public void sendNbPlayersTest()
    {
        int nbPlayers = inscriptionPlayer.getNbPlayerWaited() ;
        String URI = "/test";

        for (int i = 0; i < nbPlayers ; i++)
        {
            ID id = new ID(URI,"test"+i);
            inscriptionPlayer.sendNbPlayers(id);
            Mockito.verify(restTemplate, Mockito.times(i+1)).postForEntity(Mockito.eq(id.getUri() +"/nplayers"), Mockito.any(HttpEntity.class),
                    Mockito.any(Class.class));
        }
    }

    @Test
    public void sendStopPlayerTest()
    {
        String URI = "/test";
        List<ID> myIds = new ArrayList<>();
        int nbPlayers = inscriptionPlayer.getNbPlayerWaited() ;

        for (int i = 0; i < nbPlayers ; i++)
        {
            myIds.add(new ID(URI,"test"+i));
        }

        inscriptionPlayer.setPlayerWaitList(myIds);
        inscriptionPlayer.sendStopPlayer();

        Mockito.verify(restTemplate, Mockito.times(nbPlayers))
                .delete(Mockito.eq(URI+ "/"+ CommunicationMessages.STOP));

    }
}
