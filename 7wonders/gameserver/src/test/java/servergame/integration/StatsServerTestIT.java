package servergame.integration;

import commun.communication.StatObject;
import log.Logger;
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
import org.springframework.web.client.RestTemplate;
import servergame.clientstats.StatsServerRestTemplate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class StatsServerTestIT
{

    StatObject statObject;
    RestTemplate restTemplate;


    @Autowired
    private StatsServerRestTemplate statsServerRestTemplateTest;


    @BeforeEach
    public void connect()
    {
        statObject = new StatObject();


        ReflectionTestUtils.setField(statsServerRestTemplateTest, "URI", "http://172.28.0.253:1335/serverstats");

    }

    @Test
    public void sendStatsWorkTest()
    {
        Logger.logger.verbose = false;
        Logger.logger.verbose_socket = false;


        statsServerRestTemplateTest.sendStats(statObject);
        boolean serverResponse = (boolean)ReflectionTestUtils.getField(statsServerRestTemplateTest,"serverResponse");
        assertFalse(serverResponse);
    }
/*


    @Test
    public void sendStatsDontWorkTest()
    {
        Logger.logger.verbose = false;
        Logger.logger.verbose_socket = false;

        ReflectionTestUtils.setField(,"restTemplate");

        statsServerRestTemplateTest.sendStats(statObject);
        boolean serverResponse = (boolean)ReflectionTestUtils.getField(statsServerRestTemplateTest,"serverResponse");

        assertFalse(serverResponse);
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

        assertNotNull(httpHeaders);
        assertNotNull(httpEntity);

    }


}

/*
mvn clean install -DskipTests
mvn test
docker build 7wonders:gameserver -t scrabble:joueur
cd moteur
docker run -d --name joueur_test  -e LANCEMENT="POUR_LES_TEST" -e PORT="8081" -p 8081:8081 scrabble:joueur
mvn failsafe:integration-test -Dit.test=prg.exemple.demoscrabble.MoteurWebControleurITCase#demanderAuJoueurDeJoueurTest
docker start joueur_test
mvn failsafe:integration-test -Dit.test=prg.exemple.demoscrabble.MoteurWebControleurITCase#demanderAuJoueurDeJoueurTest2foisDeSuite
*/
