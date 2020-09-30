package servergame;

import java.io.IOException;
import java.util.ArrayList;

import client.AI.RandomAI;
import log.GameLogger;
import servergame.clientstats.SocketManager;
import servergame.engine.GameEngine;
import servergame.player.Player;
import servergame.player.PlayerController;

public class App
{
	public static void main(String[] args)
			throws IOException
	{
		GameLogger.logSpaceAfter("Hello GameServer !");
		Player p1 = new Player("Sardoche");
		Player p2 = new Player("ChuckNoris");
		Player p3 = new Player("Einstein");
		Player p4 = new Player("Alan Turing");
		p1.setController(new PlayerController(new RandomAI()));
		p2.setController(new PlayerController(new RandomAI()));
		p3.setController(new PlayerController(new RandomAI()));
		p4.setController(new PlayerController(new RandomAI()));
		
		ArrayList<Player> allPlayers = new ArrayList<Player>();
		allPlayers.add(p1);
		allPlayers.add(p2);
		allPlayers.add(p3);
		allPlayers.add(p4);

		int TIMES = 100;
		SocketManager socketManager = new SocketManager("http://127.0.0.1:1335");
		for (int i = 0; i < TIMES; i++)
		{
			GameEngine game = new GameEngine(allPlayers);
			game.startGame();
			socketManager.send(game.getStatObject());
		}
		socketManager.finish(TIMES);
	}
}
