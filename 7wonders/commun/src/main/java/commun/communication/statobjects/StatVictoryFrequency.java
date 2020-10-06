package commun.communication.statobjects;

import commun.communication.StatObject;

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
            throw new IllegalArgumentException("Les tailles sont differentes");

        int index = statObject.getUsernames().indexOf(added.get(0)) - 1;
        this.stat.set(index, this.stat.get(index) + 1);
    }
}