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
import org.springframework.context.annotation.Configuration;
import servergame.clientstats.StatsServerRestTemplate;
import org.springframework.context.annotation.Bean;
import servergame.engine.GameEngine;
import servergame.player.PlayerManager;
import servergame.player.PlayerManagerImpl;

import java.util.ArrayList;
import java.util.List;

import static commun.communication.CommunicationMessages.SERVERSTATS;

@SpringBootApplication
@Configuration
public class App
{
	public static boolean SPRING_TEST = true;
	public final static int DEFAULT_NB_PLAYER = 5;

	public static void exit (int exit_code)
	{
		Logger.exit();
		// System.exit(exit_code);
	}

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private StatsServerRestTemplate statsServerRestTemplate;

	@Autowired
	private GameInitializer gameInitializer;

	@Autowired
	private PlayerManager playerManager;

	@Autowired
	private GameEngine game;

	public App(){}

	public static void main(String args[])
	{
		// Totally not a spring test
		App.SPRING_TEST = false;
		SpringApplication app = new SpringApplication(App.class);
		app.run(args);
	}

	@Bean
	public CommandLineRunner run() {
		return args -> {
			// Spring Test Case
			if (App.SPRING_TEST)
			{ return; }

			//recuperation des variable d'environnement (si elle existe)
			String statIp = System.getenv("STATS_IP");
			if (statIp == null) statIp = "0.0.0.0";
			String statPort = System.getenv("STATS_PORT");
			if (statPort == null) statPort = "1335";

			Logger.logger.log("Ip stats: " + statIp);

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
				Logger.logger.log("Nombre de joueur incorrect automatiquement mis a 4");
				nbPlayers = 4;
			}

			Logger.logger.logSpaceAfter("Deroulement d'une partie");
			gameInitializer.initGame(nbPlayers);
			game.init(playerManager);
			game.startGame();

			Logger.logger.log("Statistiques pour 1000 parties");
			String statsURI = "http://" + statIp + ":" + statPort + "/" +SERVERSTATS;
			Logger.logger.log(statsURI);

			//No verbose
			Logger.logger.verbose = false;
			Logger.logger.verbose_socket = false;
			int TIMES = 1000;

			statsServerRestTemplate.setURI(statsURI);

			//ia generer manuellement pour les stat
			List<AI> ai = new ArrayList<>(4);
			ai.add(new RandomAI());
			ai.add(new RandomAI());
			ai.add(new SecondAI());
			ai.add(new FirstAI());

			for (int i = 0; i < TIMES; i++) {
				StatModule.setInstance(new StatObject());
				gameInitializer.initGame(ai);
				game.init(playerManager);
				game.startGame();
				statsServerRestTemplate.sendStats(game.getStatObject());
			}

			Logger.logger.verbose = true;
			statsServerRestTemplate.finishStats(TIMES);
			Logger.logger.getInstance().log("Fin de l'application");

			int exitCode = SpringApplication.exit(appContext);
			exit(exitCode);
		};
	}


	public void setGameInitializer(GameInitializer gameInitializer) {
		this.gameInitializer = gameInitializer;
	}

}
