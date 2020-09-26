package commun;

import commun.effect.IEffect;
import commun.effect.VictoryPointEffect;

public class Commun {


	public static void main(String[] args) {
		System.out.println("helloworld servergame");
		IEffect effect = new VictoryPointEffect(50);
		System.out.println(effect.getScore());
	}



}
