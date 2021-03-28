package commun.effect;

import commun.material.ChoiceMaterial;
import commun.material.Material;

/** ChoiceMaterialEffect est une classe qui represente l'effet qui represente le choix entre materiaux */
public class ChoiceMaterialEffect implements IEffect
{
    /* Champs */
    private ChoiceMaterial choiceMaterial;


    public ChoiceMaterialEffect(ChoiceMaterial choiceMaterial){
        this.choiceMaterial = choiceMaterial;
    }
    public ChoiceMaterialEffect(){}

    /** Retourne le materiels qu'on veut */
    @Override
    public Material[] getMaterials(){ return choiceMaterial.getMaterials(); }

    public int getMaterialLength ()
    {
        if (choiceMaterial.getMaterials() == null)
            return 0;
        return choiceMaterial.getMaterials().length;
    }

}
