package servergame.player.rest;


import commun.action.AbstractAction;
import commun.card.Deck;
import commun.communication.CommunicationMessages;
import commun.player.Player;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
public class GameServerRestController {

    private List<Player> players;

    @PostMapping(value = CommunicationMessages.BOARD + "/{playerID}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> getMeBackend(@PathVariable Integer playerID){
        if (players != null)
            return new ResponseEntity<Player>(HttpStatus.NO_CONTENT);
        if(playerID>=0 && playerID < players.size())
            return new ResponseEntity<Player>(players.get(playerID),HttpStatus.ACCEPTED);

        return new ResponseEntity<Player>(HttpStatus.NOT_ACCEPTABLE);
    }

    public List<Player> getPlayers() {
        return players;
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
