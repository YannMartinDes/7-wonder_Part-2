package client;

import client.AI.AI;
import client.AI.RandomAI;
import client.playerRestTemplate.InscriptionRestTemplate;
import commun.request.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@Configuration
public class App {

    @Autowired
    InscriptionRestTemplate inscriptionRestTemplate;
    public static void main(String[] args) {
        new SpringApplication(App.class).run(args);
    }

    @Bean
    public ID generateID() throws UnknownHostException {
        return new ID("http://"+InetAddress.getLocalHost().getHostAddress(),"TODO");
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
