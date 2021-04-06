package client;

import client.AI.AI;
import client.playerRestTemplate.InscriptionRestTemplate;
import client.playerRestTemplate.PlayerRestTemplate;
import commun.request.ID;
import log.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConfigurationIATest {

    ConfigurationIA configurationIA;

    InscriptionRestTemplate inscriptionRestTemplate = Mockito.mock(InscriptionRestTemplate.class);


    @BeforeEach
    public void init(){
        Logger.logger.verbose = false;

        configurationIA = new ConfigurationIA();

        Mockito.when(inscriptionRestTemplate.getId()).thenReturn(new ID("/", "test"));
        configurationIA.setInscriptionRestTemplate(inscriptionRestTemplate);
    }

    @Test
    public void generateIDTest() throws UnknownHostException {
        String[] names = new String[]{"Sardoche","Bauza","Paf le chien", "AngryNerd","Alan Turing", "Hamilton", "Chuck Norris", "Furious Kid"};
        List namesList = Arrays.asList(names);

        for(int i=0; i<20;i++){
            ID id = configurationIA.generateID("666");

            assertTrue(namesList.contains(id.getName()));
            assertEquals("http://"+ InetAddress.getLocalHost().getHostAddress()+":666", id.getUri());
        }
    }

    @Test
    public void generateAITest() {
        String[] names = new String[]{"SecondAI","RandomAI","FirstAI"};
        List namesList = Arrays.asList(names);

        PlayerRestTemplate playerRestTemplate = new PlayerRestTemplate();

        for(int i=0; i<20;i++){
            AI ai = configurationIA.generateAI(playerRestTemplate);

            Mockito.verify(inscriptionRestTemplate, new Times(i+1)).getId();
            assertTrue(namesList.contains(ai.toString()));

            assertEquals(playerRestTemplate, ai.getRequestGame());
        }
    }
}
