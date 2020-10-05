package commun.cost.solver;

import commun.effect.EffectList;
import commun.effect.IEffect;
import commun.material.Material;
import commun.material.MaterialType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MaterialsCostSolver {
    MaterialsCostArray cost;
    EffectList listToCompute;
    LinkedList<MaterialsCostSolver> allSoluce;

    public MaterialsCostSolver(Material[] materials,EffectList listToCompute){
        this.allSoluce= new LinkedList<>();
        this.allSoluce.add(this);
        this.cost = new MaterialsCostArray(materials);
        this.listToCompute= new EffectList();
        this.listToCompute.addAll(listToCompute.filterChoiceMaterialEffect());
        this.listToCompute.addAll(listToCompute.filterMaterialEffect());
    }


    private MaterialsCostSolver(){
    }

    /**
     * permet de créer une nouvelle solution a partir de l'ancienne
     * @return le clone
     */
    private MaterialsCostSolver clone(Material material){
        MaterialsCostSolver clone = new MaterialsCostSolver();
        clone.cost = cost.clone();
        clone.listToCompute = new EffectList();
        clone.listToCompute.addAll(listToCompute);
        clone.cost.sub(material.getType(),material.getNumber());
        clone.allSoluce = allSoluce;
        return clone;
    }



    /**
     * Retire les choix non ambigue et renvoie la liste des element non traiter
     */
    private EffectList removeNotAmbiguousChoice(){
        EffectList choiceMaterialEffectCopy = new EffectList();
        choiceMaterialEffectCopy.addAll(listToCompute);

        boolean asChange;
        do {
            asChange = false;
            for (int i = 0; i < choiceMaterialEffectCopy.size(); i++) {
                IEffect currentEffect = choiceMaterialEffectCopy.get(i);
                int interestingMaterialsNb = 0;
                Material  interestingMaterials = null;
                for(Material currentMat : currentEffect.getMaterials()){
                    if(cost.contains(currentMat.getType())){
                        interestingMaterialsNb++;
                        if(interestingMaterials != null) break;
                        interestingMaterials = currentMat;
                    }
                }
                //materiaux non interessant
                if(interestingMaterialsNb==0) choiceMaterialEffectCopy.remove(i);
                    //pas de choix a faire
                else if(interestingMaterialsNb==1){
                    choiceMaterialEffectCopy.remove(i);
                    cost.sub(interestingMaterials.getType(),interestingMaterials.getNumber());
                    asChange = true;
                }
            }
        }while (asChange);

        return choiceMaterialEffectCopy;

    }

    /**
     * Permet de chercher les solution ambigue
     */
    private void computeAmbiguousSoluce(){
        if(listToCompute == null || listToCompute.size()==0) return;
        IEffect effect = listToCompute.get(0);
        listToCompute.remove(0);
        boolean firstMatch = true;
        Material materialForCurrentSoluce = null;
        for(int i = 0; i<effect.getMaterials().length; i++){
            if(cost.contains(effect.getMaterials()[i].getType())) {
                if(firstMatch){
                    materialForCurrentSoluce = effect.getMaterials()[i];
                    firstMatch=false;
                }else {
                    allSoluce.add(clone(effect.getMaterials()[i]));
                }
            }
        }
        if(materialForCurrentSoluce!= null) cost.sub(materialForCurrentSoluce.getType(),materialForCurrentSoluce.getNumber());

    }

    /**
     * fonction recursive qui calcule toutes les solution possible
     */
    private void computeSoluce(){
        for(int i = 0; i<allSoluce.size();i++){

            MaterialsCostSolver soluce = allSoluce.get(i);
            do{
                soluce.listToCompute = soluce.removeNotAmbiguousChoice();
                if(!itsDone()) soluce.computeAmbiguousSoluce();
            }while(!soluce.itsDone());
            if(soluce.cost.itsDone()) return; //la solution est valide pas besoin d'en chercher d'autre
        }
    }

    /**
     * fonction recursive qui prend tout les cout finaux trouver
     * @return la liste des cout finaux
     */
    public List<MaterialsCostArray> allSoluceFind(){
        List<MaterialsCostArray> costAllSoluce = new LinkedList<>();
        for(MaterialsCostSolver soluce : allSoluce){
            if(!costAllSoluce.contains(soluce)) costAllSoluce.add(soluce.cost);
        }
        return costAllSoluce;
    }




    public boolean canBuyCard() {
        computeSoluce();
        return soluceFind();

    }

    private boolean soluceFind(){
        for(MaterialsCostArray cost :allSoluceFind()){
            if(cost.itsDone()) return true;
        }
        return false;
    }


    private boolean itsDone(){
        return listToCompute.size()==0 || cost.itsDone();
    }




}
