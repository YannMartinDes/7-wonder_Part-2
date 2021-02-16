package servergame;

import client.AI.AI;
import client.AI.FirstAI;
import client.AI.RandomAI;
import client.AI.SecondAI;
import commun.communication.StatModule;
import commun.communication.StatObject;
import log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import servergame.clientstats.StatsServerRestTemplate;
import org.springframework.context.annotation.Bean;
import servergame.engine.GameEngine;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class App
{
	public final static int DEFAULT_NB_PLAYER = 5;

	public static void exit ()
	{
		System.exit(0);
	}

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private StatsServerRestTemplate statsServerRestTemplate;

	@Autowired
	private GameInitializer gameInitializer;

	public App(){}

	public static void main(String args[])
	{

		SpringApplication app = new SpringApplication(App.class);
		app.run(args);


	}

	@Bean
	public CommandLineRunner run() {
		return args -> {
			//recuperation des variable d'environnement (si elle existe)
			String statIp = System.getenv("STATS_IP");
			if (statIp == null) statIp = "0.0.0.0";
			String statPort = System.getenv("STATS_PORT");
			if (statPort == null) statPort = "1335";

			LOGGER.log("Ip stats: " + statIp);

			//uniquement pour la parti afficher
			int nbPlayers = DEFAULT_NB_PLAYER;
			if (args.length == 1) {
				try {
					nbPlayers = Integer.parseInt(args[0]);
				} catch (Exception e) {
					nbPlayers = DEFAULT_NB_PLAYER; //nombre de joueur par defaut si le nombre n'est pas donner en parametre
				}
			}
			if (nbPlayers > 7 || nbPlayers < 3) {
				LOGGER.log("Nombre de joueur incorrect automatiquement mis a 4");
				nbPlayers = 4;
			}

			LOGGER.logSpaceAfter("Deroulement d'une partie");
			gameInitializer.initGame(nbPlayers).startGame();

			LOGGER.log("Statistiques pour 1000 parties");
			String URI = "http://" + statIp + ":" + statPort + "/serverstats";
			LOGGER.log(URI);

			//No verbose
			GameLogger.verbose = false;
			GameLogger.verbose_socket = false;
			int TIMES = 1000;

			statsServerRestTemplate.setURI(URI);

			//ia generer manuellement pour les stat
			List<AI> ai = new ArrayList<>(4);
			ai.add(new RandomAI());
			ai.add(new RandomAI());
			ai.add(new SecondAI());
			ai.add(new FirstAI());

			for (int i = 0; i < TIMES; i++) {
				StatModule.setInstance(new StatObject());
				GameEngine game = gameInitializer.initGame(ai);
				game.startGame();
				statsServerRestTemplate.sendStats(game.getStatObject());
			}

			GameLogger.verbose = true;
			statsServerRestTemplate.finishStats(TIMES);
			GameLogger.getInstance().log("Fin de l'application");

			int exitCode = SpringApplication.exit(appContext);
			System.exit(exitCode);
			//		System.exit(0);
		};
	}


	public void setGameInitializer(GameInitializer gameInitializer) {
		this.gameInitializer = gameInitializer;
	}

}
