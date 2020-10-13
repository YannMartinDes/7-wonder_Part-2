package commun.action;

import commun.card.Card;
import commun.card.Deck;
import commun.wonderboard.WonderBoard;

import java.util.ArrayList;
import java.util.List;

/** Action est une classe qui permet a l'IA de faire une action */
public abstract class AbstractAction {//TODO ajouter champ commun.

    public abstract void playAction(String playerName, Deck currentDeck, WonderBoard wonderBoard, Deck discardingDeck,
                                   WonderBoard leftNeigthbour, WonderBoard rightNeigthbour);

    public abstract void logAction(String playerName, WonderBoard wonderBoard, Deck discardingDeck,
                                  WonderBoard leftNeigthbour, WonderBoard rightNeigthbour);

    public void finishAction(String playerName, WonderBoard wonderBoard, Deck discardingDeck,
                                     WonderBoard leftNeigthbour, WonderBoard rightNeigthbour, Card card, AI ai){}

    public List<Integer[]> getTradePossibility(){return new ArrayList<>();}

    public void nextAction(String playerName, Deck currentDeck, WonderBoard wonderBoard, Deck discardingDeck,
                           WonderBoard leftNeigthbour, WonderBoard rightNeigthbour,Integer[] AIChoice){

    }

    public abstract Card getPlayedCard();

}
