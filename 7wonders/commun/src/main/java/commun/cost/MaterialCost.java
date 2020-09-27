package commun.cost;

import commun.material.Material;

public class MaterialCost implements ICost{

    private Material materialCost;
    public MaterialCost( Material materialCost)
    {
        this.materialCost=materialCost;
    }

    @Override
    public Material getMaterialCost() {
        return materialCost;
    }
}
