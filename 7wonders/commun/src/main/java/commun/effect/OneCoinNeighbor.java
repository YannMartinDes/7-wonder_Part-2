package commun.effect;

import commun.material.Material;
import commun.material.NeighborMaterials;

/**
 * cette classe va permettre à un joueur de savoir :
 *      à quel voisin il peut acheter des materiaux à 1 coin
 *      quels type de matériaux il peut acheter à 1 coin
 */
public class OneCoinNeighbor implements IEffect{

    private NeighborMaterials neighborMaterials;
    private int neighbor;
    // 0 = voisin de droite // 1 = voisin de gauche // 2 = les deux voisins

    public OneCoinNeighbor(int neighbor , NeighborMaterials neighborMaterials){
        this.neighbor = neighbor;
        this.neighborMaterials = neighborMaterials;
    }

    @Override
    public NeighborMaterials getNeighborMaterials()
    {
        return neighborMaterials;
    }
    public int getNeighbor()
    {
        return neighbor;
    }

}
