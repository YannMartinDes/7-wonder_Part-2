package serverstat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import serverstat.server.Server;

import java.net.UnknownHostException;
import java.util.Collections;


@SpringBootApplication
public class App {

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(App.class);
		app.setDefaultProperties(Collections
				.singletonMap("server.port", "1334"));
		app.run();
//		Server server = new Server();
//		server.startServer();

	}
}
