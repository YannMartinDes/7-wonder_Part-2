package commun.effect;

import commun.material.ChoiceMaterial;
import commun.material.Material;

public class ChoiceScientificEffect implements IEffect {

    private ScientificEffect[] scientificEffects;


    public ChoiceScientificEffect(ScientificEffect... scientificEffect){
        this.scientificEffects = scientificEffect;
    }

    @Override
    public ScientificEffect[] getChoiceScientificEffect(){
        return this.scientificEffects;
    }

    @Override
    public ScientificEffect getScientificEffect(int index){
        return scientificEffects[index];
    }

    public int getMaterialLength ()
    {
        return scientificEffects.length;
    }

}
