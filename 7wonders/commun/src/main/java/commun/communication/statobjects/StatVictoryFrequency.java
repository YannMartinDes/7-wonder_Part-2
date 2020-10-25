package commun.communication.statobjects;

import commun.communication.StatObject;
import log.GameLogger;

import java.util.ArrayList;

/** Statistiques de frequence de victoires */
public class StatVictoryFrequency extends StatIntegerBase
{
    /** Constructeur
     * @param size la taille par defaut */
    public StatVictoryFrequency (int size)
    { super(size); }

    /** Constructeur par defaut Jackson */
    public StatVictoryFrequency ()
    { super(1); }

    /** Addition d'une statistique de frequence de victoires
     * @param statObject l'objet des statistiques
     * @param added la liste a ajouter */
    public void add (StatObject statObject, ArrayList<String> added)
    {
        if (this.stat != null && added.size() != this.stat.size())
        {
            GameLogger.getInstance().error("Les tailles sont differentes");
            // throw new IllegalArgumentException("Les tailles sont differentes");
        }
        else
        {
            int index = statObject.getUsernames().indexOf(added.get(0)) - 1;
            if( this.stat!=null) this.stat.set(index, this.stat.get(index) + 1);
        }
    }
}