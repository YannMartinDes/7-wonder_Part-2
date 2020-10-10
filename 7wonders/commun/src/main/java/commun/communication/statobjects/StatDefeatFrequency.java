package commun.communication.statobjects;

import commun.communication.StatObject;

import java.util.ArrayList;

/** Statistiques de frequence de defaites */
public class StatDefeatFrequency extends StatIntegerBase
{
    /** Constructeur
     * @param size la taille par defaut */
    public StatDefeatFrequency (int size)
    { super(size); }

    /** Constructeur par defaut Jackson */
    public StatDefeatFrequency ()
    { super(1); }

    /** Addition d'une statistique de frequence de defaites
     * @param statObject l'objet des statistiques
     * @param added la liste a ajouter */
    public void add (StatObject statObject, ArrayList<String> added)
    {
        if (this.stat != null && added.size() != this.stat.size())
            throw new IllegalArgumentException("Les tailles sont differentes");

        // i = 1 car le premier est le vainqueur
        for (int i = 1; i < added.size(); i++)
        {
            int index = statObject.getUsernames().indexOf(added.get(i)) - 1;
            this.stat.set(index, this.stat.get(index) + 1);
        }
    }
}