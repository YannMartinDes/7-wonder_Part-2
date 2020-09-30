package commun.effect;

public class VictoryPointEffect implements IEffect {
	
	private int numberOfPoint = 0;
	
	public VictoryPointEffect(int score) {
		this.numberOfPoint = score;
	}
	
	public VictoryPointEffect() {
	}
	
	@Override
	public int getScore() {
		return numberOfPoint;
	}
	
	

}
