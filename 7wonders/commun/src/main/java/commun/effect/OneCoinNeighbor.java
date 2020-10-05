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

<<<<<<< Updated upstream
    public OneCoinNeighbor(TargetType neighbor , NeighbourMaterials neighborMaterials){
=======
    /** Constructeur
     * @param neighbor Le nombre de voisins
     * @param neighborMaterials Les materiaux */
    public OneCoinNeighbor (int neighbor, NeighbourMaterials neighborMaterials)
    {
>>>>>>> Stashed changes
        this.neighbor = neighbor;
        this.neighborMaterials = neighborMaterials;
    }

    /* Getters */

    @Override
    public NeighbourMaterials getNeighborMaterials()
<<<<<<< Updated upstream
    {
        return neighborMaterials;
    }
    public TargetType getNeighbor()
    {
        return neighbor;
    }
=======
    { return neighborMaterials; }

    public int getNeighbor()
    { return neighbor; }
>>>>>>> Stashed changes

}
