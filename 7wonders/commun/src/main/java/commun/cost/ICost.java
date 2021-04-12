package commun.cost;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import commun.effect.*;
import commun.material.Material;

/** Interface qui represente le cout */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CoinCost.class),
        @JsonSubTypes.Type(value = MaterialCost.class),
})
public interface ICost
{
    /* Getters*/

    public default int getCoinCost ()
    { return 0; }

    public default Material[] getMaterialCost ()
    { return null; }

    /** Savoir si la carte peut etre acheter
     * @param effets la liste de tout les effet possedant par le joueur
     * @return true -> peut etre acheter */
    public default boolean canBuyCard (EffectList effets)
    { return true; }

    /** Savoir si la carte peut etre acheter
     * @param playerCoins la monnaie du joueur
     * @return true -> peut etre acheter */
    public default boolean canBuyCard (int playerCoins)
    { return true; }

}
