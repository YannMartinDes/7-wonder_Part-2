package commun.cost.solver;

import commun.material.MaterialType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TradeChoiceList extends LinkedList<TradeTrace> {

    private Map<MaterialType,Integer> currentMaterialCost;

    public TradeChoiceList(Map<MaterialType,Integer> currentMaterialCoste) {
        this.currentMaterialCost = currentMaterialCoste;
    }


    public Map<MaterialType,Integer> getCurrentMaterialCoste() {
        return currentMaterialCost;
    }

    public void setCurrentMaterialCost(Map<MaterialType,Integer> currentMaterialCoste) {
        this.currentMaterialCost = currentMaterialCoste;
    }

    /**
     * Permet de faire une copie de la liste pour avoir une solution en plus a essayer
     * @return la copie
     */
    public TradeChoiceList fork(){
        Map<MaterialType,Integer> copyCost = new HashMap<>();
        TradeChoiceList copy = new TradeChoiceList(copyCost);
        for(MaterialType materialType : currentMaterialCost.keySet()){
            copyCost.put(materialType,currentMaterialCost.get(materialType));
        }
        for(TradeTrace trace : this){
            copy.add(trace);
        }
        return copy;

    }

//    public static List<TradeChoiceList> copyListOfTradeList(List<TradeChoiceList> list){
//        List<TradeChoiceList> copy = new LinkedList<>();
//        for(TradeChoiceList tradeChoiceList : list){
//            copy.add(tradeChoiceList.fork());
//        }
//        return copy;
//    }

}
