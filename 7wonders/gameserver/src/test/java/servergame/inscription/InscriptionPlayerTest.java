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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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


    RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

    String URI = "http://test.fr";

    @BeforeEach
    void initTest() {
        Logger.logger.verbose = false;
        inscriptionPlayer.setRestTemplate(restTemplate);
        inscriptionPlayer.setPlayerWaitList(new ArrayList<>());
    }

    @Test
    public void inscriptionTestOK() throws Exception
    {
        //pour tout les nombre de joueur possible dans la partie
        for(int nbPlayers = 3; nbPlayers<=7;nbPlayers++){
            //reinitialisation de l'inscription
            inscriptionPlayer.setInscriptionOpen(true);
            inscriptionPlayer.setPlayerWaitList(new ArrayList<>());
            List<ID> myIds = new ArrayList<>();


            for (int i = 0; i < nbPlayers ; i++)
            {
                //creation de la requete d'inscription du joueur
                ID id = new ID(URI,"test"+i);
                myIds.add(id);
                String jsonValue = objectMapper.writeValueAsString(id);
                //envoie de l'insciption au jeu (a chaque fois c'est ok)
                this.mockMvc.perform(post("/inscription").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonValue)).andExpect(status().isOk());
            }
            //on a bien recus toutes les inscription
            List<ID> ids = inscriptionPlayer.getPlayerWaitList();
            assertEquals(nbPlayers, ids.size());

            for (int i = 0; i < nbPlayers ; i++)
            {
                //on a bien la tout les joueur qui on bien etait enregistrer
                assertEquals(myIds.get(i).getName(), ids.get(i).getName());
                assertEquals(myIds.get(i).getUri(), ids.get(i).getUri());
            }
        }

    }

    @Test
    public void inscriptionTestImUsed() throws Exception
    {
        inscriptionPlayer.setInscriptionOpen(true);


        ID id = new ID(URI, "test");
        String jsonValue = objectMapper.writeValueAsString(id);

        this.mockMvc.perform(post("/inscription").contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue)).andExpect(status().isOk());

        //on inscrit une deuxieme personne avec le meme nom => le nom est deja utiliser
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
        int nbPlayers = inscriptionPlayer.MAX_PLAYER_REQUIRED ; //nbMax

        //on inscrit un maximum de joueur
        for (int i = 0; i < nbPlayers ; i++)
        {
            ID id = new ID(URI,"test"+i);
            myIds.add(id);
            String jsonValue = objectMapper.writeValueAsString(id);
            //evoie de l'inscription
            this.mockMvc.perform(post("/inscription").contentType(MediaType.APPLICATION_JSON)
                    .content(jsonValue)).andExpect(status().isOk());
        }

        //la fois de plus qui ne doit pas passer (atteinte du cap max)
        ID id = new ID(URI,"testError");
        String jsonValue = objectMapper.writeValueAsString(id);

        this.mockMvc.perform(post("/inscription").contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue)).andExpect(status().is(HttpStatus.TOO_MANY_REQUESTS.value()));

        List<ID> ids = inscriptionPlayer.getPlayerWaitList();
        assertEquals(nbPlayers, ids.size()); //on a bien que nbPlayers inscription

        for (int i = 0; i < nbPlayers ; i++)
        {
            //on retrouve bien les nbPlayers premier inscrit avec succes
            assertEquals(myIds.get(i).getName(), ids.get(i).getName());
            assertEquals(myIds.get(i).getUri(), ids.get(i).getUri());
        }

    }

    @Test
    public void inscriptionTestBadRequest() throws Exception
    {
        inscriptionPlayer.setInscriptionOpen(true);

        ID id = new ID(URI, null);
        assertTrue(id.getName() == null);
        String jsonValue = objectMapper.writeValueAsString(id);
        //ID mal renseigner du client => on recois un BAD_REQUEST
        this.mockMvc.perform(post("/inscription").contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue)).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        List<ID> ids = inscriptionPlayer.getPlayerWaitList();
        assertEquals(ids.size(), 0);
    }

    @Test
    public void waitInscriptionFinishTest()
    {
        //ouverture de l'inscription
        inscriptionPlayer.setInscriptionOpen(true);
        //on fait en sorte que l'on ai le max de joueur inscrit
        for (int i = 0; i < inscriptionPlayer.MAX_PLAYER_REQUIRED ; i++) {
            ID id = new ID(URI, "test" + i);
            inscriptionPlayer.getPlayerWaitList().add(id);
        }

        //la partie est bien prete a commencer
        assertTrue(inscriptionPlayer.readyToStart());
        //on cette fonction ce finit uniquement quand l'inscription est valide (readyToStart -> true)
        List<ID> ids = inscriptionPlayer.waitInscriptionFinish();

        //l'inscription c'est bien fermer et on a bien les player inscrit de retourner
        assertFalse(inscriptionPlayer.isInscriptionOpen()); //l'inscription est fermer
        assertEquals(inscriptionPlayer.getPlayerWaitList().size(),ids.size());
        assertEquals(inscriptionPlayer.MAX_PLAYER_REQUIRED,ids.size() );
    }

    @Test
    public void readyToStartMaxNbPlayer()
    {
        //La parti n'est pas prete a commencer
        assertFalse(inscriptionPlayer.readyToStart());

        //on fait en sorte que l'on ai le max de joueur inscrit
        for (int i = 0; i < inscriptionPlayer.MAX_PLAYER_REQUIRED ; i++) {
            ID id = new ID(URI, "test" + i);
            inscriptionPlayer.getPlayerWaitList().add(id);
        }

        //la partie est bien prete a commencer
        assertTrue(inscriptionPlayer.readyToStart());
    }

    @Test
    public void readyToStartTime()
    {
        //La parti n'est pas prete a commencer
        assertFalse(inscriptionPlayer.readyToStart());

        //on set le temps de la derniere inscription 60 seconde avant
        ReflectionTestUtils.setField(inscriptionPlayer,"lastInscription",System.currentTimeMillis()-60*1000);

        //On ne peut pas lancer car il y a pas asser de joueur
        assertFalse(inscriptionPlayer.readyToStart());

        //on rajoute le nombre minimum de joueur
        for (int i = 0; i < inscriptionPlayer.MIN_PLAYER_REQUIRED ; i++) {
            ID id = new ID(URI, "test" + i);
            inscriptionPlayer.getPlayerWaitList().add(id);
        }
        //on met le temps de maintenant
        ReflectionTestUtils.setField(inscriptionPlayer,"lastInscription",System.currentTimeMillis());
        //on ne peut toujour pas lancer car il faut attendre de voir si il y a pas d'autre joueur qui s'ajoute
        assertFalse(inscriptionPlayer.readyToStart());

        //on set le temps de la derniere inscription 60 seconde avant
        ReflectionTestUtils.setField(inscriptionPlayer,"lastInscription",System.currentTimeMillis()-60*1000);

        //la partie est bien prete a commencer avec les 3 joueur
        assertTrue(inscriptionPlayer.readyToStart());
    }

    @Test
    public void sendPlayerPositionTest()
    {
        int nbPlayers = inscriptionPlayer.MAX_PLAYER_REQUIRED ;

        for (int i = 0; i < nbPlayers ; i++)
        {
            ID id = new ID(URI,"test"+i);
            inscriptionPlayer.sendPlayerPosition(id,i);
            Mockito.verify(restTemplate, Mockito.times(i+1)).postForEntity(Mockito.eq(URI+"/id"), Mockito.any(HttpEntity.class),
                    Mockito.any(Class.class));
        }
    }

    @Test
    public void sendNbPlayersTest()
    {
        int nbPlayers = inscriptionPlayer.MAX_PLAYER_REQUIRED ;

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
        List<ID> myIds = new ArrayList<>();
        int nbPlayers = inscriptionPlayer.MAX_PLAYER_REQUIRED ;

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
