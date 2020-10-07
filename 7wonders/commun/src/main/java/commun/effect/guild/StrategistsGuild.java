package commun.effect.guild;

import commun.effect.IEffect;

/**
 * Represente l'effet guildes des strategies
 */
public class StrategistsGuild implements IEffect {
    @Override
    /**
     * Permet de savoir si l'effets guildes de strategies est activer
     * @return true -> activer | false -> desactiver
     */
    public boolean iSStrategistsGuild()
    { return true; }
}
