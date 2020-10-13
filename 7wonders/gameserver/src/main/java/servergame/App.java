package servergame;

import java.io.IOException;
import java.util.ArrayList;

import client.AI.FirstAI;
import client.AI.RandomAI;
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
		StatObject statObject = new StatObject();
		Player p1 = new Player("Sardoche");
		Player p2 = new Player("Paf le chien");
		Player p3 = new Player("AngryNerd");
		Player p4 = new Player("Alan Turing");
		/*Player p5 = new Player("Hamilton");
		Player p6 = new Player("Chuck Norris");
		Player p7 = new Player("Furious Kid");*/
		PlayerController playerController1 = new PlayerController(p1,new RandomAI(),statObject);
		PlayerController playerController2 = new PlayerController(p2,new RandomAI(),statObject);
		PlayerController playerController3 = new PlayerController(p3,new RandomAI(),statObject);
		PlayerController playerController4 = new PlayerController(p4,new FirstAI(),statObject);
		
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
