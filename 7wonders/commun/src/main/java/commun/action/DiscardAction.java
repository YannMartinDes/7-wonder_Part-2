package commun.action;

import commun.card.Deck;
import commun.wonderboard.WonderBoard;
import log.Logger;

public class DiscardAction extends AbstractAction {

    public DiscardAction(int indexOfCard){
        super(indexOfCard);
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
        Logger.logger.log(playerName+" a défaussé la carte : "+playedCard.getName()
        + " et a gagné 3 pièces.");
    }

}
