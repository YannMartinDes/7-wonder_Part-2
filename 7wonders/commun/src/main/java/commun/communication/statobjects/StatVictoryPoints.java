package commun.communication.statobjects;

/** StatVictoryPoints represente les statistiques sur les points de victoire */
public class StatVictoryPoints extends StatIntegerBase
{
    /** Constructeur par defaut Jackson */
    public StatVictoryPoints ()
    { super(1); }

    /** Constructeur
     * @param size la taille par defaut */
    public StatVictoryPoints (int size)
    { super(size); }
}
