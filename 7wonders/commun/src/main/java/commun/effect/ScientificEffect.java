package commun.effect;


public class ScientificEffect implements IEffect
{

    private ScientificType type = null;

    public ScientificEffect(ScientificType scientificType)
    {
        this.type = scientificType;
    }

    /**
     *
     * @return retourne le type de la carte scientifique
     */
    public ScientificType getScientificType()
    {
        return this.type;
    }

}
