package servergame.player;

import java.util.LinkedList;
import java.util.List;

public class PlayerManagerImpl implements PlayerManager{
    List<PlayerController> playerControllers;

    public PlayerManagerImpl(List<PlayerController> playerControllers) {
        this.playerControllers = playerControllers;
    }

    @Override
    public List<Player> getAllPlayers() {
        List<Player> players = new LinkedList<>();
        for(PlayerController playerController: playerControllers) players.add(playerController.getPlayer());
        return players;
    }

    @Override
    public int getNbPlayer() {
        return playerControllers.size();
    }
}
