package commun.cost.solver;

import commun.card.Card;
import commun.card.Deck;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import commun.wonderboard.WonderBoard;

import java.util.*;

public class SolverResourceBuy {
    private List<TradeChoiceList> allPosibilityToTest;
    private List<TradeChoiceList> finishPosibility = new LinkedList<>();

    public SolverResourceBuy(TradeChoiceList defaultPosibility){
        allPosibilityToTest = new LinkedList<>();
        this.allPosibilityToTest.add(defaultPosibility);// dois avoir au moins 1 element pour avoir le cout
    }

    /**
     * permet de trouver les solution ou l'on peut avec la list d'entree donner
     * @param allProduct liste des production
     * @param stopWhenFindOneSoluce s'arrete quand on a trouver une solution (la/les solution trouver est la moins chere possible)
     * @return la liste des solution
     */
    public List<TradeChoiceList> searchAllPosibility(List<LogTraceForTrade> allProduct, boolean stopWhenFindOneSoluce){
        for(LogTraceForTrade materialWithTrace: allProduct)
        {
            if(allPosibilityToTest.size()==0) return finishPosibility;
            List<TradeChoiceList> newPosibilityList = new LinkedList<>();

            for(TradeChoiceList tradeChoiceList: allPosibilityToTest)
            {
                Map<MaterialType,Integer> cost = tradeChoiceList.getCurrentMaterialCoste();
                LinkedList<Material> materialUtils = new LinkedList<>();
                for(Material material : materialWithTrace.getMaterialChoice())
                {
                    if(cost.containsKey(material.getType())) materialUtils.add(material);
                }

                //Si il y a une ressource qui nous interresse on la traite
                if(materialUtils.size()>=1)
                {
                    //on a plusieur ressource qui nous interresse on multiplie les posibilité
                    if(materialUtils.size()>1)
                    {
                        for(int i = 1; i<materialUtils.size();i++)
                            forkPosibility(newPosibilityList,tradeChoiceList,materialUtils.get(i),materialWithTrace);
                    }// on continue la branche normale

                    updateTraceChoice(tradeChoiceList,materialUtils.get(0),materialWithTrace);
                }
            }
            //on ajoute la nouvelle liste de TradeChoice a l'ancienne
            allPosibilityToTest.addAll(newPosibilityList);

            //permet de metre les solution
            checkSoluceFind();
            if(stopWhenFindOneSoluce && finishPosibility.size()>0) return finishPosibility;
        }
        return finishPosibility;
    }

    /**
     * permet de mettre a jour une list de cho
     * @param tradeChoiceList
     * @param material
     * @param materialWithTrace
     */
    public void updateTraceChoice(TradeChoiceList tradeChoiceList,Material material,LogTraceForTrade materialWithTrace){
        tradeChoiceList.add(new TradeTrace(materialWithTrace.traceForTrade(),material.getType(),material.getNumber()));
        Map<MaterialType,Integer> cost = tradeChoiceList.getCurrentMaterialCoste();
        int newValue = cost.get(material.getType())-material.getNumber();
        if(newValue<=0) cost.remove(material.getType());
        //TODO gerer bug carte plusieur ressource (2bois ...)
        else cost.put(material.getType(),newValue);
    }

    /**
     * Permet de créer une nouvelle posibilité
     * @param newChoiseList la list des nouvelle posibilité
     * @param forkElt l'element a dupliquer
     * @param material le material que l'on ajoute au fork
     * @param materialWithTrace la trace que lui ajoute
     */
    public void forkPosibility(List<TradeChoiceList> newChoiseList,TradeChoiceList forkElt,Material material,LogTraceForTrade materialWithTrace){
        TradeChoiceList newTradeChoice = forkElt.fork();
        updateTraceChoice(newTradeChoice,material,materialWithTrace);
        newChoiseList.add(newTradeChoice);
    }


    /**
     * Permet de regarder les solution trouver
     */
    public void checkSoluceFind(){
        for(int i = 0; i< allPosibilityToTest.size(); i++){
            TradeChoiceList tradeChoiceList = allPosibilityToTest.get(i);
            if(isSoluce(tradeChoiceList)){
                finishPosibility.add(tradeChoiceList);
                allPosibilityToTest.remove(tradeChoiceList);
            }
        }
    }


    public boolean isSoluce(TradeChoiceList tradeChoiceList){
        for(MaterialType type:tradeChoiceList.getCurrentMaterialCoste().keySet()){
            if(tradeChoiceList.getCurrentMaterialCoste().get(type)>0) return false;
        }
        return true;
    }

    /**
     * permet de renvoyer la list des donner pour le solver formater
     * @param isPersonnalRessource si c'est pour la personne en elle meme
     * @param materialWithTraces la liste des materiaux recuperer
     * @return liste des donner formater
     */
    public List<LogTraceForTrade> formatDataForSolver(boolean isPersonnalRessource,LogTraceForTrade... materialWithTraces){
        List<LogTraceForTrade> outputMat = new ArrayList<>();
        for(LogTraceForTrade material : materialWithTraces){
            if((material.canBeUseForTrade()|| isPersonnalRessource)
                    && material.getMaterialChoice()!=null
                    && material.getMaterialChoice().length>0){

                outputMat.add(material);
            }
        }
        return outputMat;
    }


