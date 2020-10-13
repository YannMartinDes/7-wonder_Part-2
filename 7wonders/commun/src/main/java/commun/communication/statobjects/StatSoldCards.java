package commun.communication.statobjects;

/** StatSoldCards represente le nombre de carte vendues */
public class StatSoldCards extends StatIntegerBase
{
    /** Constructeur
     * @param size la taille par defaut */
    public StatSoldCards (int size)
    { super(size); }

    /** Constructeur par defaut Jackson */
    public StatSoldCards ()
    { super(1); }
}
