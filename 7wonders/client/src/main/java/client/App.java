package client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

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
