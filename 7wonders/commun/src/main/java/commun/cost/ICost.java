package commun.cost;


import commun.material.Material;

public interface ICost
{
    public default int getCost() {
        return 0;
    }
    public default Material getMaterialCost() {
        return null;
    }

}
