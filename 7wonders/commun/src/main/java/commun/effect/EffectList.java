package commun.effect;

import java.util.ArrayList;
import java.util.List;

/** EffectList represente une liste d'effets */
public class EffectList extends ArrayList<IEffect>
{
	private static final long serialVersionUID = -3741901371092452409L;

	/** Permet d'avoir une liste de Material effect
	 * @return la list */
	public EffectList filterMaterialEffect ()
	{
		EffectList filter = new EffectList();
		for(IEffect effect : this) {
			if(effect.getMaterials() != null)
				if(effect.getMaterials().length == 1)
				filter.add(effect);
		}
		return filter;
	}
	
	/** Permet d'avoir une liste de Material effect au choix
	 * @return la list */
	public EffectList filterChoiceMaterialEffect ()
	{
		EffectList filter = new EffectList();
		for(IEffect effect : this) {
			if(effect.getMaterials() != null)
				if(effect.getMaterials().length == 2) filter.add(effect);
		}
		return filter;
	}

	/** Permet d'avoir une liste de Material effect des carte commerce
	 * @return la list */
	public EffectList filterCommerceChoiceMaterialEffect ()
	{
		EffectList filter = new EffectList();
		for(IEffect effect : this) {
			if(effect.getMaterials() != null)
				if(effect.getMaterials().length > 2) filter.add(effect);
		}
		return filter;
	}

	/** Permet d'avoir une liste des effets de reduction du commerce.
	 * @return la list */
	public OneCoinNeighborEffect[] filterOneCoinNeighborEffect ()
	{
		//FILTRE DE LA LISTE
		EffectList filter = new EffectList();
		for(IEffect effect : this) {
			if(effect.getNeighborMaterials() != null)//Si c'est une carte de reduction de commerce.
				filter.add(effect);
		}
		//CONVERSION EN TABLEAU
		OneCoinNeighborEffect[] res = new OneCoinNeighborEffect[filter.size()];
		for(int i =0; i<res.length; i++){
			res[i] = ((OneCoinNeighborEffect) filter.get(i));
		}
		return res;
	}
}
