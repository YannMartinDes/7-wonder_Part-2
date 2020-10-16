package commun.communication.statobjects;

/** StatScientificScore represente le score fourni par les cartes scientifiques possedes */
public class StatScientificScore extends StatIntegerBase
{
    /** Constructeur
     * @param size la taille par defaut */
    public StatScientificScore (int size)
    { super(size); }

    /** Constructeur par defaut Jackson */
    public StatScientificScore ()
    { super(1); }
}
