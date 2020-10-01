package servergame.player;

import client.AI.AI;
import commun.action.ActionType;
import commun.action.FinalAction;
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
	private FinalAction finalAction;
	
	public PlayerController(AI ai) {
		this.ai = ai;
		this.finalAction = new FinalAction();
	}
    /**
     * Choisi une carte au hasard dans un deck
     * @param deck
     * @return la carte choisie au hasard.
     */
    public void chooseAction (Deck deck)
	{
		//TODO CHECK RETOUR CORRECT
		this.action = ai.chooseAction(deck);
    }
    
	public Action getAction() {
		return action;
	}

	public void playAction(String playerName, Deck currentDeck, Deck discardingDeck, WonderBoard wonderBoard){
		Card playedCard = currentDeck.getCard(action.getIndexOfCard());

		if(action.getActionType() == ActionType.DISCARD){
			finalAction.setCoinEarned(3);
			finalAction.setDiscardCard(playedCard);
		}
		else if(action.getActionType() == ActionType.BUILD){
			//null -> cartes gratuites
			if(playedCard.getCostCard() == null){
				finalAction.setBuildCard(playedCard);
			}
			else{
				//Carte coutant des pièces.
				int cost = playedCard.getCostCard().getCoinCost();
				if(cost > 0){
					if(wonderBoard.getCoin() >= cost){//Si il a assez pour l'acheter.
						finalAction.setBuildCard(playedCard);
						finalAction.setCoinToPay(cost);
					}
					else{//Il ne peut pas payer.
						finalAction.setCoinEarned(3);
						finalAction.setDiscardCard(playedCard);
					}
				}

				Material[] materialCost = playedCard.getCostCard().getMaterialCost();
				if(materialCost != null){
					if(playedCard.getCostCard().canBuyCard(wonderBoard.getAllEffects())){//Peut l'acheter.
						finalAction.setBuildCard(playedCard);
					}
					else {//Ne peux pas l'acheter.
						//On check les voisins :
						//RECUP ARRAYLIST DE POSSIBILITE
						//DEMANDE IA INDEX

						finalAction.setCoinEarned(3);
						finalAction.setDiscardCard(playedCard);
					}
				}
			}
		}
		currentDeck.removeCard(action.getIndexOfCard());
	}

	public void finishAction(String playerName, WonderBoard wonderBoard, Deck discardingDeck){

		GameLogger.logSpaceBefore("Le joueur : ["+playerName+"] :");

    	if(finalAction.getCantBuildCard() != null){
			GameLogger.log("Ne peut pas construire/payer la carte "+finalAction.getCantBuildCard().getName());
		}
		if(finalAction.getCoinToPay() != 0){//Paiement d'une carte
			wonderBoard.removeCoin(finalAction.getCoinToPay());
			GameLogger.log("A payé "+finalAction.getCoinToPay()+" pièces");
		}
		if(finalAction.getBuildCard() != null){//Construction de carte.
			wonderBoard.addCardToBuilding(finalAction.getBuildCard());
			GameLogger.log("A construit la carte "+finalAction.getBuildCard().getName());

			if(finalAction.getBuildCard().getCardEffect().getNumberOfCoin()!=0){
				GameLogger.log("Gagne "+finalAction.getBuildCard().getCardEffect().getNumberOfCoin()+" pieces pour avoir construit ce batiment");
			}
		}
		if(finalAction.getDiscardCard() != null){//Defausse de carte
			discardingDeck.addCard(finalAction.getDiscardCard());
			GameLogger.log("A defaussée la carte : "+finalAction.getDiscardCard().getName());
		}
		if(finalAction.getCoinEarned() != 0){//Gain de pièces.
			wonderBoard.addCoin(finalAction.getCoinEarned());
			GameLogger.log("A gagné "+finalAction.getCoinEarned()+" pièces");
		}

		//RESET DE L'ACTION FINALE
		finalAction.reset();
	}

}
