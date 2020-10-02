package commun.effect;

import commun.card.CardType;

public class EarnWithCard {
    private CardType cardType;
    private int coinEarn;
    private boolean affectNeightbour;
    private int victoryPointEarn;

    public EarnWithCard(CardType cardType, int earnCoin, int victoryPointEarn, boolean affectNeightbour) {
        this.cardType = cardType;
        this.coinEarn = earnCoin;
        this.victoryPointEarn = victoryPointEarn;
        this.affectNeightbour = affectNeightbour;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public int getCoinEarn() {
        return coinEarn;
    }

    public void setCoinEarn(int coinEarn) {
        this.coinEarn = coinEarn;
    }

    public boolean isAffectNeightbour() {
        return affectNeightbour;
    }

    public void setAffectNeightbour(boolean affectNeightbour) {
        this.affectNeightbour = affectNeightbour;
    }

    public int getVictoryPointEarn() {
        return victoryPointEarn;
    }

    public void setVictoryPointEarn(int victoryPointEarn) {
        this.victoryPointEarn = victoryPointEarn;
    }

}
