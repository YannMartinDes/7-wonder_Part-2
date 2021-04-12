package client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

@SpringBootApplication
public class App {


    public static void main(String[] args) {
        HashMap<String,Object> properties = new HashMap<>();
        SpringApplication app = new SpringApplication(App.class);
        String profile = System.getenv("PROFILE");
        if(profile== null) {
            properties.put("spring.profiles.active", "prod");
        }else{
            properties.put("spring.profiles.active", profile);
        }


        String gameIP = System.getenv("GAME_IP");
        if (gameIP == null) gameIP = "host.docker.internal";
        String gamePort = System.getenv("GAME_PORT");
        if (gamePort == null) gamePort = "1336";

        properties.put("gameServer.uri", "http://"+gameIP+":"+gamePort);
        app.setDefaultProperties(properties);

        app.run(args);
    }

}
