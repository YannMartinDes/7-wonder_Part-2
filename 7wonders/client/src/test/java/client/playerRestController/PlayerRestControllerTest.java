package client.playerRestController;

import client.AI.AI;
import client.App;
import client.ConfigurationIA;
import com.fasterxml.jackson.databind.ObjectMapper;
import commun.action.AbstractAction;
import commun.action.DiscardAction;
import commun.card.Deck;
import commun.communication.CommunicationMessages;
import commun.effect.ScientificType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerRestControllerTest {

    @Mock
    AI mockAi = Mockito.mock(AI.class);


    @Autowired
    PlayerRestController webController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void initTest() {
        // on injecte le spy
        webController.setAi(mockAi);
    }

    @Test
    public void chooseActionTest() throws Exception{
        Deck deck = new Deck();
        AbstractAction action = new DiscardAction(1);

        Mockito.when(mockAi.chooseAction(any(Deck.class))).thenReturn(action);

        String jsonValue = objectMapper.writeValueAsString(deck);

        this.mockMvc.perform(post(CommunicationMessages.CHOOSEACTION).contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue)).andExpect(status().isAccepted())
                .andExpect(content().string(objectMapper.writeValueAsString(action)));

        //L'objet est valide
        objectMapper.readValue(objectMapper.writeValueAsString(action), AbstractAction.class);

        verify(mockAi,times(1)).chooseAction(any(Deck.class));
    }


    @Test
    public void choosePurchaseTest() throws Exception{
        List<Integer[]> purchaseChoice = new ArrayList<>();
        Integer[] purchase = new Integer[]{5,5,5};

        Mockito.when(mockAi.choosePurchasePossibility(any())).thenReturn(purchase);

        String jsonValue = objectMapper.writeValueAsString(purchaseChoice);

        this.mockMvc.perform(post(CommunicationMessages.CHOOSEPURCHASE).contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue)).andExpect(status().isAccepted())
                .andExpect(content().string(objectMapper.writeValueAsString(purchase)));

        //L'objet est valide
        objectMapper.readValue(objectMapper.writeValueAsString(purchase), Integer[].class);

        verify(mockAi,times(1)).choosePurchasePossibility(any());
    }

    @Test
    public void scientificTypeTest() throws Exception{
        ScientificType scientificType = ScientificType.GEOMETRY;

        Mockito.when(mockAi.useScientificsGuildEffect()).thenReturn(scientificType);

        this.mockMvc.perform(get(CommunicationMessages.CHOOSESCIENTIFICS))
                .andExpect(status().isAccepted())
                .andExpect(content().string(objectMapper.writeValueAsString(scientificType)));

        //L'objet est valide
        objectMapper.readValue(objectMapper.writeValueAsString(scientificType), ScientificType.class);

        verify(mockAi,times(1)).useScientificsGuildEffect();
    }

    @Test
    public void chooseCardTest() throws Exception{
        Deck deck = new Deck();
        int rep = 5;

        Mockito.when(mockAi.chooseCard(any(Deck.class))).thenReturn(rep);

        String jsonValue = objectMapper.writeValueAsString(deck);

        this.mockMvc.perform(post(CommunicationMessages.CHOOSECARD).contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue)).andExpect(status().isAccepted())
                .andExpect(content().string(objectMapper.writeValueAsString(rep)));

        //L'objet est valide
        objectMapper.readValue(objectMapper.writeValueAsString(rep), int.class);

        verify(mockAi,times(1)).chooseCard(any(Deck.class));
    }


    @Test
    public void stopClient() throws Exception
    {
        this.mockMvc.perform(delete("/" + CommunicationMessages.STOP))
                .andExpect(status().isOk());

        assertEquals(mockAi,this.webController.getAi());
    }
}
