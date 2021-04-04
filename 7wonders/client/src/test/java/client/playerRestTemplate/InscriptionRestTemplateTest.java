package client.playerRestTemplate;

import commun.request.ID;
import log.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InscriptionRestTemplateTest
{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    ResponseEntity<String> responseEntity = Mockito.mock(ResponseEntity.class);

    @Mock
    RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

    InscriptionRestTemplate inscriptionRestTemplate;
    String URI = "test/inscription";

    @BeforeEach
    public void init()
    {
        Logger.logger.verbose = false;

        inscriptionRestTemplate = new InscriptionRestTemplate();
        inscriptionRestTemplate.setId(new ID(this.URI,"testN"));
        inscriptionRestTemplate.setURI("test");
    }

    @Test
    public void inscriptionTest()
    {
        Mockito.when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        Mockito.when(restTemplate.postForEntity(eq(URI),any(HttpEntity.class), any(Class.class))).thenReturn(responseEntity);

        ReflectionTestUtils.setField(inscriptionRestTemplate, "restTemplate", restTemplate);
        inscriptionRestTemplate.inscription();

        assertEquals(this.URI, inscriptionRestTemplate.getId().getUri());
        assertEquals("testN", inscriptionRestTemplate.getId().getName());
        Mockito.verify(restTemplate,Mockito.times(1)).postForEntity(eq(URI),any(HttpEntity.class),any(Class.class));
    }

    @Test
    public void initPositionTestOk() throws Exception
    {
        int id = 5;

        String jsonValue = objectMapper.writeValueAsString(id);
        this.mockMvc.perform(post("/id").contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue)).andExpect(status().isOk());
    }

    @Test
    public void initPositionTestNotOk() throws Exception
    {
        String id = "test";

        String jsonValue = objectMapper.writeValueAsString(id);
        this.mockMvc.perform(post("/id").contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue)).andExpect(status().isBadRequest());
    }

    @Test
    public void initNbPlayerTestOk() throws Exception
    {
        int nbPlayers = 4;
        String jsonValue = objectMapper.writeValueAsString(nbPlayers);
        this.mockMvc.perform(post("/nplayers").contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue)).andExpect(status().isOk());
    }

    @Test
    public void initNbPlayerTestNotOk() throws Exception
    {
        String nbPlayers = "test";
        String jsonValue = objectMapper.writeValueAsString(nbPlayers);
        this.mockMvc.perform(post("/nplayers").contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue)).andExpect(status().isBadRequest());
    }

}
