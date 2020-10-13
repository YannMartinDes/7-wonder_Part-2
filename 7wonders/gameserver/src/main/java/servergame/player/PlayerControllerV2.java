package servergame.player;

import client.AI.AI;
import commun.action.AbstractAction;
import commun.card.Deck;
import commun.player.Player;
import commun.request.PlayerRequestGame;
import log.ConsoleColors;
import log.GameLogger;

import java.util.List;

public class PlayerControllerV2 implements PlayerRequestGame {
    Player player;
    AI ai;
    AbstractAction action;


    public PlayerControllerV2(AI ai) {
        this.ai = ai;
    }

    public void chooseAction ()
    {
        action = null;
        while(action == null)
            this.action = ai.chooseAction(player.getCurrentDeck(), player.getWonderBoard().getCoin(), player.getWonderBoard().getAllEffects());
    }

    public void playAction(Deck discardingDeck){
        action.playAction(player.getName(),player.getCurrentDeck(),player.getWonderBoard(),discardingDeck,player.getLeftNeightbour(),player.getRightNeightbour());

        if(action.getTradePossibility().size() != 0){//Si il y a un échange à choisir.
            Integer[] AIChoice = ai.choosePurchasePossibility(action.getTradePossibility());//On demande à l'IA de faire son choix;
            //On joue l'action du trade.
            action.nextAction(player.getName(),player.getCurrentDeck(),player.getWonderBoard(),discardingDeck,player.getLeftNeightbour(),player.getRightNeightbour(),AIChoice);
        }

        //Log de l'action.
        GameLogger.getInstance().logSpaceBefore("Le joueur : ["+player.getName()+"] :", ConsoleColors.ANSI_CYAN_BOLD);
        action.logAction(player.getName(),player.getWonderBoard(),discardingDeck,player.getLeftNeightbour(),player.getRightNeightbour());
    }

    public void finishAction(Deck discardingDeck){
        action.finishAction(player.getName(),player.getWonderBoard(),discardingDeck,player.getLeftNeightbour(),player.getRightNeightbour(),action.getPlayedCard(),ai);
    }

    public AbstractAction getAction() {
        return action;
    }

    public Player getPlayer() {
        return player;
    }

    /*===========================Les donner que le joueur peut demander */
    /**
     * permet de recuperer son propre joueur
     *
     * @return le joueur
     */
    @Override
    public Player getMe() {
        //TODO faire une copie
        return player;
    }

    /**
     * Permet de voir le voisin de gauche
     *
     * @return le voisin de gauche
     */
    @Override
    public Player getLeftNeighbours() {
        return player.getLeftNeightbour();
    }

    /**
     * Permet de voir le voisin de droite
     *
     * @return le voisin de droite
     */
    @Override
    public Player getRightNeighbours() {
        return player.getRightNeightbour();
    }

    /**
     * Permet de voir tout les joueur
     * @return la list de tout les joueur
     */
    @Override
    public List<Player> getAllPlayers() {
        //TODO implementer
        return null;
    }
}
