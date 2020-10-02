package commun.effect;

import commun.material.ChoiceMaterial;
import commun.material.Material;

public class ChoiceMaterialEffect implements  IEffect{
    private ChoiceMaterial choiceMaterial;

    public ChoiceMaterialEffect(ChoiceMaterial choiceMaterial){
        this.choiceMaterial = choiceMaterial;
    }
    @Override
    public ChoiceMaterial getChoiceMaterial(){
        return choiceMaterial;
    }

    //retourne le materiels qu'on veut
    @Override
    public Material getMaterial(int index){
        return choiceMaterial.getMaterials()[index];
    }

    public int getMaterialLength ()
    {
        if (choiceMaterial.getMaterials() == null)
            return 0;
        return choiceMaterial.getMaterials().length;
    }

}
