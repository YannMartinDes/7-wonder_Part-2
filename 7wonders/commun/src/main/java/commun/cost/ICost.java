package commun.cost;


import commun.effect.EffectList;
import commun.material.Material;

public interface ICost
{
    public default int getCoinCost() {
        return 0;
    }
    public default Material[] getMaterialCost() {
        return null;
    }


    /**
     * Savoir si la carte peut etre acheter
     * @param effets la liste de tout les effet possedant par le joueur
     * @return true -> peut etre acheter
     */
    public default boolean canBuyCard(EffectList effets) {	
    	return true;
    }

    public default boolean canBuyCard(int playerCoins) {
        return true;
    }

}
