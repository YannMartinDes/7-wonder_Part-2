package servergame;

import java.util.ArrayList;

import client.AI.RandomAI;
import commun.player.Player;
import commun.player.PlayerController;
import log.GameLogger;
import servergame.engine.GameEngine;

public class App
{
	public static void main(String[] args)
	{
		GameLogger.log("Hello GameServer !");
		Player p1 = new Player("joueur a");
		Player p2 = new Player("joueur b");
		Player p3 = new Player("joueur c");
		Player p4 = new Player("joueur d");
		p1.setController(new PlayerController(new RandomAI()));
		p2.setController(new PlayerController(new RandomAI()));
		p3.setController(new PlayerController(new RandomAI()));
		p4.setController(new PlayerController(new RandomAI()));
		
		ArrayList<Player> allPlayers = new ArrayList<Player>();
		allPlayers.add(p1);
		allPlayers.add(p2);
		allPlayers.add(p3);
		allPlayers.add(p4);
		
		GameEngine game= new GameEngine(allPlayers);
		game.startGame();
		
		
		
	}
}
