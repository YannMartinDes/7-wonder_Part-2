package commun.action;

import commun.card.Deck;
import commun.wonderboard.WonderBoard;

public class DiscardAction extends Action {



    public DiscardAction(int indexCard) {
		super(indexCard);
		// TODO Auto-generated constructor stub
	}



	@Override
    protected boolean playCurrentAction(Deck discardingDeck, WonderBoard wonderBoard) {
        discardingDeck.addCard(playedCard);
        wonderBoard.addCoin(3);
        return true;
    }

    @Override
    protected String actionExecuteLog() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("A defaussée la carte : ");
        stringBuilder.append(playedCard.getName());
        stringBuilder.append(" et a gagné 3 pièces");
        return stringBuilder.toString();
    }


    @Override
    protected String actionError() {
        return "666"; // impossible
    }


}
