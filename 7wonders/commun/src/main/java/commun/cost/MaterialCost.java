package commun.cost;

import commun.effect.EffectList;
import commun.effect.IEffect;
import commun.material.Material;
import commun.material.MaterialType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** MaterialCost represente le cout en materiaux */
public class MaterialCost implements ICost
{
    /* Champs */
    private Material[] materialCost;

    /** Constructeur
     * @param materialCost les couts en materiaux */
    public MaterialCost (Material... materialCost)
    { this.materialCost = materialCost; }

    /* Getter */

    @Override
    public Material[] getMaterialCost ()
    { return materialCost; }

    /** removeDoneCost permet de retirer les clefs de currentCost qui sont a 0
     * @param currentCost represente la table de hachage entre le type de materiau et son occurence */
    private boolean removeDoneCost(HashMap<MaterialType,Integer> currentCost)
    {
        boolean change = false;
        ArrayList<MaterialType> toDelete = new ArrayList<MaterialType>();
        //nettoyage de l'HashMap
        for (MaterialType type : currentCost.keySet())
        {
            if (currentCost.get(type) <= 0)
            {
                toDelete.add(type);
                change = true;
            }
        }
        for (MaterialType type : toDelete)
        {
            currentCost.remove(type);
        }
        return change;
    }

    private EffectList cleanTrivialConflict(EffectList choiceMaterialEffect, HashMap<MaterialType,Integer> currentCost)
    {
        EffectList effectList = new EffectList();

        for(IEffect addCMatEff : choiceMaterialEffect){
            if(currentCost.containsKey(addCMatEff.getMaterial(0).getType()) //les deux sont nécessaires
                    && currentCost.containsKey(addCMatEff.getMaterial(1).getType())){
                effectList.add(addCMatEff);
            }
            else if(currentCost.containsKey(addCMatEff.getMaterial(0).getType())){//un des deux est nécessaires
                Material currentMaterial = addCMatEff.getMaterial(0);
                currentCost.put(currentMaterial.getType(),currentCost.get(currentMaterial.getType()) - currentMaterial.getNumber());
            }
            else if(currentCost.containsKey(addCMatEff.getMaterial(1).getType())){//un des deux est nécessaires
                Material currentMaterial = addCMatEff.getMaterial(1);
                currentCost.put(currentMaterial.getType(),currentCost.get(currentMaterial.getType()) - currentMaterial.getNumber());
            }
        }
        return effectList;
    }

    /**
     * canBuyCard permet de savoir a partir d'une liste d'effet si
     * oui (true) ou non (false) on peut jouer une carte
     * @param effects la liste des effets
     * @return true si on peut jouer la carte, false sinon
     */
    public boolean canBuyCard (EffectList effects)
    {
        EffectList materialEffect = effects.filterMaterialEffect();
        EffectList choiceMaterialEffect = effects.filterChoiceMaterialEffect();

        MaterialType currentType;
        Material currMaterial;

        HashMap<MaterialType,Integer> currentCost = new HashMap<MaterialType, Integer>();

        //Préparation de l'HashMap
        MaterialType [] types = new MaterialType[] {MaterialType.WOOD, MaterialType.CLAY, MaterialType.STONE, MaterialType.ORES, MaterialType.GLASS, MaterialType.PAPYRUS, MaterialType.FABRIC};
        for (int i = 0; i < types.length; i++)
        {
            currentCost.put(types[i], 0);
        }
        // = ce qu'on a
        for (int i = 0; i < this.materialCost.length; i++)
        {
            Material m = this.materialCost[i];
            currentCost.replace(m.getType(), currentCost.get(m.getType()) + m.getNumber());
        }
        removeDoneCost(currentCost);

        //MATERIAUX FIXE
        for(IEffect addMatEff : materialEffect){
            if (addMatEff.getMaterialLength() > 1) continue;
            currentType = addMatEff.getMaterial(0).getType();
            if(currentCost.containsKey(currentType)){
                currentCost.put(currentType,currentCost.get(currentType) - addMatEff.getMaterial(0).getNumber());
            }
        }
        //On retire les couts payés
        this.removeDoneCost(currentCost);
        if(currentCost.size() == 0) return true;//On peut payé dès maintenant.

        //MATERIAUX A CHOIX

        do{
            choiceMaterialEffect = cleanTrivialConflict(choiceMaterialEffect,currentCost);
        }
        while (removeDoneCost(currentCost));//Un cout payé peut supprimer d'autre cas.

        while(currentCost.size() != 0 && choiceMaterialEffect.size() != 0){//Soit on a payé soit on n'a plus de choix à faire.

            //Choix arbitraire
            currMaterial = choiceMaterialEffect.get(0).getMaterial(1);
            currentCost.put(currMaterial.getType(),currentCost.get(currMaterial.getType()) - currMaterial.getNumber());
            choiceMaterialEffect.remove(choiceMaterialEffect.get(0));//Le choix est regler manuellement.

            removeDoneCost(currentCost);//Des cout ont peut etre été payé.
            choiceMaterialEffect = cleanTrivialConflict(choiceMaterialEffect,currentCost);//Des choix sont peut etre devenu triviaux.
        }
        if(currentCost.size() == 0) return true;//On peut payer (à verifier avant l'autre)
        return false;//On ne peut pas payer
    }
}
