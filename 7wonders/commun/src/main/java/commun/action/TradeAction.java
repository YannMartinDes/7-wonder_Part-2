package commun.action;

import commun.card.Card;
import commun.card.Deck;
import commun.wonderboard.WonderBoard;
import log.GameLogger;

public class TradeAction extends AbstractAction {

    Integer[] costToPaid;
    boolean succeed = false;

    public TradeAction(Integer[] costToPaid, int indexOfCard){
        super(indexOfCard);
        this.costToPaid = costToPaid;
    }

    @Override
    public void playAction(String playerName, Deck currentDeck, WonderBoard wonderBoard, Deck discardingDeck, WonderBoard leftNeigthbour, WonderBoard rightNeigthbour) {
        if (costToPaid == null) {//L'IA ne veut pas acheter chez ses voisins.
            action = new DiscardAction(indexOfCard);
            action.playAction(playerName,currentDeck,wonderBoard,discardingDeck,leftNeigthbour,rightNeigthbour);
        }
        //Si le joueur n'a pas assez d'argent pour acheter les ressources.
        else if ((costToPaid[0] + costToPaid[1]) > wonderBoard.getCoin()) {
            action = new DiscardAction(indexOfCard);
            action.playAction(playerName,currentDeck,wonderBoard,discardingDeck,leftNeigthbour,rightNeigthbour);
        }
        else {//Si il a assez pour l'acheter.
            succeed = true;
            wonderBoard.removeCoin(costToPaid[0] + costToPaid[1]);//On paye le prix
            leftNeigthbour.addCoin(costToPaid[0]);//On ajoute aux voisins.
            rightNeigthbour.addCoin(costToPaid[1]);
        }
    }

    public boolean hasBuy(){
        return succeed;
    }

    @Override
    public void logAction(String playerName, WonderBoard wonderBoard, Deck discardingDeck, WonderBoard leftNeigthbour, WonderBoard rightNeigthbour) {
        if(succeed){//il a pu acheter.
            GameLogger.getInstance().log(playerName+ " a payé "+(costToPaid[0] + costToPaid[1])+" pièces");

            if(costToPaid[0] != 0){
                GameLogger.getInstance().log(playerName+ " a payé "+costToPaid[0]+" pièces à son voisin de gauche.");
            }
            if(costToPaid[1] != 0){
                GameLogger.getInstance().log(playerName+ " a payé "+costToPaid[1]+" pièces à son voisin de droite.");
            }
        }
        else{//Il n'a pas pu ou pas voulu.
            action.logAction(playerName, wonderBoard, discardingDeck, leftNeigthbour, rightNeigthbour);//Discard.
        }
    }
}
