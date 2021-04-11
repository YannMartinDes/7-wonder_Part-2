package servergame.clientstats;

import commun.communication.CommunicationMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
public class StatsServerRestTemplateTest
{

    RestTemplate restTemplate;

    @Mock
    ResponseEntity<String> response;

    StatsServerRestTemplate statsServerRestTemplate;

    @BeforeEach
    public void init ()
    {
        MockitoAnnotations.openMocks(this);
        restTemplate = Mockito.mock(RestTemplate.class);

        // String result = response.getBody();
        Mockito.when(response.getBody()).thenReturn("A");

        Mockito.when(restTemplate.postForEntity(eq("/" + CommunicationMessages.STATS), any(HttpEntity.class), any(Class.class)))
                .thenReturn(response);

        Mockito.when(restTemplate.postForEntity(eq("/" + CommunicationMessages.FINISHED), any(HttpEntity.class), any(Class.class)))
                .thenReturn(response);

        Mockito.when(restTemplate.getForEntity(eq("/" + CommunicationMessages.STOP), any(Class.class)))
                .thenReturn(response);

        statsServerRestTemplate = new StatsServerRestTemplate(restTemplate);
        statsServerRestTemplate.setURI("");
    }

    @Test
    public void sendStatsTest ()
    {
        statsServerRestTemplate.sendStats(null);
        assertEquals(statsServerRestTemplate.getResponse(), true);
        Mockito.verify(restTemplate, times(1)).postForEntity(eq("/" + CommunicationMessages.STATS), any(HttpEntity.class), any(Class.class));
    }

    @Test
    public void sendStatsTestFailOnCatch ()
    {
        doThrow(RestClientException.class)
                .when(restTemplate)
                .postForEntity(eq("/" + CommunicationMessages.STATS), any(HttpEntity.class), any(Class.class));

        try {
            statsServerRestTemplate.sendStats(null);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(statsServerRestTemplate.getResponse(), false);
        }
    }

    @Test
    public void sendStatsTestFailOnSanitaryCheck ()
    {
        this.statsServerRestTemplate.setServerResponse(false);
        this.statsServerRestTemplate.sendStats(null);
        Mockito.verify(restTemplate, times(0)).postForEntity(eq("/" + CommunicationMessages.STATS), any(HttpEntity.class), any(Class.class));
    }

    @Test
    public void finishStatsTest ()
    {
        this.statsServerRestTemplate.finishStats(5);
        assertEquals(statsServerRestTemplate.getResponse(), true);
        Mockito.verify(restTemplate, times(1)).postForEntity(eq("/" + CommunicationMessages.FINISHED), any(HttpEntity.class), any(Class.class));
        Mockito.verify(restTemplate, times(1)).getForEntity(eq("/" + CommunicationMessages.STOP), any(Class.class));
    }

    @Test
    public void finishStatsTestFail1 ()
    {
        doThrow(RestClientException.class)
                .when(restTemplate)
                .postForEntity(eq("/" + CommunicationMessages.FINISHED), any(HttpEntity.class), any(Class.class));

        try {
            statsServerRestTemplate.finishStats(5);
            throw new Exception();
        } catch (Exception e) {
            Mockito.verify(restTemplate, times(1)).postForEntity(eq("/" + CommunicationMessages.FINISHED), any(HttpEntity.class), any(Class.class));
            Mockito.verify(restTemplate, times(0)).getForEntity(eq("/" + CommunicationMessages.STOP), any(Class.class));
            assertEquals(statsServerRestTemplate.getResponse(), false);
        }
    }

    @Test
    public void finishStatsTestFail2 ()
    {
        doThrow(RestClientException.class)
                .when(restTemplate)
                .getForEntity(eq("/" + CommunicationMessages.STOP), any(Class.class));

        try {
            statsServerRestTemplate.finishStats(5);
            throw new Exception();
        } catch (Exception e) {
            Mockito.verify(restTemplate, times(1)).postForEntity(eq("/" + CommunicationMessages.FINISHED), any(HttpEntity.class), any(Class.class));
            Mockito.verify(restTemplate, times(1)).getForEntity(eq("/" + CommunicationMessages.STOP), any(Class.class));
            assertEquals(statsServerRestTemplate.getResponse(), false);
        }
    }
}
