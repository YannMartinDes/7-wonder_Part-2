package commun.effect;

/** EarnWithCardEffect est une classe qui represente l'effet sur le gain en fonction d'une carte */
public class EarnWithCardEffect implements IEffect
{
    /* Champs */
    EarnWithCard earnWithCard;

    /** Constructeur
     * @param earnWithCard le gain en fonction de la carte */
    public EarnWithCardEffect (EarnWithCard earnWithCard)
    { this.earnWithCard = earnWithCard; }
    public EarnWithCardEffect(){}
    /* Getters */

    @Override
    public EarnWithCard getEarnWithCardEffect ()
    { return earnWithCard; }
}
