package commun.cost.solver;

import commun.material.Material;
import commun.material.MaterialType;

import java.util.*;

/**
 * representation du prix
 */
public class MaterialsCostArray{
    private int[] cost;
    private int totalCost = 0;

    public MaterialsCostArray(){
        cost = new int[MaterialType.values().length];
    }

    private MaterialsCostArray(int[] cost,int totalCost){
        this.cost = cost;
        this.totalCost = totalCost;
    }

    public MaterialsCostArray(Material[] costMaterials){
        this();
        intWithMaterialList(costMaterials);
    }

    private void intWithMaterialList(Material[] costMaterials){
        for(Material material: costMaterials){
            this.cost[material.getType().getIndex()] += material.getNumber();
        }
        computeTotalCost();
    }

    private void computeTotalCost(){
        totalCost = 0;
        for(int value : cost) totalCost+=value;
    }

    /**
     * Permet de faire une copie de l'objet
     * @return la copie de l'objet
     */
    public MaterialsCostArray clone(){
        int[] copy =  new int[cost.length];
        for(int i = 0; i<cost.length; i++){
            copy[i] = cost[i];
        }
        return new MaterialsCostArray(copy,totalCost);
    }

    /**
     * permet de mettre un cout pour un type de materiel
     * @param materialType le material type
     * @param value la valeur
     */
    public void put(MaterialType materialType,int value){
        if(value<0) value = 0;
        totalCost -= cost[materialType.getIndex()] - value; // on met a jour la valeur totale
        cost[materialType.getIndex()] = value;
        computeTotalCost();
    }

    /**
     * permet de soustraire le cout pour un type de materiel
     * @param materialType le material type
     * @param value la valeur a soustraire
     */
    public void sub(MaterialType materialType,int value){
        if(cost[materialType.getIndex()]-value<0) value = cost[materialType.getIndex()];
        totalCost -= value; // on met a jour la valeur totale
        cost[materialType.getIndex()] -= value;
    }


    /**
     * permet de recuperer le cout d'un type de materiel
     * @param materialType la materiel
     * @return le cout restant
     */
    public int get(MaterialType materialType){
        return cost[materialType.getIndex()];
    }

    /**
     * permet de regarder si le prix a besoin d'un type materiel
     * @param materialType le type de materiel
     * @return le type de materiel
     */
    public boolean contains(MaterialType materialType){
        return cost[materialType.getIndex()]>0;
    }



    //pour le contains de l'arrayList
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaterialsCostArray that = (MaterialsCostArray) o;
        return Arrays.equals(cost, that.cost);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(cost);
    }

    public boolean itsDone(){
        return totalCost<=0;
    }



    /* ==================================== SOLVER FOR BUILD CARD ==================================== */

    /**
     * permet de calculer toutes les combinaison
     * @return la list des combinaison
     */
    public ArrayList<MaterialsCostArray> combinaison() {
        int lowerBound = 0; // le cout minimum
        ArrayList<MaterialsCostArray> result = new ArrayList<>();
        Deque<MaterialsCostArray> stack = new ArrayDeque<MaterialsCostArray>();

        stack.push(this.clone());

        while (!stack.isEmpty()) {
            MaterialsCostArray current = stack.pop();
            result.add(current);

            for (MaterialsCostArray next : expand(current, lowerBound)) {
                if (!result.contains(next)) {
                    stack.push(next);
                }
            }
        }
        return result;
    }
    /*
                   methode subNewCostArray ->
        [2,1,0,0]       <- -[2,1,0,0] -> [0,0,0,0]
        [1,1,0,0]       <- -[2,1,0,0] -> [1,0,0,0]
        [0,1,0,0]       <- -[2,1,0,0] -> [2,0,0,0]
        [2,0,0,0]       <- -[2,1,0,0] -> [0,1,0,0]
        [1,0,0,0]       <- -[2,1,0,0]
        [0,0,0,0]       <- -[2,1,0,0]

     */

    private List<MaterialsCostArray> expand(MaterialsCostArray current, int lowerBound) {
        List<MaterialsCostArray> nexts = new ArrayList<MaterialsCostArray>();

        for (int i = 0; i < current.cost.length; i++) {
            if (current.cost[i] > lowerBound) { //si le prix de la ressource n'est pas null
                MaterialsCostArray copyCurrent = current.clone();
                copyCurrent.cost[i]--;
                nexts.add(copyCurrent);
            }
        }

        return nexts;
    }

    /**
     * Permet de créer un nouveau cout a l'aide d'un soustraction
     * @param toSub le tableau de ressource a soustraire
     * @return un nouveau de tableau de cout qui result de la soustraction
     */
    public MaterialsCostArray subNewCostArray(MaterialsCostArray toSub){
        MaterialsCostArray result = this.clone();
        for(int i = 0; i<result.cost.length;i++){
            result.cost[i] -= toSub.cost[i];
            if(result.cost[i]<0) result.cost[i]=0;
        }
        result.computeTotalCost();
        return result;
    }

}
