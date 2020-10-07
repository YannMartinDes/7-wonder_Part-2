package commun.cost;

import commun.cost.solver.MaterialsCostArray;
import commun.cost.solver.MaterialsCostSolver;
import commun.effect.EffectList;
import commun.effect.IEffect;
import commun.effect.OneCoinNeighborEffect;
import commun.effect.TargetType;
import commun.material.Material;
import commun.material.MaterialType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    /**
     * Liste de toutes les possibilites pour les prix à payer pour les joueurs de gauche et droite.
     * @param soluceBuyNeighbours : Les possibilités d'achat de ressource chez les deux voisins
     * @param reductionEffect : les effets de reduction de prix lors du commerce.
     * @return La liste des differentes possibilité de prix à payer aux deux voisins.
     */
    public List<Integer[]> costListBuyNeightbour(List<MaterialsCostArray[]> soluceBuyNeighbours, OneCoinNeighborEffect... reductionEffect){
        List<Integer[]> res = new ArrayList<>();

        if(soluceBuyNeighbours.size() == 0) return res;//Liste vide on ne peut rien acheter.

        boolean[] leftReduction = materialReduction(TargetType.LEFT_NEIGHTBOUR,reductionEffect);
        boolean[] rightReduction = materialReduction(TargetType.RIGHT_NEIGHTBOUR,reductionEffect);

        for(MaterialsCostArray[] costArrays : soluceBuyNeighbours){
            //TOUJOURS 2 ELEMENTS
            int[] leftN = costArrays[0].getCost();
            int[] rigthN = costArrays[1].getCost();

            int leftCost = 0;
            int rightCost = 0;

            //VOISIN DE GAUCHE
            for(int i = 0; i < leftN.length;i++){//leftReduction et leftN ont la meme taille.
                if(leftReduction[i]){//Reduction pour cette ressource.
                    leftCost += 1 * leftN[i];
                }
                else{//Pas de réduction
                    leftCost += 2 * leftN[i];
                }
            }
            //VOISIN DE DROITE
            for(int i = 0; i < rigthN.length;i++){//rightReduction et rigthN ont la meme taille.
                if(rightReduction[i]){//Reduction pour cette ressource.
                    rightCost += 1 * rigthN[i];
                }
                else{//Pas de réduction
                    rightCost += 2 * rigthN[i];
                }
            }
            addPricePossibility(res,leftCost,rightCost);
        }
        return res;
    }

    /**
     * Renvoie un tableau de booleen qui dit quelle ressource ont une réduction de prix.
     * @param targetNeightbour : le voisin où la reduction s'applique
     * @param reductionEffectTab : les effets de reduction du joueur
     * @return un tableau de booleen.
     */
    private boolean[] materialReduction(TargetType targetNeightbour, OneCoinNeighborEffect[] reductionEffectTab){
        boolean[] materialReductionTab = new boolean[7];//Tous à false de base.

        for(OneCoinNeighborEffect reducEffect : reductionEffectTab){
            if(reducEffect.getNeighbor() == targetNeightbour //Si la reduction s'applique au voisin ciblé
                    || reducEffect.getNeighbor() == TargetType.BOTH_NEIGHTBOUR){

                for(Material material : reducEffect.getNeighborMaterials()){
                    materialReductionTab[material.getType().getIndex()] = true;//Reduction sur ce matériel.
                }
            }
        }
        return materialReductionTab;
    }


    /**
     * Ajoute le couple de prix à la liste si il n'y est pas déjà
     * @param pricePossibility : la liste des possibilité d'achat
     * @param leftCost le prix a payer au voisin de gauche.
     * @param rightCost le prix a payer au voisin de droite.
     */
    private void addPricePossibility(List<Integer[]> pricePossibility, int leftCost, int rightCost){
        boolean alreadyInList = false;
        for(Integer[] price : pricePossibility){//Pour chaque probabilité déjà presente.
            if(price[0] == leftCost && price[1] == rightCost)//Si deja present dans la liste.
                alreadyInList = true;
        }
        if(!alreadyInList){//Si pas déjà present on ajoute.
            pricePossibility.add(new Integer[]{leftCost,rightCost});
        }
    }
}
