package commun.cost;

public class CoinCost implements ICost{

    private int cost = 0;

    public CoinCost(int cost){
        this.cost = cost;
    }
    public CoinCost(){

    }

    @Override
    public int getCost(){
        return cost;
    }

}
