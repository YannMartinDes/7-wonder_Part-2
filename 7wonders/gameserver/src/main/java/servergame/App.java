package servergame;

import java.io.IOException;
import java.util.ArrayList;

import client.AI.AI;
import client.AI.FirstAI;
import client.AI.RandomAI;
import commun.communication.StatModule;
import commun.communication.StatObject;
import log.GameLogger;
import servergame.clientstats.SocketManager;
import servergame.engine.GameEngine;
import commun.player.Player;
import servergame.player.PlayerController;
import servergame.player.PlayerManagerImpl;

public class App
{
	public static void main(String[] args)
			throws IOException
	{
		StatObject statObject = StatModule.getInstance();
		Player p1 = new Player("Sardoche");
		Player p2 = new Player("Paf le chien");
		Player p3 = new Player("AngryNerd");
		Player p4 = new Player("Alan Turing");
		/*Player p5 = new Player("Hamilton");
		Player p6 = new Player("Chuck Norris");
		Player p7 = new Player("Furious Kid");*/
		AI ai = new RandomAI();
		PlayerController playerController1 = new PlayerController(p1,ai,statObject);
		ai.setRequestGame(playerController1);

		ai = new RandomAI();
		PlayerController playerController2 = new PlayerController(p2,ai,statObject);
		ai.setRequestGame(playerController2);

		ai = new RandomAI();
		PlayerController playerController3 = new PlayerController(p3,ai,statObject);
		ai.setRequestGame(playerController3);

		ai = new FirstAI();
		PlayerController playerController4 = new PlayerController(p4,ai,statObject);
		ai.setRequestGame(playerController4);
		
		ArrayList<PlayerController> allPlayers = new ArrayList<PlayerController>();
		allPlayers.add(playerController1);
		allPlayers.add(playerController2);
		allPlayers.add(playerController3);
		allPlayers.add(playerController4);

		GameLogger.getInstance().logSpaceAfter("Deroulement d'une partie");
		GameEngine game = new GameEngine(new PlayerManagerImpl(allPlayers),statObject);
		game.startGame();
		GameLogger.getInstance().log("Statistiques pour 1000 parties");
		GameLogger.verbose = false;
		GameLogger.verbose_socket = false;
		int TIMES = 1000;
		SocketManager socketManager = new SocketManager("http://127.0.0.1:1335");
		for (int i = 0; i < TIMES; i++)
		{
			statObject = new StatObject();
			game = new GameEngine(new PlayerManagerImpl(allPlayers),statObject);
			game.startGame();
			socketManager.send(game.getStatObject());
		}
		socketManager.finish(TIMES);
		GameLogger.verbose = false;
		GameLogger.getInstance().log("Fin de l'application");
	}
}
