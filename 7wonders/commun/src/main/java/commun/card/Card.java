package commun.card;

import commun.cost.ICost;
import commun.effect.IEffect;


/**
 *  Représente une carte dans 7Wonders
 */
public class Card {

	private ICost costCard;
	private IEffect cardEffect;
	private String name;
	private CardType type;
	private int age;

	/**
	 *
	 * @param name Le nom de la carte
	 * @param type Le type de la carte
	 * @param cardEffect Effet de la carte ( gains...)
	 * @param age  Age de la carte
	 *
	 */
	
	public Card(String name, CardType type , IEffect cardEffect, Integer age, ICost costCard) {
		this.cardEffect = cardEffect;
		this.name = name;
		this.type = type;
		this.age = age;
		this.costCard=costCard;

	}

	/**
	* @return Retourne l'effet de la carte
	 */
	public IEffect getCardEffect() {
		return cardEffect;
	}

	/**
	 * @return Retourne le nom de la carte
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Retourne le type de la carte
	 */
	public CardType getType() {
		return type;
	}

	/**
	 * @return Retourne l'age de la carte
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * @return Retourne le cout de la carte
	 */
	public ICost getCostCard() {
		return costCard;
	}

	//POUR TEST
	public void setCostCard(ICost costCard) {
		this.costCard = costCard;
	}

	public void setCardEffect(IEffect cardEffect) {
		this.cardEffect = cardEffect;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(CardType type) {
		this.type = type;
	}

	public void setAge(int age) {
		this.age = age;
	}


	@Override
	public String toString() {
		return name;
	}
	
}
