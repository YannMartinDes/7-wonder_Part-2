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
     * @param effects la liste de tout les effet possedant par le joueur
     * @return true -> peut etre acheter
     */
    @Override
    public boolean canBuyCard(EffectList effects)
    {
    	MaterialType materialForBuild = materialCost.getType();
    	int materialNumberForBuild = materialCost.getNumber();
    	//pour chaque effet
    	for(IEffect effect : effects)
    	{
            //si c'est un effets ressource et qu'il correspond a la ressource du batiment
    		if(effect.getMaterial() !=null && effect.getMaterial().getType().equals(materialForBuild)) {
    			materialNumberForBuild -= effect.getMaterial().getNumber();
    		}
            //si c'est un effets ressource a choix et que l'une des deux correspond a la ressource attendue
    		else if(effect.getChoiceMaterial().getMaterial1() != null && effect.getChoiceMaterial().getMaterial1() != null)
    		{
    			if(effect.getChoiceMaterial().getMaterial1().getType().equals(materialForBuild))
    			{
                    materialNumberForBuild -=effect.getChoiceMaterial().getMaterial1().getNumber();
                }
                if(effect.getChoiceMaterial().getMaterial2().getType().equals(materialForBuild))
                {
                    materialNumberForBuild -=effect.getChoiceMaterial().getMaterial2().getNumber();
                }

    		}
    		if(materialNumberForBuild<=0) return true;
    	}

    	return false;
    	
    }
}
