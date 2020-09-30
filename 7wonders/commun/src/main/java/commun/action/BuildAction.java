package commun.action;

import commun.card.Deck;
import commun.cost.MaterialCost;
import commun.material.Material;
import commun.wonderboard.WonderBoard;
import log.GameLogger;

public class BuildAction extends Action {



    public BuildAction(int indexCard) {
		super(indexCard);
	}

	@Override
    protected boolean playCurrentAction(Deck DiscardingDeck, WonderBoard wonderBoard) {
		//null -> cartes gratuites
        if(playedCard.getCostCard() == null){
            wonderBoard.addCardToBuilding(playedCard);
            return true;
        }
        //Carte coutant des pièces.
        int cost = playedCard.getCostCard().getCoinCost();
        if(cost > 0){
            if(wonderBoard.getCoin() >= cost){//Si il a assez pour l'acheter.
                GameLogger.log("Il paye "+cost+" pièces");
                wonderBoard.addCardToBuilding(playedCard);
                wonderBoard.removeCoin(cost);//On lui retire son argent.
                return true;
            }
        }
        Material[] materialCost = playedCard.getCostCard().getMaterialCost();
        if(materialCost != null){
            //DEMANDER A L"IA CE QUELLE UTILISE
            //SI MAUVAIS RETOUR DISCARD
            //SI BON RETOUR MAIS PAS SUFFISANT -> VOISINS (DEMANDER A L'IA QUI ET QUOI)
        }

        return false;
    }
	

    @Override
    protected String actionExecuteLog() {
        return "Il construit le batiment "+playedCard.getName();
    }

    
    @Override
    protected String actionError() {
        return "n'a pas pu construire le batiment "+playedCard.getName()+" car il n'a pas assez de ressource";
    }


}
