package commun.action;

import commun.card.Card;
import commun.card.Deck;
import commun.wonderboard.WonderBoard;
import log.GameLogger;

public class DiscardAction extends AbstractAction {

    private int indexOfCard;//Index de la carte ciblée
    private Card playedCard;//Carte ciblée

    public DiscardAction(int indexOfCard){
        this.indexOfCard = indexOfCard;
    }

    @Override
    public void playAction(String playerName, Deck currentDeck, WonderBoard wonderBoard, Deck discardingDeck,
                           WonderBoard leftNeigthbour, WonderBoard rightNeigthbour) {

        playedCard = currentDeck.getCard(indexOfCard);
        wonderBoard.addCoin(3);//Gagne 3 pièces
        discardingDeck.addCard(playedCard);//Ajout à la défausse.

        currentDeck.removeCard(indexOfCard);//On retire la carte de la main.
    }

    @Override
    public void logAction(String playerName, WonderBoard wonderBoard, Deck discardingDeck,
                          WonderBoard leftNeigthbour, WonderBoard rightNeigthbour) {
        GameLogger.getInstance().log(playerName+" a défaussé la carte : "+playedCard.getName()
        + " et a gagné 3 pièces.");
    }

    @Override
    public Card getPlayedCard() {
        return playedCard;
    }

}
