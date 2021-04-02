package client.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Classe utilitaire servant a avoir des fonctions pratiques sur la communication avec le jeu
 */
@Component
@Scope("singleton")
public class CommunicationUtils
{
    /** Constructeur par defaut */
    public CommunicationUtils () {}

    /**
     * Cette fonction permet de generer le nom du joueur automatiquement
     * TODO: Changer par une generation plus lisible
     * @return le nouveau nom du joueur
     */
    public String generatePlayerName ()
    {
        return "Player_" + Long.toHexString(Double.doubleToLongBits(Math.random()));
    }
}
