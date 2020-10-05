package commun.communication.statobjects;

/** StatMoney permet de faire des statistiques sur la monnaie */
public class StatMoney extends StatIntegerBase
{
    /** Constructeur
     * @param size la taille par defaut */
    public StatMoney (int size)
    { super(size); }

    /** Constructeur par defaut Jackson */
    public StatMoney ()
    { super(1); }
}