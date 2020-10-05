package commun.material;

/**
 * Cette classe va permettre au joueur de choisir les materiaux
 * qu'il veut acheter chez son voisin ou ses si leur wonder board les produissent
 */
public class NeighbourMaterials
{
    /* Champs */
    private Material[] neighborMaterials;

    /** Constructeur
     * @param materials Les materiaux */
    public NeighbourMaterials (Material... materials)
    { this.neighborMaterials = materials; }

    /* Getters */
    public Material[] getNeighborMaterials ()
    { return neighborMaterials; }
}
