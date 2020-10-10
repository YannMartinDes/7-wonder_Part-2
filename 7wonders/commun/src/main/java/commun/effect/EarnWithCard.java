package commun.effect;

import commun.card.CardType;

/** EarnWithCard est une classe qui represente le gain en fonction d'une carte */
public class EarnWithCard
{
    /* Champs */
    private CardType[] cardType;
    private int coinEarn;
    private TargetType affectedNeightbour;
    private int victoryPointEarn;

    /** Constructeur
     * @param cardType Le type de la carte
     * @param earnCoin Le nombre de monnaies gagnees
     * @param victoryPointEarn Le nombre de point de victoire gagnees
     * @param affectedNeightbour Si ca effect les voisins du joueur */
    public EarnWithCard(int earnCoin, int victoryPointEarn, TargetType affectedNeightbour, CardType... cardType)
    {
        this.cardType = cardType;
        this.coinEarn = earnCoin;
        this.victoryPointEarn = victoryPointEarn;
        this.affectedNeightbour = affectedNeightbour;
    }

    /* Getters - Setters */
    public CardType[] getCardType ()
    { return cardType; }


    public int getCoinEarn ()
    { return coinEarn; }


    public TargetType getAffectedNeightbour ()
    { return affectedNeightbour; }


    public int getVictoryPointEarn ()
    { return victoryPointEarn; }

}
