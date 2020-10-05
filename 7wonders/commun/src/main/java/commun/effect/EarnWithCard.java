package commun.effect;

import commun.card.CardType;

public class EarnWithCard {
    private CardType[] cardType;
    private int coinEarn;
    private TargetType affectedNeightbour;
    private int victoryPointEarn;

    public EarnWithCard(int earnCoin, int victoryPointEarn, TargetType affectedNeightbour, CardType... cardType) {
        this.cardType = cardType;
        this.coinEarn = earnCoin;
        this.victoryPointEarn = victoryPointEarn;
        this.affectedNeightbour = affectedNeightbour;
    }

    public CardType[] getCardType() {
        return cardType;
    }

    public void setCardType(CardType[] cardType) {
        this.cardType = cardType;
    }

    public int getCoinEarn() {
        return coinEarn;
    }

    public void setCoinEarn(int coinEarn) {
        this.coinEarn = coinEarn;
    }

    public TargetType getAffectedNeightbour() {
        return affectedNeightbour;
    }

    public void setAffectedNeightbour(TargetType affectNeightbour) {
        this.affectedNeightbour = affectNeightbour;
    }

    public int getVictoryPointEarn() {
        return victoryPointEarn;
    }

    public void setVictoryPointEarn(int victoryPointEarn) {
        this.victoryPointEarn = victoryPointEarn;
    }

}
