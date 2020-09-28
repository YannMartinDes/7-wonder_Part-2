package commun.effect;

import java.util.ArrayList;
import java.util.List;

public class EffectList extends ArrayList<IEffect>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3741901371092452409L;
	
	/**
	 * Permet d'avoir une liste de Material effect
	 * @return la list
	 */
	private EffectList filterMaterialEffect() {
		EffectList filter = new EffectList();
		for(IEffect effect : this) {
			if(effect.getMaterial()!=null)
				filter.add(effect);
		}
		return filter;
		
	}
	
	/**
	 * Permet d'avoir une liste de Material effect au choix
	 * @return la list
	 */
	private EffectList filterChoiceMaterialEffect() {
		EffectList filter = new EffectList();
		for(IEffect effect : this) {
			if(effect.getChoiceMaterial()!=null)
				filter.add(effect);
		}
		return filter;
		
	}

	
	
	

}
