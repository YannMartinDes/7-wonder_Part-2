package commun.effect;

/** ScientificEffect represente les effets bases sur les cartes scientifiques */
public class ScientificEffect implements IEffect
{
    /* Champs */
    private ScientificType type = null;

    /** Constructeur
     * @param scientificType le type scientifique */
    public ScientificEffect (ScientificType scientificType)
    { this.type = scientificType; }

    /** @return retourne le type de la carte scientifique */
    public ScientificType getScientificType()
    { return this.type; }

}
