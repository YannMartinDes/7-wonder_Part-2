package commun.effect;

public abstract interface Effect {
	

	public default int getScore() {
		return 0;
	}
	

}
