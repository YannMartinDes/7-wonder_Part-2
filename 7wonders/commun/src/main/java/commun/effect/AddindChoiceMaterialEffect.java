package commun.effect;

import commun.material.ChoiceMaterial;
import commun.material.Material;

public class AddindChoiceMaterialEffect implements IEffect{
    private ChoiceMaterial choiceMaterial;

    public AddindChoiceMaterialEffect(ChoiceMaterial choiceMaterial){
        this.choiceMaterial = choiceMaterial;
    }

    @Override
    public ChoiceMaterial getChoiceMaterial() {
        return choiceMaterial;
    }
}
