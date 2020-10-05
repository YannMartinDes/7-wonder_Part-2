package commun.effect;

import commun.material.ChoiceMaterial;
import commun.material.Material;

/** ChoiceMaterialEffect est une classe qui represente l'effet qui represente le choix entre materiaux */
public class ChoiceMaterialEffect implements IEffect
{
    /* Champs */
    private ChoiceMaterial choiceMaterial;

    /** Constructeur
     * @param choiceMaterial le choix des materiaux */
    public ChoiceMaterialEffect (ChoiceMaterial choiceMaterial)
    { this.choiceMaterial = choiceMaterial; }

    /* Getters */
    @Override
    public ChoiceMaterial getChoiceMaterial ()
    { return choiceMaterial; }

    /** Retourne le materiels qu'on veut */
    @Override
    public Material getMaterial (int index)
    { return choiceMaterial.getMaterials()[index]; }

    public int getMaterialLength ()
    {
        if (choiceMaterial.getMaterials() == null)
            return 0;
        return choiceMaterial.getMaterials().length;
    }

}
