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
public class App {


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

}
