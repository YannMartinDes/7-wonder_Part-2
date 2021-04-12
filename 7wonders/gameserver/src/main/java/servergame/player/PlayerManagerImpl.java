package servergame.player;

import commun.card.Deck;
import commun.player.Player;
import commun.wonderboard.WonderBoard;
import log.Logger;
import log.coloring.ConsoleColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import servergame.card.CardManager;
import servergame.player.rest.PlayerBoardController;
import servergame.wonderboard.WonderBoardFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
@Scope("singleton")
public class PlayerManagerImpl implements PlayerManager, PlayerView {

    @Autowired
    PlayerBoardController gameServerRestController;

    List<PlayerController> playerControllers;

    public PlayerManagerImpl(List<PlayerController> playerControllers) {
        this.playerControllers = playerControllers;
    }

    public PlayerManagerImpl() {
        this.playerControllers = new ArrayList<PlayerController>();
    }

    public void init (List<PlayerController> playerControllers)
    {
        this.playerControllers = playerControllers;
        gameServerRestController.setPlayers(this.getAllPlayers());
    }

    @Override
    public List<PlayerController> getPlayerControllers() {
        return playerControllers;
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

    @Override
    public void chooseAction() {
        for(PlayerController playerController: playerControllers) playerController.chooseAction();
    }

    @Override
    public void playAction(Deck discard) {
        for(PlayerController playerController : playerControllers){//On demande a chaque joueur sont action
            playerController.playAction(discard);
        }
    }


    @Override
    public void finishAction(Deck discard) {
        for(PlayerController playerController : playerControllers){//On applique les effets de leur action.
            playerController.finishAction(discard);
        }
    }


    @Override
    public void informations() {
        Logger.logger.logSpaceBefore("--- Information ---", ConsoleColors.ANSI_BLUE_BOLD_BRIGHT);
        for(Player player : this.getAllPlayers()) {
            Logger.logger.logSpaceBefore("-- Information du joueur "+player.getName()+" ("+player.getWonderBoard().getWonderName()+" FACE "+player.getWonderBoard().getFace()+") :",ConsoleColors.ANSI_BLUE);
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
            this.getAllPlayers().get(i).setRightNeightbour(this.getAllPlayers().get((i+1)%this.getNbPlayer()).getWonderBoard());
            //voisin de gauche.
            if(i == 0){//Cas particulier
                this.getAllPlayers().get(0).setLeftNeightbour(this.getAllPlayers().get(this.getNbPlayer()-1).getWonderBoard());
            }
            else{
                this.getAllPlayers().get(i).setLeftNeightbour(this.getAllPlayers().get(i-1).getWonderBoard());
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

    @Override
    public void initPlayerView(){
        for (PlayerController playerController : playerControllers){
            playerController.setPlayerView(this);
        }
    }

    /*====================== Vue des joueur ======================*/
    private int getIndex(Player player){
        for(int i =0; i<playerControllers.size(); i++){
            if(playerControllers.get(i).getPlayer().equals(player)){
                return i;
            }
        }
        return -1; //non trouver normalement impossible
    }

    /**
     * Permet de recuperer le voisin de gauche du joueur
     *
     * @param player le joueur que l'on veut recuperer les voisin de gauche
     * @return le voisin de gauche
     */
    @Override
    public Player getLeftNeighbours(Player player) {
        int index = getIndex(player);
        if(index==0) return playerControllers.get(playerControllers.size()-1).getPlayer();
        return playerControllers.get(index-1).getPlayer();
    }

    /**
     * Permet de recuperer le voisin de droite du joueur
     *
     * @param player le joueur que l'on veut recuperer les voisin de droite
     * @return le voisin de droite
     */
    @Override
    public Player getRightNeighbours(Player player) {
        int index = getIndex(player);
        if(index==playerControllers.size()-1) return playerControllers.get(0).getPlayer();
        return playerControllers.get(index+1).getPlayer();
    }


}
