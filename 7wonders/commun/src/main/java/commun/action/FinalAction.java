package commun.action;

/** FinalAction est la classe qui reprsesente l'action finale d'une action
 * c'est une classe qui permet le post-traitement d'une action */
public class FinalAction
{
    /* Champs */
    private boolean isBuildCard = false;
    private boolean isBuildStep = false;
    private boolean cantBuildStep = false;
    private boolean isDiscardCard = false;
    private boolean cantBuildCard = false;
    private int coinEarned = 0;
    private int coinToPay = 0;
    private int coinToPayLeftNeightbour = 0;//Prix à payer au voisin de gauche.
    private int coinToPayRigthNeightbour = 0;//Prix à payer au voisin de droite.

    /** Remet a 0 la classe */
    public void reset ()
    {
        this.coinEarned = 0;
        this.coinToPay = 0;
        this.isDiscardCard = false;
        this.isBuildCard = false;
        this.isBuildStep = false;
        this.cantBuildStep = false;
        this.cantBuildCard = false;
        this.coinToPayLeftNeightbour = 0;
        this.coinToPayRigthNeightbour = 0;
    }

    /* Getters - Setters */

    public boolean isBuildStep() {
        return isBuildStep;
    }

    public void setBuildStep(boolean buildStep) {
        isBuildStep = buildStep;
    }

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
    { return cantBuildCard; }

    public void setCantBuildCard (boolean cantBuildCard)
    { this.cantBuildCard = cantBuildCard; }

    public int getCoinToPayLeftNeightbour() {
        return coinToPayLeftNeightbour;
    }

    public void setCoinToPayLeftNeightbour(int coinToPayLeftNeightbour) {
        this.coinToPayLeftNeightbour = coinToPayLeftNeightbour;
    }

    public int getCoinToPayRigthNeightbour() {
        return coinToPayRigthNeightbour;
    }

    public void setCoinToPayRigthNeightbour(int coinToPayRigthNeightbour) {
        this.coinToPayRigthNeightbour = coinToPayRigthNeightbour;
    }

    public boolean cantBuildStep() {
        return cantBuildStep;
    }

    public void setCantBuildStep(boolean cantBuildStep) {
        this.cantBuildStep = cantBuildStep;
    }
}
