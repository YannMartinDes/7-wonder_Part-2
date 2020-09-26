package commun.cost;

public class CostInCoin implements ICost{

    private int cost = 0;

    public CostInCoin(int cost){
        this.cost = cost;
    }
    public CostInCoin(){

    }

    @Override
    public int getCost(){
        return cost;
    }

}
