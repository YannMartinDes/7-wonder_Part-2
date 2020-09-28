package commun.action;

import commun.card.Deck;
import commun.wonderboard.WonderBoard;

public class BuildAction extends Action {



    public BuildAction(int indexCard) {
		super(indexCard);
	}

	@Override
    protected boolean playCurrentAction(Deck DiscardingDeck, WonderBoard wonderBoard) {
		
		//TODO check les ressoucre
		
		wonderBoard.addCardToBuilding(playedCard);
        return true;
    }
	

    @Override
    protected String actionExecuteLog() {
        return "A construit le batiment "+playedCard.getName();
    }

    
    @Override
    protected String actionError() {
        return "n'a pas pue construire le batiment "+playedCard.getName()+" car il n'a pas asser de ressource";
    }


}
