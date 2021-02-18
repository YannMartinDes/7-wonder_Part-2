package servergame.clientstats;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import log.Logger;
import log.coloring.ConsoleColors;

public class StopListener implements Emitter.Listener {
    Socket socket;

    public StopListener(Socket socket){
        this.socket = socket;
    }

    @Override
    public void call(Object... objects) {
        Logger.logger.log("Travail terminé - arrêt du client.", ConsoleColors.ANSI_CYAN_BOLD);
        socket.disconnect();
        System.exit(0);//Socket ne se deconnecte pas.
    }
}
