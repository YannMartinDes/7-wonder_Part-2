package commun.action;

/** FinalAction est la classe qui reprsesente l'action finale d'une action
 * c'est une classe qui permet le post-traitement d'une action */
public class FinalAction
{
    /* Champs */
    private boolean isBuildCard = false;
    private boolean isDiscardCard = false;
    private boolean canBuildCard = false;
    private int coinEarned = 0;
    private int coinToPay = 0;

    /** Remet a 0 la classe */
    public void reset ()
    {
        this.coinEarned = 0;
        this.coinToPay = 0;
        this.isDiscardCard = false;
        this.isBuildCard = false;
        this.canBuildCard = false;
    }

    /* Getters - Setters */

    public int getCoinEarned ()
    { return coinEarned; }

    public void setCoinEarned (int coinEarned)
    { this.coinEarned = coinEarned; }

    public int getCoinToPay ()
    { return coinToPay; }

    public void setCoinToPay (int coinToPay)
    { this.coinToPay = coinToPay; }

    public boolean isBuildCard ()
    { return isBuildCard; }

    public void setBuildCard (boolean buildCard)
    { isBuildCard = buildCard; }

    public boolean isDiscardCard ()
    { return isDiscardCard; }

    public void setDiscardCard (boolean discardCard)
    { isDiscardCard = discardCard; }

    public boolean cantBuildCard ()
    { return canBuildCard; }

    public void setCantBuildCard (boolean canBuildCard)
    { this.canBuildCard = canBuildCard; }
}
