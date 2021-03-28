package commun.effect;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import commun.action.BuildAction;
import commun.action.BuildStepAction;
import commun.action.DiscardAction;
import commun.action.TradeAction;
import commun.effect.guild.ScientistsGuildEffect;
import commun.effect.guild.StrategistsGuild;
import commun.material.Material;

/** Interface qui represente un effet */
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type"
)
@JsonSubTypes({
		@JsonSubTypes.Type(value = ChoiceMaterialEffect.class),
		@JsonSubTypes.Type(value = CoinEffect.class),
		@JsonSubTypes.Type(value = EarnWithCardEffect.class),
		@JsonSubTypes.Type(value = EarnWithWonderEffect.class),
		@JsonSubTypes.Type(value = MilitaryEffect.class),
		@JsonSubTypes.Type(value = OneCoinNeighborEffect.class),
		@JsonSubTypes.Type(value = ScientificEffect.class),
		@JsonSubTypes.Type(value = VictoryPointEffect.class),
		@JsonSubTypes.Type(value = ScientistsGuildEffect.class),
		@JsonSubTypes.Type(value = StrategistsGuild.class)
})
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
	 * Permet de savoir si l'effets Guilde des Stratèges est activer
	 * @return true -> activer | false -> desactiver
	 */
	public default boolean iSStrategistsGuild()
	{ return false; }

	/**
	 * Permet de savoir si l'effets est Guilde des Scientifique
	 * @return la guilde des scientifique (si c'est cette effet)
	 */
	public default ScientistsGuildEffect getScientistsGuild()
	{ return null; }




}