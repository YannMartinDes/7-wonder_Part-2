package commun.communication.statobjects;

/** StatIntegerBase represente la base d'une statistique a base d'entiers  */
public class StatCard extends StatIntegerBase
{
    /** Constructeur
     * @param size la taille par defaut */
    public StatCard (int size)
    { super(size); }

    /** Constructeur par defaut Jackson */
    public StatCard ()
    { super(1); }
}