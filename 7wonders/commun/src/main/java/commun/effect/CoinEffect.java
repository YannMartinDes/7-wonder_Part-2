package commun.effect;

/**  */
public class CoinEffect implements IEffect
{
    /* Champs */
    private int numberOfCoin = 0;

    /** Constructeur
     * @param numberOfCoin le nombre de monnaies */
    public CoinEffect (int numberOfCoin)
    { this.numberOfCoin = numberOfCoin; }

    /** Constructeur */
    public CoinEffect () {}

    /* Getters */

    @Override
    public int getNumberOfCoin ()
    { return this.numberOfCoin; }

}
