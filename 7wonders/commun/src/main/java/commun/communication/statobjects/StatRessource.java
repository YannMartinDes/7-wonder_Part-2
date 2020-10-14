package commun.communication.statobjects;

/** StatRessource represente une seule ressource generee */
public class StatRessource extends StatIntegerBase
{
    /** Constructeur
     * @param size la taille par defaut */
    public StatRessource (int size)
    { super(size); }

    /** Constructeur par defaut Jackson */
    public StatRessource ()
    { super(1); }
}
