package commun.effect;

import commun.material.Material;
import commun.material.NeighbourMaterials;

/**
 * cette classe va permettre à un joueur de savoir :
 *      à quel voisin il peut acheter des materiaux à 1 coin
 *      quels type de matériaux il peut acheter à 1 coin
 */
public class OneCoinNeighbor implements IEffect{

    private NeighbourMaterials neighborMaterials ;
    private TargetType neighbor;

    public OneCoinNeighbor(TargetType neighbor , NeighbourMaterials neighborMaterials){
        this.neighbor = neighbor;
        this.neighborMaterials = neighborMaterials;
    }

    @Override
    public NeighbourMaterials getNeighborMaterials()
    {
        return neighborMaterials;
    }
    public TargetType getNeighbor()
    {
        return neighbor;
    }

}
