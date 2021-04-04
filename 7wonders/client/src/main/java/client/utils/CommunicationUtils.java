package client.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Classe utilitaire servant a avoir des fonctions pratiques sur la communication avec le jeu
 */
@Component
@Scope("singleton")
public class CommunicationUtils
{
    private  String[] names = new String[]{"Sardoche","Bauza","Paf le chien", "AngryNerd","Alan Turing", "Hamilton", "Chuck Norris", "Furious Kid"};
    private Random random = new SecureRandom();

    /** Constructeur par defaut */
    public CommunicationUtils () {}

    /**
     * Cette fonction permet de generer le nom du joueur automatiquement
     * @return le nouveau nom du joueur
     */
    public String generatePlayerName ()
    {
        return names[(random.nextInt(names.length))];
    }
}
