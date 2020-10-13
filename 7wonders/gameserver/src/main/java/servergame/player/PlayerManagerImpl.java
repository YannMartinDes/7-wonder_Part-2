package servergame.player;

import commun.card.Deck;
import commun.player.Player;
import commun.wonderboard.WonderBoard;
import log.ConsoleColors;
import log.GameLogger;
import servergame.card.CardManager;
import servergame.wonderboard.WonderBoardFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlayerManagerImpl implements PlayerManager {

    List<PlayerControllerV2> playerControllers;
    private final GameLogger _LOGGER = GameLogger.getInstance();

    public PlayerManagerImpl(List<PlayerControllerV2> playerControllers) {
        this.playerControllers = playerControllers;
    }


    @Override
    public List<PlayerControllerV2> getPlayerControllers() {
        return playerControllers;
    }

    @Override
    public List<Player> getAllPlayers() {
        List<Player> players = new LinkedList<>();
        for(PlayerControllerV2 playerController: playerControllers) players.add(playerController.getPlayer());
        return players;
    }

    @Override
    public int getNbPlayer() {
        return playerControllers.size();
    }

    @Override
    public void playAction(Deck discard) {
        for(PlayerControllerV2 playerController : playerControllers){//On demande a chaque joueur sont action
            playerController.playAction(discard);
        }
    }


    @Override
    public void finishAction(Deck discard) {
        for(PlayerControllerV2 playerController : playerControllers){//On applique les effets de leur action.
            playerController.finishAction(discard);
        }
    }


    @Override
    public void informations() {
        _LOGGER.logSpaceBefore("--- Information ---", ConsoleColors.ANSI_BLUE_BOLD_BRIGHT);
        for(Player player : this.getAllPlayers()) {
            _LOGGER.logSpaceBefore("-- Information du joueur "+player.getName()+" ("+player.getWonderBoard().getWonderName()+") :",ConsoleColors.ANSI_BLUE);
            player.information();
        }
    }

    @Override
    public void assignPlayersDeck(CardManager cardManager) {
        for(int i =0; i<this.getNbPlayer(); i++) {
            this.getAllPlayers().get(i).setCurrentDeck(cardManager.getHand(i));
        }
    }

    /**
     * Assigne les voisins (leur wonderboard) aux joueurs.
     */
    @Override
    public void assignNeightbours(){
        for(int i = 0; i<this.getNbPlayer(); i++){
            //voisin de droite
            this.getAllPlayers().get(i).setRightNeightbour(this.getAllPlayers().get((i+1)%this.getNbPlayer()));
            //voisin de gauche.
            if(i == 0){//Cas particulier
                this.getAllPlayers().get(0).setLeftNeightbour(this.getAllPlayers().get(this.getNbPlayer()-1));
            }
            else{
                this.getAllPlayers().get(i).setLeftNeightbour(this.getAllPlayers().get(i-1));
            }
        }
    }

    /**
     * On assigne une merveille au joueur pour la partie
     */
    @Override
    public void assignPlayersWonderBoard(){
        ArrayList<WonderBoard> wonders = new WonderBoardFactory().chooseWonderBoard(getNbPlayer());

        for(int i =0; i<getNbPlayer(); i++) {
            this.getAllPlayers().get(i).setWonderBoard(wonders.get(i));
        }
    }
}
