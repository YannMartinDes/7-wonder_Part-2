package commun.effect;

import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.NeighbourMaterials;

public interface IEffect {

	public default int getScore() {
		return 0;
	}
	public default int getMaterialLength () { return 0; };
	public default Material getMaterial(int index){ return null;}
	public default ChoiceMaterial getChoiceMaterial(){return null;}
	public default int getNumberOfCoin() {
		return 0;
	}
	public default NeighbourMaterials getNeighborMaterials()
	{
		return null;
	}
	public default int getMilitaryEffect() {
		return 0;
	}
	public default ScientificType getScientificType() {
		return null;
	}
	public default EarnWithCard getEarnWithCardEffect(){return null;}


}
