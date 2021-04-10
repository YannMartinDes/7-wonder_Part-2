package servergame;

import commun.communication.StatModule;
import commun.communication.StatObject;
import log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import servergame.clientstats.StatsServerRestTemplate;
import servergame.engine.GameEngine;
import servergame.inscription.InscriptionPlayer;
import servergame.player.PlayerManager;

import java.util.Locale;

import static commun.communication.CommunicationMessages.SERVERSTATS;

@SpringBootApplication
@Configuration
public class App
{
	public static boolean SPRING_TEST = true;

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
	private InscriptionPlayer inscriptionPlayer;

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

			Logger.logger.logSpaceAfter("Début d'une partie");
			gameInitializer.initGame();
			game.init(playerManager);
			game.startGame();

			//lancement des parti de stat (si necessaire)
			String doState = System.getenv("DO_STATE");
			if(doState!=null && doState.equalsIgnoreCase("true"))
				makeStateGames();

			inscriptionPlayer.sendStopPlayer(); //fin des joueur

			Logger.logger.getInstance().log("Fin de l'application");
			int exitCode = SpringApplication.exit(appContext);
			exit(exitCode);
		};
	}

	private void makeStateGames(){
		//on recupere le nombre de parti de stat que l'on veut faire
		String timesStr = System.getenv("NB_STATE_GAME");
		int TIMES = 100; //par defaut 100 parti
		if(timesStr!=null)
			TIMES = Integer.parseInt(timesStr);

		//recuperation des variable d'environnement (si elle existe)
		String statIp = System.getenv("STATS_IP");
		if (statIp == null) statIp = "0.0.0.0";
		String statPort = System.getenv("STATS_PORT");
		if (statPort == null) statPort = "1335";
		Logger.logger.log("Ip stats: " + statIp);

		Logger.logger.log("Statistiques pour "+TIMES+" parties");
		String statsURI = "http://" + statIp + ":" + statPort + "/" +SERVERSTATS;
		Logger.logger.log(statsURI);

		//No verbose
		Logger.logger.verbose = false;
		Logger.logger.verbose_socket = false;
		if(playerManager.getNbPlayer()>5) {TIMES = 100;}

		statsServerRestTemplate.setURI(statsURI);

		for (int i = 0; i < TIMES; i++) {
			if((i+1)%10==0){
				Logger.logger.verbose = true;
				Logger.logger.log("Debut de la partie : "+(i+1)+" sur "+ TIMES);
				Logger.logger.verbose = false;
			}
			StatModule.setInstance(new StatObject());
			gameInitializer.initGame();

			game.init(playerManager);
			game.startGame();
			statsServerRestTemplate.sendStats(game.getStatObject());
		}
		Logger.logger.verbose = true;
		statsServerRestTemplate.finishStats(TIMES);
	}


	public void setGameInitializer(GameInitializer gameInitializer) {
		this.gameInitializer = gameInitializer;
	}

}
