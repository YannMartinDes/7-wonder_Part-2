package client.playerRestTemplate;

import commun.communication.CommunicationMessages;
import commun.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;

public class PlayerRestTemplateTest {

    RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

    ResponseEntity<Player> responseEntity = Mockito.mock(ResponseEntity.class);

    ResponseEntity<Player> responseEntity2 = Mockito.mock(ResponseEntity.class);

    ResponseEntity<Player> responseEntity3 = Mockito.mock(ResponseEntity.class);

    PlayerRestTemplate playerRestTemplate;

    @BeforeEach
    public void init(){

        Player player = new Player("toto");
        Mockito.when(responseEntity.getBody()).thenReturn(player);
        Player player2 = new Player("titi");
        Mockito.when(responseEntity2.getBody()).thenReturn(player2);
        Player player3 = new Player("tata");
        Mockito.when(responseEntity3.getBody()).thenReturn(player3);

        String URI = "test/"+ CommunicationMessages.BOARD +"/";

        Mockito.when(restTemplate.getForEntity(eq(URI+"0"), any(Class.class))).thenReturn(responseEntity);
        Mockito.when(restTemplate.getForEntity(eq(URI+"1"), any(Class.class))).thenReturn(responseEntity2);
        Mockito.when(restTemplate.getForEntity(eq(URI+"2"), any(Class.class))).thenReturn(responseEntity3);

        playerRestTemplate = new PlayerRestTemplate(restTemplate);
        playerRestTemplate.setURI("test");
        playerRestTemplate.setPlayerID(0);
        playerRestTemplate.setNbPlayer(3);
    }

    /**
     * Verfier que le client envois bien la demande d'accée à sa wonderbord
     */
    @Test
    public void getMeTest(){
        Player player = playerRestTemplate.getMe();

        assertEquals("toto",player.getName());
        Mockito.verify(restTemplate,Mockito.times(1)).getForEntity(anyString(), any(Class.class));
    }

    /**
     * Verfier que le client envois bien la demande d'accée à la wonderbord de son voisin de droite
     */
    @Test
    public void getRightNeighbours(){
        Player player = playerRestTemplate.getRightNeighbours();

        assertEquals("titi",player.getName());
        Mockito.verify(restTemplate,Mockito.times(1)).getForEntity(anyString(), any(Class.class));
    }

    /**
     * Verfier que le client envois bien la demande d'accée à la wonderbord de son voisin de gauche
     */
    @Test
    public void getLeftNeighbours(){
        Player player = playerRestTemplate.getLeftNeighbours();

        assertEquals("tata",player.getName());
        Mockito.verify(restTemplate,Mockito.times(1)).getForEntity(anyString(), any(Class.class));
    }

    /**
     * Verfier que le client envois bien la demande d'accée à tout les joueurs
     */
    @Test
    public void getAllPlayers(){
        List<Player> allPlayer = playerRestTemplate.getAllPlayers();
        ArrayList<String> names = new ArrayList<>();
        names.add("titi"); names.add("toto"); names.add("tata");

        assertEquals(3, allPlayer.size());

        for(Player p : allPlayer){
            assertTrue(names.contains(p.getName()));
        }

        Mockito.verify(restTemplate,Mockito.times(3)).getForEntity(anyString(), any(Class.class));

    }
}
