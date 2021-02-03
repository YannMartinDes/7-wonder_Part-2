package serverstat;

import serverstat.server.Server;

import java.net.UnknownHostException;

public class App {

	public static void main(String[] args) throws UnknownHostException {

		Server server = new Server();
		server.startServer();

	}
}
