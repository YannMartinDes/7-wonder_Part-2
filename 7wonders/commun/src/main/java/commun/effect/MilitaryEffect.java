package commun.effect;

/** MilitaryEffect est une class qui représente le gain militaire */
public class MilitaryEffect implements IEffect
{
    /* Champs */
    private int nbEffect;

    /** Constructeur
     * @param nbEffect le nombre de gains */
    public MilitaryEffect (int nbEffect)
    { this.nbEffect = nbEffect; }

    /* Getters */
    @Override
    public int getMilitaryEffect()
    { return nbEffect; }
}
