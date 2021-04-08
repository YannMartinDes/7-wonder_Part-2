package client;

import client.playerRestTemplate.InscriptionRestTemplate;
import commun.request.ID;
import log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
//cette classe est executée uniquement si on est pas dans le profile test test
public class ApplicationStarter {

    @Bean
    @Profile("prod")
    public CommandLineRunner run(@Autowired InscriptionRestTemplate inscriptionRestTemplate){
        return args -> {
            Logger.logger.log("IP serveur de jeu : " + inscriptionRestTemplate.getURI());
            boolean isInscris = inscriptionRestTemplate.inscription();
            if(!isInscris){
                System.exit(0);
            }
        };
    }

    @Bean
    @Profile("IT")
    public CommandLineRunner runIT(@Autowired InscriptionRestTemplate inscriptionRestTemplate){
        return args -> {
            Logger.logger.log("Translate URI for IT : http://"+System.getenv("IP")+":"+System.getenv("PORT"));
            ID idTest = inscriptionRestTemplate.getId();
            idTest.setUri("http://"+System.getenv("IP")+":"+System.getenv("PORT"));
            Logger.logger.log("IP serveur de jeu : " + inscriptionRestTemplate.getURI());

            //tentative d'inscription
            int i = 0;
            boolean isInscris = false;
            Thread.sleep(15000);
            while (i<30 && !isInscris) {
                isInscris = inscriptionRestTemplate.inscription();
                i++;
                Thread.sleep(2000);
            }
            if(!isInscris){
                System.exit(0);
            }
        };
    }
}
