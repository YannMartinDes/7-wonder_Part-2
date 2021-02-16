package servergame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App2 {



    public static void main(String args[])
    {
        SpringApplication app = new SpringApplication(App2.class);
        app.run(args);
    }
}
