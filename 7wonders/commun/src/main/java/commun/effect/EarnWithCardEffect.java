package commun.effect;

public class EarnWithCardEffect implements IEffect {
    EarnWithCard earnWithCard;

    public EarnWithCardEffect(EarnWithCard earnWithCard){
        this.earnWithCard = earnWithCard;
    }

    @Override
    public EarnWithCard getEarnWithCardEffect() {
        return earnWithCard;
    }
}
