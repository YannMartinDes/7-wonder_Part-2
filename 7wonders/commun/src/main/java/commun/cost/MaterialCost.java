package commun.cost;

import commun.cost.solver.MaterialsCostArray;
import commun.cost.solver.MaterialsCostSolver;
import commun.effect.EffectList;
import commun.effect.IEffect;
import commun.material.Material;
import commun.material.MaterialType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaterialCost implements ICost{

    private Material[] materialCost;
    public MaterialCost( Material... materialCost)
    {
        this.materialCost=materialCost;
    }

    @Override
    public Material[] getMaterialCost() {
        return materialCost;
    }
    

    public boolean canBuyCard (EffectList effects)
    {
        
        MaterialsCostSolver solver = new MaterialsCostSolver(materialCost,effects);
        return solver.canBuyCard();
    }

    public List<MaterialsCostArray[]> soluceBuyNeighbours(EffectList myEffect, EffectList left, EffectList right){
        MaterialsCostSolver solver = new MaterialsCostSolver(materialCost,myEffect);
        List<MaterialsCostArray[]> result = solver.soluceBuyNeighbours(left,right);
        return result;

    }



}
