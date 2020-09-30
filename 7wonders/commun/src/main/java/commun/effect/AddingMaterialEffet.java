package commun.effect;

import commun.material.Material;

public class AddingMaterialEffet implements IEffect {
    private Material material;

    public AddingMaterialEffet(Material material){
        this.material = material;
    }

    @Override
    public Material getMaterial() {
        return material;
    }
}
