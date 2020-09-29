package commun.effect;

public class CoinEffect implements IEffect
{
    private int numberOfCoin=0;

    public CoinEffect(int score)
    {
        this.numberOfCoin = numberOfCoin;
    }

    public CoinEffect() {
    }

    @Override
    public int getNumberOfCoin() {
        return this.numberOfCoin;
    }

}
