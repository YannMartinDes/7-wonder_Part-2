package commun.cost;

import commun.effect.AddingMaterialEffet;
import commun.effect.EffectList;
import commun.effect.IEffect;
import commun.material.Material;
import commun.material.MaterialType;

import java.util.ArrayList;
import java.util.HashMap;
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
    

//    /**
//     * Savoir si la carte peut etre acheter
//     * @param effects la liste de tout les effet possedant par le joueur
//     * @return true -> peut etre acheter
//     */
//    @Override
//    public boolean canBuyCard(EffectList effects)
//    {
//    	MaterialType materialForBuild;// = materialCost.getType();
//    	int materialNumberForBuild = 0;// = materialCost.getNumber();
//    	//pour chaque effet
//    	for(IEffect effect : effects)
//    	{
//            //si c'est un effets ressource et qu'il correspond a la ressource du batiment
//    		if(effect.getMaterial() !=null && effect.getMaterial().getType().equals(materialForBuild)) {
//    			materialNumberForBuild -= effect.getMaterial().getNumber();
//    		}
//            //si c'est un effets ressource a choix et que l'une des deux correspond a la ressource attendue
//    		else if(effect.getChoiceMaterial() != null)
//    		{
//    			if(effect.getChoiceMaterial().getMaterial1().getType().equals(materialForBuild))
//    			{
//                    materialNumberForBuild -=effect.getChoiceMaterial().getMaterial1().getNumber();
//                }
//                if(effect.getChoiceMaterial().getMaterial2().getType().equals(materialForBuild))
//                {
//                    materialNumberForBuild -=effect.getChoiceMaterial().getMaterial2().getNumber();
//                }
//
//    		}
//    		if(materialNumberForBuild<=0) return true;
//    	}
//
//    	return false;
//    }

    private boolean removeDoneCost(HashMap<MaterialType,Integer> currentCost){
        boolean change = false;
        //nettoyage de l'HashMap
        for (Map.Entry mapentry : currentCost.entrySet()) {
            if(Integer.parseInt(mapentry.getValue().toString()) <= 0){
                currentCost.remove(mapentry.getKey());
                change = true;
            }
        }
        return change;
    }

    private EffectList cleanTrivialConflict(EffectList choiceMaterialEffect, HashMap<MaterialType,Integer> currentCost){

        EffectList effectList = new EffectList();

        for(IEffect addCMatEff : choiceMaterialEffect){
            if(currentCost.containsKey(addCMatEff.getChoiceMaterial().getMaterial1().getType()) //les deux sont nécessaires
                    && currentCost.containsKey(addCMatEff.getChoiceMaterial().getMaterial2().getType())){
                effectList.add(addCMatEff);
            }
            else if(currentCost.containsKey(addCMatEff.getChoiceMaterial().getMaterial1().getType())){//un des deux est nécessaires
                Material currentMaterial = addCMatEff.getChoiceMaterial().getMaterial1();
                currentCost.put(currentMaterial.getType(),currentCost.get(currentMaterial.getType()) - currentMaterial.getNumber());
            }
            else if(currentCost.containsKey(addCMatEff.getChoiceMaterial().getMaterial2().getType())){//un des deux est nécessaires
                Material currentMaterial = addCMatEff.getChoiceMaterial().getMaterial2();
                currentCost.put(currentMaterial.getType(),currentCost.get(currentMaterial.getType()) - currentMaterial.getNumber());
            }
        }
        return effectList;
    }

    public boolean canBuyCard(EffectList effects){
        EffectList materialEffect = effects.filterMaterialEffect();
        EffectList choiceMaterialEffect = effects.filterChoiceMaterialEffect();

        MaterialType currentType;
        Material currMaterial;

        HashMap<MaterialType,Integer> currentCost = new HashMap<MaterialType, Integer>();

        //Préparation de l'HashMap
        for(Material m : materialCost){
            currentCost.put(m.getType(),m.getNumber());
        }

        //MATERIAUX FIXE
        for(IEffect addMatEff : materialEffect){
            currentType = addMatEff.getMaterial().getType();
            if(currentCost.containsKey(currentType)){
                currentCost.put(currentType,currentCost.get(currentType) - addMatEff.getMaterial().getNumber());
            }
        }
        //On retire les couts payés
        removeDoneCost(currentCost);
        if(currentCost.size() == 0) return true;//On peut payé dès maintenant.

        //MATERIAUX A CHOIX
        do{
            choiceMaterialEffect = cleanTrivialConflict(choiceMaterialEffect,currentCost);
        }
        while (removeDoneCost(currentCost));//Un cout payé peut supprimer d'autre cas.

        while(currentCost.size() != 0 && choiceMaterialEffect.size() != 0){//Soit on a payé soit on n'a plus de choix à faire.

            //Choix arbitraire
            currMaterial = choiceMaterialEffect.get(0).getChoiceMaterial().getMaterial1();
            currentCost.put(currMaterial.getType(),currentCost.get(currMaterial.getType()) - currMaterial.getNumber());
            choiceMaterialEffect.remove(choiceMaterialEffect.get(0));//Le choix est regler manuellement.

            removeDoneCost(currentCost);//Des cout ont peut etre été payé.
            choiceMaterialEffect = cleanTrivialConflict(choiceMaterialEffect,currentCost);//Des choix sont peut etre devenu triviaux.
        }
        if(currentCost.size() == 0) return true;//On peut payer (à verifier avant l'autre)
        return false;//On ne peut pas payer
    }
}
