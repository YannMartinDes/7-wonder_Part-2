package client;

import client.AI.AI;
import client.AI.FirstAI;
import client.AI.RandomAI;
import client.AI.SecondAI;
import client.playerRestTemplate.InscriptionRestTemplate;
import client.playerRestTemplate.PlayerRestTemplate;
import client.utils.CommunicationUtils;
import commun.request.ID;
import log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

@Configuration
public class ConfigurationIA {

    @Autowired
    private InscriptionRestTemplate inscriptionRestTemplate;


    @Bean(name = "id")
    public ID generateID(@Value("${server.port}") String clientPort) throws UnknownHostException {
        CommunicationUtils communicationUtils = new CommunicationUtils();
        Logger.logger.log("mon adresse : "+"http://"+InetAddress.getLocalHost().getHostAddress()+":"+clientPort);
        String playerName = communicationUtils.generatePlayerName();
        Logger.logger.log("Mon nom: " + playerName);
        return new ID("http://"+InetAddress.getLocalHost().getHostAddress()+":"+clientPort,playerName);
    }



    @Bean
    public AI generateAI(@Autowired PlayerRestTemplate playerRequestGame){

        int num = new Random().nextInt(3);
        AI ai = null;

        switch (num){
            case 1:
                ai = new FirstAI();
                break;
            case 2:
                ai = new SecondAI();
                break;
            default:
                ai = new RandomAI();
        }
        Logger.logger.log("Ma stratégie : "+ai.toString());

        ai.setRequestGame(playerRequestGame);
        inscriptionRestTemplate.getId().setStrategy(ai.toString());

        return ai;
    }

    public InscriptionRestTemplate getInscriptionRestTemplate() {
        return inscriptionRestTemplate;
    }

    public void setInscriptionRestTemplate(InscriptionRestTemplate inscriptionRestTemplate) {
        this.inscriptionRestTemplate = inscriptionRestTemplate;
    }
}
