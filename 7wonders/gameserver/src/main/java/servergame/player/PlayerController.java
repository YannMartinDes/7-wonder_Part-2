package servergame.player;

import client.AI.AI;
import commun.action.ActionType;
import commun.action.FinalAction;
import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.action.Action;
import commun.communication.StatObject;
import commun.effect.EarnWithCard;
import commun.effect.EffectList;
import commun.effect.IEffect;
import commun.effect.TargetType;
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
	private boolean playedCardIsBuild;
	private boolean playedStepIsBuild;
	private WonderStep playedStep;

	
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
			this.action = ai.chooseAction(deck, playerCoins, playerEffects,wonderSteps);
    }
    
	public Action getAction() {
		return action;
	}

	/**
	 * L'action se joue et les effets sont mémoriser
	 * @param currentDeck
	 * @param wonderBoard
	 */
	public void playAction(Deck currentDeck, WonderBoard wonderBoard, StatObject statObject, String playerName){
		playedCard = currentDeck.getCard(action.getIndexOfCard());
		playedCardIsBuild = false;//On ne sais pas si elle va être construite.
		playedStep = action.getWonderStep();
		playedStepIsBuild=false;


		if(action.getActionType() == ActionType.DISCARD){
			finalAction.setCoinEarned(3);
			finalAction.setDiscardCard(true);
		}
		else if(action.getActionType() == ActionType.BUILD_STAGE_WONDER){

			if(action.getWonderStep().getBuilt() == true){
				// Si l'étape choisi est deja construite.
				finalAction.setCoinEarned(3);
				finalAction.setDiscardCard(true);
			}
			else {
				Material[] materialCost = playedStep.getCost().getMaterialCost() ; // le cout est toujours en materiel
				if(materialCost != null) {
					//savoir si j'ai assez de ressource pour construire cette etape de la merveille
					if (playedStep.getCost().canBuyCard(wonderBoard.getAllEffects())) {
						finalAction.setBuildStep(true);    //la carte est construit
						finalAction.setStepBuilt(playedStep.getStep());
						playedStep.setConstructionMarker(playedCard); // le marqueur de l'etape
						playedStep.toBuild(); //l'etape est construit
						playedStepIsBuild=true;
					}
					else{
						finalAction.setBuildStep(false);
						finalAction.setCoinEarned(3);
						finalAction.setDiscardCard(true);
						finalAction.setStepBuilt(playedStep.getStep());

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
		this.endActionStatistics(statObject, playerName);
		currentDeck.removeCard(action.getIndexOfCard());
	}

	/** Pour les tests unitaires */
	public void playAction(Deck currentDeck, WonderBoard wonderBoard)
	{ this.playAction(currentDeck, wonderBoard, null, null); }

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
	public void finishAction(String playerName, WonderBoard wonderBoard, Deck discardingDeck){

		GameLogger.getInstance().logSpaceBefore("Le joueur : ["+playerName+"] :",ConsoleColors.ANSI_CYAN_BOLD);

    	if(finalAction.cantBuildCard()){
			GameLogger.getInstance().log("Ne peut pas construire/payer la carte "+playedCard.getName(), ConsoleColors.ANSI_RED);
			playedCardIsBuild = false;
		}
		if(finalAction.getCoinToPay() != 0){//Paiement d'une carte
			wonderBoard.removeCoin(finalAction.getCoinToPay());
			GameLogger.getInstance().log("A payé "+finalAction.getCoinToPay()+" pièces");
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
		}
		if(finalAction.getCoinEarned() != 0){//Gain de pièces.
			wonderBoard.addCoin(finalAction.getCoinEarned());
			GameLogger.getInstance().log("A gagné "+finalAction.getCoinEarned()+" pièces");
		}
		if(finalAction.isBuildStep()){ //construire le step.
			GameLogger.getInstance().log("A construit l'étape  *"+finalAction.getStepBuilt()+"* de la merveille.");
			playedCardIsBuild = false;
			playedStepIsBuild=true;

		}
		if(!finalAction.isBuildStep() && finalAction.getStepBuilt() != 0){ //le setp ne peut pas etres construit
			GameLogger.getInstance().log("Ne peut pas construire l'étape *"+finalAction.getStepBuilt()+"* de la merveille.", ConsoleColors.ANSI_RED);
			playedCardIsBuild = false;
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

			//TODO GERER AUTRE CARTE
		}
		// Les Etape de la Merveille
		if(playedStepIsBuild){
			for (IEffect effect: Arrays.asList(playedStep.getEffects())) {
				if(effect.getNumberOfCoin() != 0){
					GameLogger.getInstance().logSpaceBefore(playerName+" gagne "+effect.getNumberOfCoin()+" pieces grâce à l'étape  *"+playedStep.getStep()+"* de la merveille.", ConsoleColors.ANSI_GREEN);
					wonderBoard.addCoin(effect.getNumberOfCoin());//Ajout des pièces.
				}
				if(effect.getMilitaryEffect() != 0){
					GameLogger.getInstance().logSpaceBefore(playerName+ " gagne "+effect.getMilitaryEffect() + " de puissance millitaire grâce à l'étape  *"+playedStep.getStep()+"* de la merveille.", ConsoleColors.ANSI_GREEN);
					wonderBoard.addMilitaryPower(effect.getMilitaryEffect()); //ajout des carte millitaire
				}
			}

			}
	}
}
