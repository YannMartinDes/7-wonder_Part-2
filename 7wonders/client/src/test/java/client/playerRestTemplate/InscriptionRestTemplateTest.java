package client.playerRestTemplate;

import commun.request.ID;
import log.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class InscriptionRestTemplateTest
{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    ResponseEntity<String> responseEntity = Mockito.mock(ResponseEntity.class);

    PlayerRestTemplate playerRestTemplate = Mockito.mock(PlayerRestTemplate.class);

    RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

    @Autowired
    InscriptionRestTemplate inscriptionRestTemplate;
    String URI = "test/inscription";

    @BeforeEach
    public void init()
    {
        Logger.logger.verbose = false;

        inscriptionRestTemplate.playerRestTemplate = playerRestTemplate;
        inscriptionRestTemplate.setId(new ID(this.URI,"testN"));
        inscriptionRestTemplate.setURI("test");
    }

    /**
     * Ce test permet de verifier que lorsque le client essais d'inscrire à partie,
     * toutes les étapes se deroulent comme prevus et que la demande d'inscription est envoyer
     */
    @Test
    public void inscriptionTest()  {
        Mockito.when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        Mockito.when(restTemplate.postForEntity(eq(URI),any(HttpEntity.class), any(Class.class))).thenReturn(responseEntity);

        ReflectionTestUtils.setField(inscriptionRestTemplate, "restTemplate", restTemplate);
        inscriptionRestTemplate.inscription();

        assertEquals(this.URI, inscriptionRestTemplate.getId().getUri());
        assertEquals("testN", inscriptionRestTemplate.getId().getName());
        Mockito.verify(restTemplate,Mockito.times(1)).postForEntity(eq(URI),any(HttpEntity.class),any(Class.class));
    }

    /**
     * Ce test permet de verifier que lorsque le joueur est inscrit, il reçois bien une position
     * par le server
     * @throws Exception
     */
    @Test
    public void initPositionTestOk() throws Exception
    {
        int id = 5;

        String jsonValue = objectMapper.writeValueAsString(id);
        this.mockMvc.perform(post("/id").contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue)).andExpect(status().isOk());
        verify(playerRestTemplate,times(1)).setPlayerID(anyInt());

    }

    /**
     * Ce test permet de verifier que lorsque le joueur n'a pas pus s'inscrire, il ne reçois pas une position
     * par le server
     * @throws Exception
     */
    @Test
    public void initPositionTestNotOk() throws Exception
    {
        String id = "test";

        String jsonValue = objectMapper.writeValueAsString(id);
        this.mockMvc.perform(post("/id").contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue)).andExpect(status().isBadRequest());
        verify(playerRestTemplate,never()).setPlayerID(anyInt());

    }

    /**
     * Ce test permet de verifier que lorsque le joueur est inscrit à une partie,
     * au debut de celle ci il reçois bien par le server le nombre des joueurs inscrit
     */
    @Test
    public void initNbPlayerTestOk() throws Exception
    {
        int nbPlayers = 4;
        String jsonValue = objectMapper.writeValueAsString(nbPlayers);
        this.mockMvc.perform(post("/nplayers").contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue)).andExpect(status().isOk());

        verify(playerRestTemplate,times(1)).setNbPlayer(anyInt());
    }

    /**
     * Ce test permet de verifier que lorsque le joueur n'a pas pus s'inscrire  à une partie,
     *  il ne reçois pas le nombre des joueurs inscrit
     */
    @Test
    public void initNbPlayerTestNotOk() throws Exception
    {
        String nbPlayers = "test";
        String jsonValue = objectMapper.writeValueAsString(nbPlayers);
        this.mockMvc.perform(post("/nplayers").contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue)).andExpect(status().isBadRequest());
        verify(playerRestTemplate,never()).setNbPlayer(anyInt());

    }

}
