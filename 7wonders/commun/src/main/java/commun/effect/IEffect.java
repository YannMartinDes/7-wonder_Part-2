package commun.effect;

import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.NeighborMaterials;

/** Interface qui represente un effet */
public interface IEffect
{
	/* Getters */

	public default Material[] getMaterials(){return null;}



	public default int getNumberOfCoin ()
	{ return 0; }

	public default NeighborMaterials getNeighborMaterials()
	{ return null; }

	public default int getMilitaryEffect ()
	{ return 0; }

	public default ScientificType getScientificType ()
	{ return null; }

	public default EarnWithCard getEarnWithCardEffect ()
	{return null;}
  
	public default ScientificEffect[] getChoiceScientificEffect(){
		return null;
	}

}