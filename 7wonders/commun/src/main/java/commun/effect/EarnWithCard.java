package commun.effect;

import commun.card.CardType;

<<<<<<< Updated upstream
public class EarnWithCard {
    private CardType[] cardType;
=======
/** EarnWithCard est une classe qui represente le gain en fonction d'une carte */
public class EarnWithCard
{
    /* Champs */
    private CardType cardType;
>>>>>>> Stashed changes
    private int coinEarn;
    private TargetType affectedNeightbour;
    private int victoryPointEarn;

<<<<<<< Updated upstream
    public EarnWithCard(int earnCoin, int victoryPointEarn, TargetType affectedNeightbour, CardType... cardType) {
=======
    /** Constructeur
     * @param cardType Le type de la carte
     * @param earnCoin Le nombre de monnaies gagnees
     * @param victoryPointEarn Le nombre de point de victoire gagnees
     * @param affectNeightbour Si ca effect les voisins du joueur */
    public EarnWithCard (CardType cardType, int earnCoin, int victoryPointEarn, boolean affectNeightbour)
    {
>>>>>>> Stashed changes
        this.cardType = cardType;
        this.coinEarn = earnCoin;
        this.victoryPointEarn = victoryPointEarn;
        this.affectedNeightbour = affectedNeightbour;
    }

<<<<<<< Updated upstream
    public CardType[] getCardType() {
        return cardType;
    }

    public void setCardType(CardType[] cardType) {
        this.cardType = cardType;
    }
=======
    /* Getters - Setters */

    public CardType getCardType ()
    { return cardType; }
>>>>>>> Stashed changes

    public void setCardType (CardType cardType)
    { this.cardType = cardType; }

    public int getCoinEarn ()
    { return coinEarn; }

<<<<<<< Updated upstream
    public TargetType getAffectedNeightbour() {
        return affectedNeightbour;
    }

    public void setAffectedNeightbour(TargetType affectNeightbour) {
        this.affectedNeightbour = affectNeightbour;
    }
=======
    public void setCoinEarn (int coinEarn)
    { this.coinEarn = coinEarn; }

    public boolean isAffectNeightbour ()
    { return affectNeightbour; }
>>>>>>> Stashed changes

    public void setAffectNeightbour (boolean affectNeightbour)
    { this.affectNeightbour = affectNeightbour; }

    public int getVictoryPointEarn ()
    { return victoryPointEarn; }

    public void setVictoryPointEarn (int victoryPointEarn)
    { this.victoryPointEarn = victoryPointEarn; }
}
