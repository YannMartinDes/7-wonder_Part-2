package client;

import client.AI.AI;
import client.AI.RandomAI;
import client.playerRestTemplate.InscriptionRestTemplate;
import commun.request.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Configuration
public class App {

    @Autowired
    InscriptionRestTemplate inscriptionRestTemplate;

    //@Value("${server.port}")
    String clientPort="12345";

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        app.run(args);
    }

    @Bean
    public ID generateID() throws UnknownHostException {
        System.out.println("mon adresse : "+"http://"+InetAddress.getLocalHost().getHostAddress()+":"+clientPort);
        return new ID("http://"+InetAddress.getLocalHost().getHostAddress()+":"+clientPort,"TODO");
    }

    @Bean
    public AI generateAI(){
        //TODO generate randome ai
        AI ai = new RandomAI();
        return ai;
    }

    @Bean
    public CommandLineRunner run(){
        return args -> {
            String gameIP = System.getenv("GAME_IP");
            if (gameIP == null) gameIP = "0.0.0.0";
            String gamePort = System.getenv("GAME_PORT");
            if (gamePort == null) gamePort = "1336";
            inscriptionRestTemplate.setURI("http://"+gameIP+":"+gamePort);
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
