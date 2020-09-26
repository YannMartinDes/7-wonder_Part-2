package commun.effect;

import commun.material.ChoiceMaterial;
import commun.material.Material;

public interface IEffect {

	public default int getScore() {
		return 0;
	}
	public default Material getMaterial(){ return null;}
	public default ChoiceMaterial getChoiceMaterial(){return null;}
}
