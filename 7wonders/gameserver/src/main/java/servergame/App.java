package servergame;

import client.AI.AI;
import client.AI.FirstAI;
import client.AI.RandomAI;
import client.AI.SecondAI;
import commun.communication.StatModule;
import commun.communication.StatObject;
import log.GameLogger;
import servergame.clientstats.SocketManager;
import servergame.engine.GameEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App
{
	public final static int DEFAULT_NB_PLAYER = 5;
	private final static GameLogger LOGGER = GameLogger.getInstance();

	public static void main(String[] args)
			throws IOException
	{

		//uniquement pour la parti afficher
		int nbPlayers=DEFAULT_NB_PLAYER;
		if(args.length==1){
			try {
				nbPlayers = Integer.parseInt(args[0]);
			}
			catch (Exception e){
				nbPlayers = DEFAULT_NB_PLAYER; //nombre de joueur par defaut si le nombre n'est pas donner en parametre
			}
		}
		if(nbPlayers>7 || nbPlayers<3) {
			LOGGER.log("Nombre de joueur incorrect automatiquement mis a 4");
			nbPlayers=4;
		}

		LOGGER.logSpaceAfter("Deroulement d'une partie");
		GameInitializer gameInitializer = new GameInitializer();
		gameInitializer.initGame(nbPlayers).startGame();

		LOGGER.log("Statistiques pour 1000 parties");
		GameLogger.verbose = false;
		GameLogger.verbose_socket = false;
		int TIMES = 1000;
		SocketManager socketManager = new SocketManager("http://127.0.0.1:1335");

		//ia generer manuellement pour les stat
		List<AI> ai = new ArrayList<>(4);
		ai.add(new RandomAI());
		ai.add(new RandomAI());
		ai.add(new SecondAI());
		ai.add(new FirstAI());

		for (int i = 0; i < TIMES; i++)
		{
			StatModule.setInstance(new StatObject());
			GameEngine game = gameInitializer.initGame(ai);
			game.startGame();
			socketManager.send(game.getStatObject());
		}
		socketManager.finish(TIMES);
		GameLogger.verbose = true;
		GameLogger.getInstance().log("Fin de l'application");
		System.exit(0);
	}
}
