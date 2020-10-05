package commun.effect;

/** VictoryPointEffect est une classe qui represente l'effet sur les points de victoires */
public class VictoryPointEffect implements IEffect
{
	/* Champs */
	private int numberOfPoint = 0;

	/** Constructeur
	 * @param score le score*/
	public VictoryPointEffect (int score)
	{ this.numberOfPoint = score; }

	/** Constructeur */
	public VictoryPointEffect () {}

	/* Getters */
	@Override
	public int getScore ()
	{ return numberOfPoint; }
}
