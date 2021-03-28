package commun.effect;

import commun.material.Material;

/** OneCoinNeighborEffect
 * Cette classe va permettre à un joueur de savoir :
 * à quel voisin il peut acheter des materiaux à 1 coin
 * quels type de matériaux il peut acheter à 1 coin */
public class OneCoinNeighborEffect implements IEffect
{
    /* Champs */
    private Material[] neighborMaterials ;
    private TargetType neighbor;

    /** Constructeur
     * @param neighbor Le nombre de voisins
     * @param neighborMaterials Les materiaux */
    public OneCoinNeighborEffect(TargetType neighbor , Material... neighborMaterials)
    {
        this.neighbor = neighbor;
        this.neighborMaterials = neighborMaterials;
    }

    public OneCoinNeighborEffect(){}

    /* Getters */

    @Override
    public Material[] getNeighborMaterials()
    { return neighborMaterials; }

    public TargetType getNeighbor()
    { return neighbor; }
}
