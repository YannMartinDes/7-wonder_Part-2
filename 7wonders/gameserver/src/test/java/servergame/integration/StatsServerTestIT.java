package servergame.integration;

import commun.communication.CommunicationMessages;
import commun.communication.StatObject;
import log.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import servergame.clientstats.StatsServerRestTemplate;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
public class StatsServerTestIT
{

    StatObject statObject;


    @SpyBean
    private StatsServerRestTemplate statsServerRestTemplateTest;

    private RestTemplate restTemplate;


    @Before
    public void connect()
    {
        Logger.logger.verbose = false;
        Logger.logger.verbose_socket = false;
        restTemplate = Mockito.spy(new RestTemplate());

        statObject = new StatObject();
        ReflectionTestUtils.setField(statsServerRestTemplateTest, "URI", "http://0.0.0.0:1335/"+CommunicationMessages.SERVERSTATS);
        ReflectionTestUtils.setField(statsServerRestTemplateTest,"restTemplate",restTemplate);
        System.out.println("uri :"+statsServerRestTemplateTest.getURI());
    }

    @Test
    public void sendStatsWorkTest()
    {
        statsServerRestTemplateTest.sendStats(statObject);
        //on envoie bien un requete au serveur
        verify(restTemplate,times(1)).postForEntity(anyString(),any(),any());
        boolean serverResponse = (boolean)ReflectionTestUtils.getField(statsServerRestTemplateTest,"serverResponse");

        assertTrue(serverResponse);
    }



    @Test
    public void sendStatsServerDownTest()
    {
        statsServerRestTemplateTest.sendStats(statObject);
        //on envoie bien un requete au serveur
        verify(restTemplate,times(1)).postForEntity(anyString(),any(),any());

        //on a pas de reponse on met le boolean serverResponse a false pour evite d'attendre le timeout
        // (on fait sans la connexion pour la suite)
        boolean serverResponse = (boolean)ReflectionTestUtils.getField(statsServerRestTemplateTest,"serverResponse");
        //absence de reponse
        assertFalse(serverResponse);

        //on appelle plus la methode postFor entity
        for(int i = 0; i<50;i++){
            statsServerRestTemplateTest.sendStats(statObject);
        }

        //reste a 1
        verify(restTemplate,times(1)).postForEntity(anyString(),any(),any());
    }



    @Test
    public void finishStatsTest() throws InterruptedException {
        statsServerRestTemplateTest.finishStats(1000);
        HttpHeaders httpHeaders = (HttpHeaders)ReflectionTestUtils.getField(statsServerRestTemplateTest,"headers");
        HttpEntity<Integer>  httpEntity = (HttpEntity<Integer>)ReflectionTestUtils.getField(statsServerRestTemplateTest,"httpEntity");
        //on a bien eu une reponse
        assertNotNull(httpHeaders);
        assertNotNull(httpEntity);

        //on envoie donc requete pour dire au serveur de stat de ce fermé
        verify(restTemplate,times(1))
                .postForEntity(eq(statsServerRestTemplateTest.getURI()+"/"+ CommunicationMessages.FINISHED)
                        ,any(),any());

        //le temps que le serveur ce ferme
        Thread.sleep(4000);

        boolean serverResponse = (boolean)ReflectionTestUtils.getField(statsServerRestTemplateTest,"serverResponse");

        //on avais bien des reponse du serveur
        assertTrue(serverResponse);
        //on envoie une requete
        statsServerRestTemplateTest.sendStats(statObject);
        //on a pas reussi a joindre le serveur donc il est bien stopé
        serverResponse = (boolean)ReflectionTestUtils.getField(statsServerRestTemplateTest,"serverResponse");
        assertFalse(serverResponse);


    }

    @Test
    public void realUseTest()
    {
        int time = 50;
        boolean serverResponse = (boolean)ReflectionTestUtils.getField(statsServerRestTemplateTest,"serverResponse");
        assertTrue(serverResponse);
        for(int i = 0; i<time;i++){
            statsServerRestTemplateTest.sendStats(statObject);
        }
        //on a bien reussi a joindre a chaque fois le serveur sinon le boolean serait passer a faux
        serverResponse = (boolean)ReflectionTestUtils.getField(statsServerRestTemplateTest,"serverResponse");
        assertTrue(serverResponse);

        //on lui dit que l'on a fini
        statsServerRestTemplateTest.finishStats(time);
        HttpHeaders httpHeaders = (HttpHeaders)ReflectionTestUtils.getField(statsServerRestTemplateTest,"headers");
        HttpEntity<Integer>  httpEntity = (HttpEntity<Integer>)ReflectionTestUtils.getField(statsServerRestTemplateTest,"httpEntity");
        //on a bien eu une reponse
        assertNotNull(httpHeaders);
        assertNotNull(httpEntity);


    }



}