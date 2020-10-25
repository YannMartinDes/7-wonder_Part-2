package commun.communication.statobjects;

import log.GameLogger;

import java.util.ArrayList;

/** StatIntegerBase represente la base d'une statistique a base d'entiers  */
public class StatIntegerBase extends StatBase<Integer> implements IStat<ArrayList<Integer>>
{
    /** Constructeur
     * @param size la taille par defaut */
    protected StatIntegerBase (int size)
    {
        super();
        for (int i = 0; i < size; i++)
        { this.stat.add(0); }
    }

    /** Addition d'une statistique a base d'entiers
     * @param added les statistiques a ajouter */
    public void add (ArrayList<Integer> added)
    {
        if (this.stat != null && added.size() != this.stat.size())
        {
            GameLogger.getInstance().error("Les tailles sont differentes");
            // throw new IllegalArgumentException("Les tailles sont differentes");
        }
        else
        {
            if( this.stat!=null) {
                for (int i = 0; i < this.stat.size(); i++) {
                    this.stat.set(i, this.stat.get(i) + added.get(i));
                }
            }
        }
    }
}