    public List<TradeChoiceList> getAllPosibilityToTest() {
        return allPosibilityToTest;
    }

    public void setAllPosibilityToTest(List<TradeChoiceList> allPosibilityToTest) {
        this.allPosibilityToTest = allPosibilityToTest;
    }



// a voir certaine fonction peuvent permetre d'opti (a changer les input
//    /**
//     * Modifie la tradeChoiceList par a l'aide des ressource unique
//     * @param deck le deck des batiment construit
//     * @param tradeChoiceList la liste qui trace le parsing
//     */
//    public void computeOneRessourceTradeChoice(Deck deck,TradeChoiceList tradeChoiceList){
//        Map<MaterialType,Integer> cost = tradeChoiceList.getCurrentMaterialCoste();
//        Deck deckOneRessource = filterDeckOnRessource(deck);
//        for(Card card : deckOneRessource){
//            Material materialEffet = card.getCardEffect().getMaterial();
//
//            //si le materielle n'est pas necessaire
//            if(!cost.containsKey(materialEffet.getType())) continue;
//            int valueCurrentMaterialCost = cost.get(materialEffet.getType());
//
//            //si le cout est inferieur a 0 (normalement impossible)
//            if(valueCurrentMaterialCost<=0) cost.remove(materialEffet.getType());
//
//            //si l'effet
//            if(materialEffet.getNumber()>=0){
//                //ajout de la trace du path trouver
//                tradeChoiceList.add(new TradeChoice(card.getName(),materialEffet.getType(),materialEffet.getNumber()));
//                valueCurrentMaterialCost-=materialEffet.getNumber();
//
//                updateMapNumber(cost,materialEffet.getType(),valueCurrentMaterialCost);
//            }
//        }
//    }
//
//    private void updateMapNumber(Map<MaterialType,Integer> map, MaterialType key,int newValue){
//        if(map.containsKey(key) && newValue<=0) map.remove(key);
//        else map.put(key,newValue);
//    }
//
//
//    /**
//     * Permet de soustraire
//     * @param wonderBoard la wonderBoard que l'on prend les ressource
//     * @param tradeChoiceList La liste qui trace les parsing
//     */
//    public void computeWonderBoard(WonderBoard wonderBoard,TradeChoiceList tradeChoiceList){
//        Map<MaterialType,Integer> cost = tradeChoiceList.getCurrentMaterialCoste();
//
//        if(!cost.containsKey(wonderBoard.getMaterialEffect().getMaterial().getType()))
//            return;
//
//        int valueCurrentMaterialCost = cost.get(wonderBoard.getMaterialEffect().getMaterial().getType());
//
//        if(wonderBoard.getMaterialEffect().getMaterial().getNumber()>=0) {
//            tradeChoiceList.add(new TradeChoice(wonderBoard.getWonderName(),
//                    wonderBoard.getMaterialEffect().getMaterial().getType(),
//                    wonderBoard.getMaterialEffect().getMaterial().getNumber()));
//            valueCurrentMaterialCost -= wonderBoard.getMaterialEffect().getMaterial().getNumber();
//            updateMapNumber(cost,wonderBoard.getMaterialEffect().getMaterial().getType(),valueCurrentMaterialCost);
//        }
//    }
//
//
//    /**
//     * Permet d'enlever les choix double evident
//     * @param choiceDeckMat le deck de materiaux a choix (vas etre modifier)
//     * @param tradeChoiceList la tradeChoiceList qui vas etre mis a jour
//     * @return le deck de carte que l'on a encore besoin et que l'on dois faire un choix
//     */
//    public void computeEvidentChoiceMaterial(Deck choiceDeckMat,TradeChoiceList tradeChoiceList){
//
//        Map<MaterialType,Integer> cost = tradeChoiceList.getCurrentMaterialCoste();
//        for(int i = 0; i<choiceDeckMat.size(); i++) {
//
//            Card card = choiceDeckMat.getCard(i);
//            ChoiceMaterial choiceMaterial = card.getCardEffect().getChoiceMaterial();
//            if (cost.containsKey(choiceMaterial.getMaterial1().getType()) && cost.containsKey(choiceMaterial.getMaterial2().getType()))
//                continue; //on ne traite pas maintenant
//            else {
//                //Si une unique ressource nous intere
//                Material materialEffet = null;
//                if (cost.containsKey(choiceMaterial.getMaterial1().getType())) {
//                    materialEffet = choiceMaterial.getMaterial1();
//                } else if (cost.containsKey(choiceMaterial.getMaterial1().getType())) {
//                    materialEffet = choiceMaterial.getMaterial2();
//                }
//                if (materialEffet != null && materialEffet.getNumber() > 0) {
//                    tradeChoiceList.add(new TradeChoice(card.getName(),materialEffet.getType(),materialEffet.getNumber()));
//                    updateMapNumber(cost, materialEffet.getType(), cost.get(materialEffet.getType()) - materialEffet.getNumber());
//                }
//                choiceDeckMat.removeCard(i);
//            }
//
//        }
//
//    }



}
