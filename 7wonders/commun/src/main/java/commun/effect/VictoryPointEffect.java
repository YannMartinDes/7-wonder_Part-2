package commun.effect;

public class VictoryPointEffect implements Effect {
	
	int score=0;
	
	public VictoryPointEffect(int score) {
		this.score = score;
	}
	
	public VictoryPointEffect() {
	}
	
	@Override
	public int getScore() {
		return score;
	}
	
	

}
