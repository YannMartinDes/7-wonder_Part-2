package commun.communication.statobjects;

/** StatConflicts represente les statistiques sur les conflits militaires */
public class StatConflicts extends StatIntegerBase
{
    /** Constructeur
     * @param size la taille par defaut */
    public StatConflicts (int size)
    { super(size); }

    /** Constructeur par defaut Jackson */
    public StatConflicts ()
    { super(1); }
}
