package commun.cost;

/** CoinCost represente la classe qui gere le cout en monnaie */
public class CoinCost implements ICost
{
    /* Champs */
    private int cost = 0;

    /** Constructeur
     * @param cost le cout */
    public CoinCost (int cost)
    { this.cost = cost; }

    /** Constructeur par defaut */
    public CoinCost ()
    {}

    /* Getters */

    @Override
    public int getCoinCost ()
    { return cost; }

    /** canBuyCard permet de savoir si la carte est jouable
     * selon le nombre de monnaie d'un joueur
     * @param playerCoins la monnaie d'un joueur
     * @return true si oui, false sinon
     */
    public boolean canBuyCard (int playerCoins)
    { return playerCoins >= this.cost; }
}
