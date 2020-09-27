package commun.player.action;

import commun.card.Card;
import commun.card.Deck;
import commun.wonderboard.WonderBoard;

public class BuildAction extends Action {

    public BuildAction(Card playedCard) {
        super(playedCard);
    }

    @Override
    protected boolean playCurrentAction(Deck DiscardingDeck, WonderBoard wonderBoard) {
        return false;
    }

    @Override
    protected String actionExecuteLog() {
        return "A construit le batiment "+playedCard.getName();
    }

    @Override
    protected String actionError() {
        return null;
    }


}
