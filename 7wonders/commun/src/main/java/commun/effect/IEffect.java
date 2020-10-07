package commun.effect;

import commun.material.ChoiceMaterial;
import commun.material.Material;

/** Interface qui represente un effet */
public interface IEffect
{
	/* Getters */

	public default Material[] getMaterials(){return null;}

	public default int getScore(){
		return 0;
	}

	public default int getNumberOfCoin ()
	{ return 0; }

	public default Material[] getNeighborMaterials()
	{ return null; }

	public default int getMilitaryEffect ()
	{ return 0; }

	public default ScientificType getScientificType ()
	{ return null; }

	public default EarnWithCard getEarnWithCardEffect ()
	{return null;}

	public default EarnWithWonder getEarnWithWonderEffect(){
		return null;
	}

	public default ScientificEffect getScientificEffect(int index){
		return null;
	}

	/**
	 * Permet de savoir si l'effets guildes de strategies est activer
	 * @return true -> activer | false -> desactiver
	 */
	public default boolean iSStrategistsGuild()
	{ return false; }


}