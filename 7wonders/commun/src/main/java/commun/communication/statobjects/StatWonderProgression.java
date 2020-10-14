package commun.communication.statobjects;

/** StatWonderProgression represente le taux de progression de la merveille sur 3 */
public class StatWonderProgression extends StatIntegerBase
{
    /** Constructeur
     * @param size la taille par defaut */
    public StatWonderProgression (int size)
    { super(size); }

    /** Constructeur par defaut Jackson */
    public StatWonderProgression ()
    { super(1); }
}
