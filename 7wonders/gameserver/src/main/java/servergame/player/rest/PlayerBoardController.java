package servergame.player.rest;



import commun.communication.CommunicationMessages;
import commun.player.Player;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Component
@RestController
public class PlayerBoardController {

    private List<Player> players;

    public PlayerBoardController(){}

    @GetMapping(value = "/"+CommunicationMessages.BOARD + "/{playerID}")
    public ResponseEntity<Player> loadBoard(@PathVariable String playerID){
        if (players == null)
            return new ResponseEntity<Player>(HttpStatus.NO_CONTENT);
        int playerIDInt = Integer.parseInt(playerID);
        if(playerIDInt>=0 && playerIDInt < players.size())
        {

            Player p = players.get(playerIDInt);
            Player player = new Player(p.getName());
            player.setCurrentDeck(null);
            player.setWonderBoard(p.getWonderBoard());
            player.setFinalScore(p.getFinalScore());
            player.setLeftNeightbour(p.getLeftNeightbour());
            player.setRightNeightbour(p.getRightNeightbour());

            return new ResponseEntity<Player>(player,HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<Player>(HttpStatus.NOT_ACCEPTABLE);
    }

    public List<Player> getPlayers() {
        return players;
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}