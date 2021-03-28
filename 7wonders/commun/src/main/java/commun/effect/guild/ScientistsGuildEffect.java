package commun.effect.guild;

import com.fasterxml.jackson.annotation.JsonIgnore;
import commun.effect.IEffect;
import commun.effect.ScientificType;

/**
 * effet de la Guilde des Scientifique
 */
public class ScientistsGuildEffect implements IEffect {

    private ScientificType selectedScientificType;

    public ScientistsGuildEffect(){ }


    @Override
    @JsonIgnore
    public ScientistsGuildEffect getScientistsGuild()
    {
        return this;
    }

    @Override
    public ScientificType getScientificType() {
        return selectedScientificType;
    }

    public void setSelectedScientificType(ScientificType selected){
        this.selectedScientificType = selected;
    }
}
