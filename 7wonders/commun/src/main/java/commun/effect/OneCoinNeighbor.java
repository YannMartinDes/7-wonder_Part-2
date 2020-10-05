package commun.effect;

import commun.material.NeighbourMaterials;

/** OneCoinNeighbor
 * Cette classe va permettre à un joueur de savoir :
 * à quel voisin il peut acheter des materiaux à 1 coin
 * quels type de matériaux il peut acheter à 1 coin */
public class OneCoinNeighbor implements IEffect
{
    /* Champs */
    private NeighbourMaterials neighborMaterials ;
    private TargetType neighbor;

    /** Constructeur
     * @param neighbor Le nombre de voisins
     * @param neighborMaterials Les materiaux */
    public OneCoinNeighbor(TargetType neighbor , NeighbourMaterials neighborMaterials)
    {
        this.neighbor = neighbor;
        this.neighborMaterials = neighborMaterials;
    }

    /* Getters */

    @Override
    public NeighbourMaterials getNeighborMaterials()
    { return neighborMaterials; }

    public TargetType getNeighbor()
    { return neighbor; }
}
