package commun;

import commun.effect.Effect;
import commun.effect.VictoryPointEffect;

public class Commun {


	public static void main(String[] args) {
		System.out.println("helloworld servergame");
		Effect effect = new VictoryPointEffect(50);
		System.out.println(effect.getScore());
	}



}
