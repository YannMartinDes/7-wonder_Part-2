package commun.action;

import commun.card.Card;

public class FinalAction {
    private boolean isBuildCard = false;
    private boolean isDiscardCard = false;
    private boolean canBuildCard = false;
    private int coinEarned = 0;
    private int coinToPay = 0;

    public void reset(){
        this.coinEarned = 0;
        this.coinToPay = 0;
        isDiscardCard = false;
        isBuildCard = false;
        canBuildCard = false;
    }



    public int getCoinEarned() {
        return coinEarned;
    }

    public void setCoinEarned(int coinEarned) {
        this.coinEarned = coinEarned;
    }

    public int getCoinToPay() {
        return coinToPay;
    }

    public void setCoinToPay(int coinToPay) {
        this.coinToPay = coinToPay;
    }

    public boolean isBuildCard() {
        return isBuildCard;
    }

    public void setBuildCard(boolean buildCard) {
        isBuildCard = buildCard;
    }

    public boolean isDiscardCard() {
        return isDiscardCard;
    }

    public void setDiscardCard(boolean discardCard) {
        isDiscardCard = discardCard;
    }

    public boolean cantBuildCard() {
        return canBuildCard;
    }

    public void setCantBuildCard(boolean canBuildCard) {
        this.canBuildCard = canBuildCard;
    }
}
