package servergame.integration;

import commun.communication.StatObject;
import log.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import servergame.clientstats.StatsServerRestTemplate;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
public class StatsServerTestIT
{

    StatObject statObject;


    @Autowired
    private StatsServerRestTemplate statsServerRestTemplateTest;


    @Before
    public void connect()
    {
        statObject = new StatObject();
        ReflectionTestUtils.setField(statsServerRestTemplateTest, "URI", "http://0.0.0.0:1335/serverstats");
        System.out.println("uri :"+statsServerRestTemplateTest.getURI());
    }

    @Test
    public void sendStatsWorkTest()
    {
        Logger.logger.verbose = true;
        Logger.logger.verbose_socket = true;

        statsServerRestTemplateTest.sendStats(statObject);
        boolean serverResponse = (boolean)ReflectionTestUtils.getField(statsServerRestTemplateTest,"serverResponse");

        assertTrue(serverResponse);
    }

/*

    @Test
    public void sendStatsDontWorkTest()
    {
        Logger.logger.verbose = false;
        Logger.logger.verbose_socket = false;

        statsServerRestTemplateTest.sendStats(statObject);
        ResponseEntity<String> response  = (ResponseEntity<String>) ReflectionTestUtils.getField(statsServerRestTemplateTest,"response");
        assertTrue(response.getStatusCode().equals(HttpStatus.OK));
    }


 */
    @Test
    public void finishStatsTest()
    {
        Logger.logger.verbose = false;
        Logger.logger.verbose_socket = false;

        statsServerRestTemplateTest.finishStats(new Integer(1000));
        HttpHeaders httpHeaders = (HttpHeaders)ReflectionTestUtils.getField(statsServerRestTemplateTest,"headers");
        HttpEntity<Integer>  httpEntity = (HttpEntity<Integer>)ReflectionTestUtils.getField(statsServerRestTemplateTest,"httpEntity");

        //ResponseEntity<String> response  = (ResponseEntity<String>) ReflectionTestUtils.getField(statsServerRestTemplateTest,"response");
        //assertTrue(response.getStatusCode().equals(HttpStatus.OK));

        assertNotNull(httpHeaders);
        assertNotNull(httpEntity);

    }

}