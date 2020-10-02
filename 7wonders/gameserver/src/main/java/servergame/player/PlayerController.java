package servergame.player;

import client.AI.AI;
import commun.action.ActionType;
import commun.action.FinalAction;
import commun.card.Card;
import commun.card.Deck;
import commun.action.Action;
import commun.effect.EarnWithCard;
import commun.material.Material;
import commun.wonderboard.WonderBoard;
import log.ConsoleColors;
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
	private Card playedCard;
	private boolean playedCardIsBuild;

	
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
		action = null;
		while(action == null)
			this.action = ai.chooseAction(deck);
    }
    
	public Action getAction() {
		return action;
	}

	/**
	 * L'action se joue et les effets sont mémoriser
	 * @param currentDeck
	 * @param wonderBoard
	 */
	public void playAction(Deck currentDeck, WonderBoard wonderBoard){
		playedCard = currentDeck.getCard(action.getIndexOfCard());
		playedCardIsBuild = false;//On ne sais pas si elle va être construite.

		if(action.getActionType() == ActionType.DISCARD){
			finalAction.setCoinEarned(3);
			finalAction.setDiscardCard(true);
		}
		else if(action.getActionType() == ActionType.BUILD){

			if(wonderBoard.isAlreadyInBuilding(playedCard.getName())){//On ne peut pas construire deux cartes du meme nom.
				finalAction.setCantBuildCard(true);
				finalAction.setCoinEarned(3);
				finalAction.setDiscardCard(true);
			}
			else{
				//null -> cartes gratuites
				if(playedCard.getCostCard() == null){
					finalAction.setBuildCard(true);
				}
				else{
					//Carte coutant des pièces.
					int cost = playedCard.getCostCard().getCoinCost();
					if(cost > 0){
						if(wonderBoard.getCoin() >= cost){//Si il a assez pour l'acheter.
							finalAction.setBuildCard(true);
							finalAction.setCoinToPay(cost);
						}
						else{//Il ne peut pas payer.
							finalAction.setCantBuildCard(true);
							finalAction.setCoinEarned(3);
							finalAction.setDiscardCard(true);
						}
					}

					Material[] materialCost = playedCard.getCostCard().getMaterialCost();
					if(materialCost != null){
						if(playedCard.getCostCard().canBuyCard(wonderBoard.getAllEffects())){//Peut l'acheter.
							finalAction.setBuildCard(true);
						}
						else {//Ne peux pas l'acheter.
							//On check les voisins :
							//RECUP ARRAYLIST DE POSSIBILITE
							//DEMANDE IA INDEX

							finalAction.setCantBuildCard(true);
							finalAction.setCoinEarned(3);
							finalAction.setDiscardCard(true);
						}
					}
				}
			}
		}
		currentDeck.removeCard(action.getIndexOfCard());
	}

	/**
	 * finit les actions en cours en appliquant leurs effets
	 * @param playerName
	 * @param wonderBoard
	 * @param discardingDeck
	 */
	public void finishAction(String playerName, WonderBoard wonderBoard, Deck discardingDeck){

		GameLogger.logSpaceBefore("Le joueur : ["+playerName+"] :",ConsoleColors.ANSI_CYAN_BOLD);

    	if(finalAction.cantBuildCard()){
			GameLogger.log("Ne peut pas construire/payer la carte "+playedCard.getName(), ConsoleColors.ANSI_RED);
			playedCardIsBuild = false;
		}
		if(finalAction.getCoinToPay() != 0){//Paiement d'une carte
			wonderBoard.removeCoin(finalAction.getCoinToPay());
			GameLogger.log("A payé "+finalAction.getCoinToPay()+" pièces");
		}
		if(finalAction.isBuildCard()){//Construction de carte.
			wonderBoard.addCardToBuilding(playedCard);
			GameLogger.log("A construit la carte "+playedCard.getName());
			playedCardIsBuild = true;//La carte est construite.
		}
		if(finalAction.isDiscardCard()){//Defausse de carte
			discardingDeck.addCard(playedCard);
			GameLogger.log("A defaussée la carte : "+playedCard.getName());
			playedCardIsBuild = false;
		}
		if(finalAction.getCoinEarned() != 0){//Gain de pièces.
			wonderBoard.addCoin(finalAction.getCoinEarned());
			GameLogger.log("A gagné "+finalAction.getCoinEarned()+" pièces");
		}

		//RESET DE L'ACTION FINALE
		finalAction.reset();
	}

	/**
	 * Ici se produit les effets qui ont lieu après les execution des actions.
	 * @param playerName
	 * @param wonderBoard
	 * @param leftNeigthbour
	 * @param rightNeigthbour
	 */
	public void afterAction(String playerName, WonderBoard wonderBoard, WonderBoard leftNeigthbour, WonderBoard rightNeigthbour){

		if(playedCardIsBuild){//SEULEMENT SI LA CARTE EST CONSTRUITE.

			//CARTE COMME TAVERNE
			if(playedCard.getCardEffect().getNumberOfCoin()!=0){
				GameLogger.logSpaceBefore(playerName+" gagne "+playedCard.getCardEffect().getNumberOfCoin()+" pieces grâce au batiment "+playedCard.getName(), ConsoleColors.ANSI_GREEN);
				wonderBoard.addCoin(playedCard.getCardEffect().getNumberOfCoin());//Ajout des pièces.
			}

			//CARTE COMME CASERNE
			if(playedCard.getCardEffect().getMilitaryEffect() != 0){
				GameLogger.logSpaceBefore(playerName+ " gagne "+playedCard.getCardEffect().getMilitaryEffect() + " de puissance millitaire grâce au batiment "+playedCard.getName(), ConsoleColors.ANSI_GREEN);
				wonderBoard.addMilitaryPower(playedCard.getCardEffect().getMilitaryEffect());
			}

			//CARTE COMME VIGNOBLE
			if(playedCard.getCardEffect().getEarnWithCardEffect() != null){
				EarnWithCard earnWithCard = playedCard.getCardEffect().getEarnWithCardEffect();

				int coinEarned = 0;

				//Pieces gagné chez soit x le facteur de pièces.
				coinEarned += wonderBoard.countCard(earnWithCard.getCardType()) * earnWithCard.getCoinEarn();

				if(earnWithCard.isAffectNeightbour()){
					//On gagne des pièces pour les cartes construites chez nos deux voisins.
					coinEarned += leftNeigthbour.countCard(earnWithCard.getCardType()) * earnWithCard.getCoinEarn();
					coinEarned += rightNeigthbour.countCard(earnWithCard.getCardType()) * earnWithCard.getCoinEarn();
				}

				//TODO PHARE ELLE SE COMPTE ELLE MEME RETIRER 1;

				wonderBoard.addCoin(coinEarned);
				GameLogger.logSpaceBefore(playerName+ " gagne "+coinEarned+" pièces grâce au batiment "+playedCard.getName(), ConsoleColors.ANSI_GREEN);
			}
		}
	}
}
