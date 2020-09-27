package commun.player.action;

import commun.card.Card;
import commun.card.Deck;
import commun.wonderboard.WonderBoard;
import log.GameLogger;

public abstract class Action {

    protected Card playedCard;

    public Action(Card playedCard){
        this.playedCard = playedCard;
    }


    public void playAction(Deck discardingDeck, WonderBoard wonderBoard,String playerName){
        GameLogger.logSpaceBefore("Le joueur : ["+playerName+"] a joué :");
        boolean actionExecuted = playCurrentAction(discardingDeck, wonderBoard);
        if(actionExecuted){
            GameLogger.log(actionExecuteLog());
        }
        else {
            //action par defaut si le coup n'est pas jouer
            GameLogger.log(actionError());
            defaultAction(discardingDeck, wonderBoard);
        }
    }

    protected abstract boolean playCurrentAction(Deck DiscardingDeck, WonderBoard wonderBoard);

    /**
     * Action par defaut defausse de la carte
     * @param discardingDeck la defausse
     * @param wonderBoard le plateau du joueur qui fait l'action
     */
    private void defaultAction(Deck discardingDeck, WonderBoard wonderBoard){
        Action defaultAction = new DiscardAction(playedCard);
        defaultAction.playCurrentAction(discardingDeck,wonderBoard);
        defaultAction.actionExecuteLog();
    }


    /*--------------------------LOGGER-------------------------------------*/

    /**
     * Permet de dire l'action qui viens de ce produire
     * @return l'action
     */
    protected abstract String actionExecuteLog();


    /**
     * Le nom de l'action pour afficher qu'elle n'a pas etait faite
     * @return le nom de l'action
     */
    protected abstract String actionError();




}
