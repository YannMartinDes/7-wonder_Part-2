package servergame.player;

import client.AI.AI;
import commun.action.ActionType;
import commun.action.FinalAction;
import commun.card.Card;
import commun.card.Deck;
import commun.action.Action;
import commun.communication.StatObject;
import commun.cost.MaterialCost;
import commun.cost.solver.MaterialsCostArray;
import commun.effect.*;
import commun.material.Material;
import commun.wonderboard.WonderBoard;
import commun.wonderboard.WonderStep;
import log.ConsoleColors;
import log.GameLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	private WonderStep currentStep;
	private boolean playedCardIsBuild;
	private boolean playedStepIsBuild;

	
	public PlayerController(AI ai) {
		this.ai = ai;
		this.finalAction = new FinalAction();
	}
    /**
     * Choisi une carte au hasard dans un deck
     * @param deck
     * @return la carte choisie au hasard.
     */
    public void chooseAction (Deck deck, int playerCoins, EffectList playerEffects, List<WonderStep> wonderSteps)
	{
		action = null;
		while(action == null)
			this.action = ai.chooseAction(deck, playerCoins, playerEffects);
    }
    
	public Action getAction() {
		return action;
	}

	/**
	 * Joue l'action choisie par l'IA
	 * @param currentDeck : la main du joueur
	 * @param wonderBoard : le plateau de merveille du joueur
 	 * @param statObject : l'objet de statistiques
	 * @param playerName : le nom du joueur
	 * @param leftNeigthbour : le plateau de merveille du joueur de gauche
	 * @param rightNeigthbour: le plateau de merveille du joueur de droite
	 */
	public void playAction(Deck currentDeck, WonderBoard wonderBoard, StatObject statObject,
						   String playerName, WonderBoard leftNeigthbour, WonderBoard rightNeigthbour){
		playedCard = currentDeck.getCard(action.getIndexOfCard());
		currentStep = wonderBoard.getCurrentStep();
		playedCardIsBuild = false;//On ne sais pas si elle va être construite.
		playedStepIsBuild=false;//On ne sais pas si l'étape va être construit.


		if(action.getActionType() == ActionType.DISCARD){
			finalAction.setCoinEarned(3);
			finalAction.setDiscardCard(true);
		}
		else if(action.getActionType() == ActionType.BUILD_STAGE_WONDER){
			if(currentStep == null){//Il ne reste aucune étapes à construire.
				finalAction.setCoinEarned(3);
				finalAction.setDiscardCard(true);
			}
			else {
				Material[] materialCost = currentStep.getCost().getMaterialCost() ; // le cout est toujours en materiel
				if(materialCost != null) {
					//savoir si j'ai assez de ressource pour construire cette etape de la merveille
					if (currentStep.getCost().canBuyCard(wonderBoard.getAllEffects())) {
						finalAction.setBuildStep(true);//la carte est construite
					}
					else {//Ne peux pas l'acheter par ses moyens.
						//On regarde les possibilités d'achats chez ses voisins.
						List<MaterialsCostArray[]> materialPurchasePossibility = ((MaterialCost) currentStep.getCost()).soluceBuyNeighbours(
								wonderBoard.getAllEffects(),
								leftNeigthbour.getAllEffects(),
								rightNeigthbour.getAllEffects());
						//On regarde le prix a payer chez chaque voisins
						List<Integer[]> purchasePossibility = ((MaterialCost) currentStep.getCost()).costListBuyNeightbour(
								materialPurchasePossibility,
								wonderBoard.getAllEffects().filterOneCoinNeighborEffect());

						if(purchasePossibility.size() == 0){//Il ne peut pas construire meme avec ses voisins
							finalAction.setCantBuildStep(true);
							finalAction.setCoinEarned(3);
							finalAction.setDiscardCard(true);
						}
						else {
							Integer[] possibilityChoosed = ai.choosePurchasePossibility(purchasePossibility);
							if(possibilityChoosed == null){//L'IA ne veut pas acheter chez ses voisins.
								finalAction.setCoinEarned(3);
								finalAction.setDiscardCard(true);
							}
							//Si le joueur n'a pas assez d'argent pour acheter les ressources.
							else if((possibilityChoosed[0] + possibilityChoosed[1]) > wonderBoard.getCoin()){
								finalAction.setCantBuildStep(true);
								finalAction.setCoinEarned(3);
								finalAction.setDiscardCard(true);
							}
							else{//Si il a assez pour l'acheter.
								finalAction.setBuildStep(true);
								finalAction.setCoinToPay((possibilityChoosed[0] + possibilityChoosed[1]));
								finalAction.setCoinToPayLeftNeightbour(possibilityChoosed[0]);
								finalAction.setCoinToPayRigthNeightbour(possibilityChoosed[1]);
							}
						}
					}
				}
			}

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
						if(playedCard.getCostCard().canBuyCard(wonderBoard.getCoin())){//Si il a assez pour l'acheter.
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
						else {//Ne peux pas l'acheter par ses moyens.
							//On regarde les possibilités d'achats chez ses voisins.
							List<MaterialsCostArray[]> materialPurchasePossibility = ((MaterialCost) playedCard.getCostCard()).soluceBuyNeighbours(
									wonderBoard.getAllEffects(),
									leftNeigthbour.getAllEffects(),
									rightNeigthbour.getAllEffects());
							//On regarde le prix a payer chez chaque voisins
							List<Integer[]> purchasePossibility = ((MaterialCost) playedCard.getCostCard()).costListBuyNeightbour(
									materialPurchasePossibility,
									wonderBoard.getAllEffects().filterOneCoinNeighborEffect());

							if(purchasePossibility.size() == 0){//Il ne peut pas construire meme avec ses voisins
								finalAction.setCantBuildCard(true);
								finalAction.setCoinEarned(3);
								finalAction.setDiscardCard(true);
							}
							else {
								Integer[] possibilityChoosed = ai.choosePurchasePossibility(purchasePossibility);
								if(possibilityChoosed == null){//L'IA ne veut pas acheter chez ses voisins.
									finalAction.setCoinEarned(3);
									finalAction.setDiscardCard(true);
								}
								//Si le joueur n'a pas assez d'argent pour acheter les ressources.
								else if((possibilityChoosed[0] + possibilityChoosed[1]) > wonderBoard.getCoin()){
									finalAction.setCantBuildCard(true);
									finalAction.setCoinEarned(3);
									finalAction.setDiscardCard(true);
								}
								else{//Si il a assez pour l'acheter.
									finalAction.setBuildCard(true);
									finalAction.setCoinToPay((possibilityChoosed[0] + possibilityChoosed[1]));
									finalAction.setCoinToPayLeftNeightbour(possibilityChoosed[0]);
									finalAction.setCoinToPayRigthNeightbour(possibilityChoosed[1]);
								}
							}
						}
					}
				}
			}
		}
		this.endActionStatistics(statObject, playerName);
		currentDeck.removeCard(action.getIndexOfCard());
	}


	/** Pour les tests unitaires */
	public void playAction(Deck currentDeck, WonderBoard wonderBoard)
	{ this.playAction(currentDeck, wonderBoard, null, null,null,null); }

	private void endActionStatistics (StatObject statObject, String playerName)
	{
		if (statObject != null)
		{
			int indexInStatObject = statObject.getUsernames().indexOf(playerName) - 1;
			if (this.finalAction.isBuildCard())
			{
				ArrayList<Integer> array = new ArrayList<Integer>();
				this.fillStatisticsArray(indexInStatObject, statObject, array);
				statObject.getStatCards(this.playedCard.getType().getIndex()).add(array);
			}
		}
	}

	private void fillStatisticsArray (int index, StatObject statObject, ArrayList<Integer> array)
	{
		// - 1 a cause du username '/'
		for (int i = 0; i < statObject.getUsernames().size() - 1; i++)
		{
			if (i == index) { array.add(1); }
			else { array.add(0); }
		}
	}

	/**
	 * finit les actions en cours en appliquant leurs effets
	 * @param playerName
	 * @param wonderBoard
	 * @param discardingDeck
	 */
	public void finishAction(String playerName, WonderBoard wonderBoard, Deck discardingDeck,
							 WonderBoard leftNeigthbour, WonderBoard rightNeigthbour){

		GameLogger.getInstance().logSpaceBefore("Le joueur : ["+playerName+"] :",ConsoleColors.ANSI_CYAN_BOLD);

    	if(finalAction.cantBuildCard()){
			GameLogger.getInstance().log("Ne peut pas construire/payer la carte "+playedCard.getName(), ConsoleColors.ANSI_RED);
			playedCardIsBuild = false;
		}
		if(finalAction.cantBuildStep()){ //le step ne peut pas etre construit.
			if(currentStep == null) GameLogger.getInstance().log("Ne peut plus construire d'étape de sa merveille.", ConsoleColors.ANSI_RED);
			else GameLogger.getInstance().log("Ne peut pas construire l'étape *"+currentStep.getStepNumber()+"* de la merveille.", ConsoleColors.ANSI_RED);

			playedStepIsBuild = false;
		}
		if(finalAction.getCoinToPay() != 0){//Paiement d'une carte
			wonderBoard.removeCoin(finalAction.getCoinToPay());
			GameLogger.getInstance().log("A payé "+finalAction.getCoinToPay()+" pièces");
		}
		if(finalAction.getCoinToPayLeftNeightbour() != 0){
			leftNeigthbour.addCoin(finalAction.getCoinToPayLeftNeightbour());
			GameLogger.getInstance().log("A payé "+finalAction.getCoinToPayLeftNeightbour()+" pièces à son voisin de gauche.");
		}
		if(finalAction.getCoinToPayRigthNeightbour() != 0){
			leftNeigthbour.addCoin(finalAction.getCoinToPayRigthNeightbour());
			GameLogger.getInstance().log("A payé "+finalAction.getCoinToPayRigthNeightbour()+" pièces à son voisin de droite.");
		}
		if(finalAction.isBuildCard()){//Construction de carte.
			wonderBoard.addCardToBuilding(playedCard);
			GameLogger.getInstance().log("A construit la carte "+playedCard.getName());
			playedCardIsBuild = true;//La carte est construite.
		}
		if(finalAction.isDiscardCard()){//Defausse de carte
			discardingDeck.addCard(playedCard);
			GameLogger.getInstance().log("A defaussée la carte : "+playedCard.getName());
			playedCardIsBuild = false;
			playedStepIsBuild=false;
		}
		if(finalAction.getCoinEarned() != 0){//Gain de pièces.
			wonderBoard.addCoin(finalAction.getCoinEarned());
			GameLogger.getInstance().log("A gagné "+finalAction.getCoinEarned()+" pièces");
		}
		if(finalAction.isBuildStep()){ //construire le step.
			GameLogger.getInstance().log("A construit l'étape  *"+currentStep.getStepNumber()+"* de la merveille.");
			playedStepIsBuild=true;
			currentStep.setConstructionMarker(playedCard); // le marqueur de l'etape
			currentStep.toBuild(); //l'etape est construite
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

		if(playedCardIsBuild && playedCard.getCardEffect() != null){//SEULEMENT SI LA CARTE EST CONSTRUITE.

			//CARTE COMME TAVERNE
			if(playedCard.getCardEffect().getNumberOfCoin()!=0){
				GameLogger.getInstance().logSpaceBefore(playerName+" gagne "+playedCard.getCardEffect().getNumberOfCoin()+" pieces grâce au batiment "+playedCard.getName(), ConsoleColors.ANSI_GREEN);
				wonderBoard.addCoin(playedCard.getCardEffect().getNumberOfCoin());//Ajout des pièces.
			}

			//CARTE COMME CASERNE
			if(playedCard.getCardEffect().getMilitaryEffect() != 0){
				GameLogger.getInstance().logSpaceBefore(playerName+ " gagne "+playedCard.getCardEffect().getMilitaryEffect() + " de puissance millitaire grâce au batiment "+playedCard.getName(), ConsoleColors.ANSI_GREEN);
				wonderBoard.addMilitaryPower(playedCard.getCardEffect().getMilitaryEffect());
			}

			//CARTE COMME VIGNOBLE
			if(playedCard.getCardEffect().getEarnWithCardEffect() != null){
				EarnWithCard earnWithCard = playedCard.getCardEffect().getEarnWithCardEffect();
				int coinEarned = 0;

				//Pieces gagné chez soit x le facteur de pièces.
				coinEarned += wonderBoard.countCard(earnWithCard.getCardType()) * earnWithCard.getCoinEarn();

				//LE PHARE NE DOIS PAS SE COMPTER ELLE MEME RETIRER 1 COIN;
				if(playedCard.getName().equals("PHARE")){ coinEarned -= 1;}

				if(earnWithCard.getAffectedNeightbour() == TargetType.ME_AND_NEIGHTBOUR){
					//On gagne des pièces pour les cartes construites chez nos deux voisins.
					coinEarned += leftNeigthbour.countCard(earnWithCard.getCardType()) * earnWithCard.getCoinEarn();
					coinEarned += rightNeigthbour.countCard(earnWithCard.getCardType()) * earnWithCard.getCoinEarn();
				}

				wonderBoard.addCoin(coinEarned);
				GameLogger.getInstance().logSpaceBefore(playerName+ " gagne "+coinEarned+" pièces grâce au batiment "+playedCard.getName(), ConsoleColors.ANSI_GREEN);
			}

			//CARTE COMME ARÈNE
			if(playedCard.getCardEffect().getEarnWithWonderEffect() != null){
				EarnWithWonder earnWithWonder = playedCard.getCardEffect().getEarnWithWonderEffect();

				//Pieces gagné chez soit x le facteur de pièces.
				int coinEarned = wonderBoard.countStepBuild() * earnWithWonder.getCoinEarn();

				wonderBoard.addCoin(coinEarned);
				GameLogger.getInstance().logSpaceBefore(playerName+ " gagne "+coinEarned+" pièces grâce au batiment "+playedCard.getName(), ConsoleColors.ANSI_GREEN);
			}

			//TODO GERER AUTRE CARTE
		}
		// Les Etape de la Merveille
		if(playedStepIsBuild){
			for (IEffect effect: currentStep.getEffects()) {
				if(effect.getNumberOfCoin() != 0){
					GameLogger.getInstance().logSpaceBefore(playerName+" gagne "+effect.getNumberOfCoin()+" pieces grâce à l'étape  *"+currentStep.getStepNumber()+"* de la merveille.", ConsoleColors.ANSI_GREEN);
					wonderBoard.addCoin(effect.getNumberOfCoin());//Ajout des pièces.
				}
				if(effect.getMilitaryEffect() != 0){
					GameLogger.getInstance().logSpaceBefore(playerName+ " gagne "+effect.getMilitaryEffect() + " de puissance millitaire grâce à l'étape  *"+currentStep.getStepNumber()+"* de la merveille.", ConsoleColors.ANSI_GREEN);
					wonderBoard.addMilitaryPower(effect.getMilitaryEffect()); //ajout des carte millitaire
				}
			}
		}
	}
}
