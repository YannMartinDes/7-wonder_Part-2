package commun.effect;

public class EarnWithWonder {

    private int coinEarn;
    private TargetType affectedNeightbour;
    private int victoryPointEarn;

    public EarnWithWonder(TargetType affectedNeightbour, int coinEarn, int victoryPointEarn) {
        this.coinEarn = coinEarn;
        this.affectedNeightbour = affectedNeightbour;
        this.victoryPointEarn = victoryPointEarn;
    }

    public int getCoinEarn() {
        return coinEarn;
    }

    public TargetType getAffectedNeightbour() {
        return affectedNeightbour;
    }

    public int getVictoryPointEarn() {
        return victoryPointEarn;
    }
}
