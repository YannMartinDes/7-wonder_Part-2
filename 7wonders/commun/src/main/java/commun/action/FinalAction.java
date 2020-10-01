package commun.action;

import commun.card.Card;

public class FinalAction {
    private Card buildCard = null;
    private int coinEarned = 0;
    private Card discardCard = null;
    private int coinToPay = 0;
    private Card cantBuildCard = null;

    public void reset(){
        this.buildCard = null;
        this.discardCard = null;
        this.cantBuildCard = null;
        this.coinEarned = 0;
        this.coinToPay = 0;
    }

    public Card getBuildCard() {
        return buildCard;
    }

    public void setBuildCard(Card buildCard) {
        this.buildCard = buildCard;
    }

    public int getCoinEarned() {
        return coinEarned;
    }

    public void setCoinEarned(int coinEarned) {
        this.coinEarned = coinEarned;
    }

    public Card getDiscardCard() {
        return discardCard;
    }

    public void setDiscardCard(Card discardCard) {
        this.discardCard = discardCard;
    }

    public int getCoinToPay() {
        return coinToPay;
    }

    public void setCoinToPay(int coinToPay) {
        this.coinToPay = coinToPay;
    }

    public Card getCantBuildCard() {
        return cantBuildCard;
    }

    public void setCantBuildCard(Card cantBuildCard) {
        this.cantBuildCard = cantBuildCard;
    }
}
