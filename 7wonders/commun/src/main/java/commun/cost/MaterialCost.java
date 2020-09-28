package commun.cost;

import commun.effect.EffectList;
import commun.effect.IEffect;
import commun.material.Material;
import commun.material.MaterialType;

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
    
    
    /**
     * Savoir si la carte peut etre acheter
     * @param effets la liste de tout les effet possedant par le joueur
     * @return true -> peut etre acheter
     */
    @Override
    public boolean canBuyCard(EffectList effects) {	
    	MaterialType materialForBuild = materialCost.getType();
    	int materialNumberForBuild = materialCost.getNumber();
    	
    	for(IEffect effect : effects) {

    		
    		if(effect.getMaterial() !=null && effect.getMaterial().getType().equals(materialForBuild)) {
    			materialNumberForBuild -= effect.getMaterial().getNumber();
    		}
    		else if(effect.getChoiceMaterial().getMaterial1() != null) {
    			Material mat1 = effect.getChoiceMaterial().getMaterial1()
    		}
    	}
    	return true;
    	
    }
}
