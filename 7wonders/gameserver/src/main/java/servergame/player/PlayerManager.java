package servergame.player;

import servergame.player.Player;
import servergame.player.PlayerController;

import java.util.List;

public interface PlayerManager {

    public List<Player> getAllPlayers();

    public int getNbPlayer();
}
