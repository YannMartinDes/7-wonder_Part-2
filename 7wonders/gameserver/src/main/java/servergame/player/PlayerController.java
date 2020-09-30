package servergame.player;

import client.AI.AI;
import commun.action.ActionType;
import commun.card.Card;
import commun.card.Deck;
import commun.action.Action;
import commun.material.Material;
import commun.wonderboard.WonderBoard;
import log.GameLogger;

/**
 * permet de verifier les entrer de l'ia
 * @author Yohann
 *
 */
public class PlayerController {
	
	private AI ai;
	private Action action;
	
	
	public PlayerController(AI ai) {
		this.ai = ai;
	}
    /**
     * Choisi une carte au hasard dans un deck
     * @param deck
     * @return la carte choisie au hasard.
     */
    public void chooseAction (Deck deck)
	{
		Action value;

    	value = ai.chooseAction(deck);
    	//TODO ia crée une action
    	this.action = value;
    }
    
	public Action getAction() {
		return action;
	}

	public void playAction(String playerName, Deck currentDeck, Deck discardingDeck, WonderBoard wonderBoard){
		Card playedCard = currentDeck.getCard(action.getIndexOfCard());
		GameLogger.log("Le joueur : ["+playerName+"] :");

		if(action.getActionType() == ActionType.DISCARD){
			discardAction(discardingDeck,wonderBoard,playedCard);
		}
		else if(action.getActionType() == ActionType.BUILD){
			//null -> cartes gratuites
			if(playedCard.getCostCard() == null){
				wonderBoard.addCardToBuilding(playedCard);
				GameLogger.log("A construit la carte "+playedCard.getName());
			}
			else{
				//Carte coutant des pièces.
				int cost = playedCard.getCostCard().getCoinCost();
				if(cost > 0){
					if(wonderBoard.getCoin() >= cost){//Si il a assez pour l'acheter.
						GameLogger.log("Paye "+cost+" pièces");
						wonderBoard.addCardToBuilding(playedCard);
						GameLogger.log("Et a construit la carte "+playedCard.getName());
						wonderBoard.removeCoin(cost);//On lui retire son argent.
					}
					else{//Il ne peut pas payer.
						GameLogger.log("Ne peut pas payer la carte.");
						discardAction(discardingDeck,wonderBoard,playedCard);
					}
				}

				Material[] materialCost = playedCard.getCostCard().getMaterialCost();
				if(materialCost != null){
					//ai.chooseBuildingPossibility();
					if(playedCard.getCostCard().canBuyCard(wonderBoard.getAllEffects())){//Peut l'acheter.
						wonderBoard.addCardToBuilding(playedCard);
						GameLogger.log("A construit la carte "+playedCard.getName());
					}
					else {//Ne peux pas l'acheter.
						GameLogger.log("Ne peut pas construire la carte.");
						discardAction(discardingDeck,wonderBoard,playedCard);
					}
				}
			}
		}
		currentDeck.removeCard(action.getIndexOfCard());
	}

	private void discardAction(Deck discardingDeck, WonderBoard wonderBoard, Card playedCard){
		discardingDeck.addCard(playedCard);
		wonderBoard.addCoin(3);
		GameLogger.log("A defaussée la carte : "+playedCard.getName()+" et a gagné 3 pièces.");
	}

}
