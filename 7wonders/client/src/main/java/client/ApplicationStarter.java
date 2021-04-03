package client;

import client.playerRestTemplate.InscriptionRestTemplate;
import log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
//cette classe est executée uniquement si on est pas dans le profile test test
public class ApplicationStarter {

    @Bean
    public CommandLineRunner run(@Autowired InscriptionRestTemplate inscriptionRestTemplate){
        return args -> {
            Logger.logger.log("IP serveur de jeu : " + inscriptionRestTemplate.getURI());
            inscriptionRestTemplate.inscription();
        };
    }
}
