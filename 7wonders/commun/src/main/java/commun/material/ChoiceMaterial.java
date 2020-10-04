package commun.material;

import commun.effect.IEffect;

/**
 * choix entre plusieurs materiels
 */
public class ChoiceMaterial implements IEffect {
    private Material [] materials;

    public ChoiceMaterial(Material... materials){
        this.materials = materials;
    }

    @Override
    public Material[] getMaterials() {
        return materials;
    }

}
