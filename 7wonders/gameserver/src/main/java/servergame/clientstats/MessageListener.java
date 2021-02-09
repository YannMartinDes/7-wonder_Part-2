package servergame.clientstats;

import io.socket.emitter.Emitter;
import log.ConsoleColors;
import log.GameLogger;

public class MessageListener implements Emitter.Listener {
    @Override
    public void call(Object... objects) {
        if(objects != null ){

            GameLogger.getInstance().log("MESSAGE(S) RECU(S) :", ConsoleColors.ANSI_CYAN_BOLD);
            for(int i =0; i<objects.length; i++){
                GameLogger.getInstance().log(objects[i].toString());
            }
        }
        else{
            GameLogger.getInstance().log("ERREUR : PAS DE MESSAGE", ConsoleColors.ANSI_RED);
        }
    }
}
