package commun.material;

/**
 * Cette classe va permettre au joueur de choisir les materiaux
 * qu'il veut acheter chez son voisin ou ses si leur wonder board les produissent
 */
public class NeighborMaterials
{
    private Material[] neighborMaterials ;
    public NeighborMaterials(Material... materials)
    {
        this.neighborMaterials = materials;
    }

    public Material[] getNeighborMaterials()
    {
        return neighborMaterials;
    }
}
