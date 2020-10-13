package servergame.player;

import client.AI.AI;
import commun.action.AbstractAction;
import commun.card.Deck;
import log.ConsoleColors;
import log.GameLogger;

public class PlayerControllerV2 {
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
}
