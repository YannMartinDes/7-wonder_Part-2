package client;

import client.AI.AI;
import client.AI.FirstAI;
import client.AI.RandomAI;
import client.AI.SecondAI;
import client.playerRestTemplate.InscriptionRestTemplate;
import client.playerRestTemplate.PlayerRestTemplate;
import client.utils.CommunicationUtils;
import commun.request.ID;
import commun.request.PlayerRequestGame;
import log.Logger;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Random;

@SpringBootApplication
@Configuration
public class App {

    @Autowired
    InscriptionRestTemplate inscriptionRestTemplate;

    @Autowired
    Environment environment;

    //Todo: Voir pourquoi le @Autowired ne marche pas
    @Autowired
    CommunicationUtils communicationUtils = new CommunicationUtils();

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);

        HashMap<String,Object> properties = new HashMap<>();
        String gameIP = System.getenv("GAME_IP");
        if (gameIP == null) gameIP = "0.0.0.0";
        String gamePort = System.getenv("GAME_PORT");
        if (gamePort == null) gamePort = "1336";

        properties.put("gameServer.uri", "http://"+gameIP+":"+gamePort);
        app.setDefaultProperties(properties);

        app.run(args);
    }



    @Bean(name = "id")
    public ID generateID(@Value("${server.port}") String clientPort) throws UnknownHostException {

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
            case 0:
                ai = new RandomAI();
                break;
            case 1:
                ai = new FirstAI();
                break;
            case 2:
                ai = new SecondAI();
                break;
        }
        Logger.logger.log("Ma stratégie : "+ai.toString());

        ai.setRequestGame(playerRequestGame);
        inscriptionRestTemplate.getId().setStrategy(ai.toString());

        return ai;
    }

    @Bean
    public CommandLineRunner run(){
        return args -> {
            System.out.println("IP serveur de jeu : "+inscriptionRestTemplate.getURI());
            inscriptionRestTemplate.inscription();
        };
    }

    public InscriptionRestTemplate getInscriptionRestTemplate() {
        return inscriptionRestTemplate;
    }

    public void setInscriptionRestTemplate(InscriptionRestTemplate inscriptionRestTemplate) {
        this.inscriptionRestTemplate = inscriptionRestTemplate;
    }
}
