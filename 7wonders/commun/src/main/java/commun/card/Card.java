package commun.card;

import commun.effect.Effect;

public class Card {
	
	private final Effect cardEffect;
	
	public Card(Effect cardEffect) {
		this.cardEffect = cardEffect;
	}

	public Effect getCardEffect() {
		return cardEffect;
	}
	
	
}
