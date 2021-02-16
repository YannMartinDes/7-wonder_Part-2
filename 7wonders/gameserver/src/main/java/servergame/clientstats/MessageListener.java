package servergame.clientstats;

import io.socket.emitter.Emitter;
import log.Logger;
import log.coloring.ConsoleColors;

public class MessageListener implements Emitter.Listener {
    @Override
    public void call(Object... objects) {
        if(objects != null ){

            Logger.logger.log("MESSAGE(S) RECU(S) :", ConsoleColors.ANSI_CYAN_BOLD);
            for(int i =0; i<objects.length; i++){
                Logger.logger.log(objects[i].toString());
            }
        }
        else{
            Logger.logger.log("ERREUR : PAS DE MESSAGE", ConsoleColors.ANSI_RED);
        }
    }
}
