package commun.action;

import commun.card.Card;
import commun.card.Deck;
import commun.cost.MaterialCost;
import commun.cost.solver.MaterialsCostArray;
import commun.effect.EarnWithCard;
import commun.effect.EarnWithWonder;
import commun.effect.TargetType;
import commun.material.Material;
import commun.request.RequestToPlayer;
import commun.wonderboard.WonderBoard;
import commun.wonderboard.WonderStep;
import log.ConsoleColors;
import log.GameLogger;

import java.util.ArrayList;
import java.util.List;

public class BuildAction extends AbstractAction {

    private boolean haveBuild = false;//Variable si le joueur a réussis à construire.
    private boolean haveBuildWithJoker = false;//Si il a construit avec son joker.
    private List<Integer[]> tradePossibility;//Liste des possibilité d'echange
    private boolean isPlayJoker = false;

    public BuildAction(int indexOfCard, boolean playJoker){
        super(indexOfCard);
        this.isPlayJoker = playJoker;
        type = ActionType.BUILD;//TODO DELETE
        this.tradePossibility = new ArrayList<>();
    }

    @Override
    public List<Integer[]> getTradePossibility() {
        return tradePossibility;
    }

    @Override
    public Card getPlayedCard() {
        return playedCard;
    }

    @Override
    public void playAction(String playerName, Deck currentDeck, WonderBoard wonderBoard, Deck discardingDeck, WonderBoard leftNeigthbour, WonderBoard rightNeigthbour) {
        playedCard = currentDeck.getCard(indexOfCard);

        if(wonderBoard.isAlreadyInBuilding(playedCard.getName())){//On ne peut pas construire deux cartes du meme nom.
            action = new DiscardAction(indexOfCard);
            action.playAction(playerName,currentDeck,wonderBoard,discardingDeck,leftNeigthbour,rightNeigthbour);//On Discard.
            return;
        }
        //null -> cartes gratuites
        if(playedCard.getCostCard() == null || wonderBoard.getNameOfFreeCards().contains(playerName) ){
            haveBuild = true;
            currentDeck.removeCard(indexOfCard);
            return;
        }

        //Carte coutant des pièces.
        int cost = playedCard.getCostCard().getCoinCost();
        if(cost > 0){
            buyWithMoney(playerName,currentDeck,wonderBoard,discardingDeck,leftNeigthbour,rightNeigthbour);
        }

        Material[] materialCost = playedCard.getCostCard().getMaterialCost();
        if(materialCost != null){
            buyWithMaterial(playerName,currentDeck,wonderBoard,discardingDeck,leftNeigthbour,rightNeigthbour);
        }
    }

    /**
     * Achat d'une carte avec l'argent
     */
    private void buyWithMoney(String playerName, Deck currentDeck, WonderBoard wonderBoard, Deck discardingDeck, WonderBoard leftNeigthbour, WonderBoard rightNeigthbour){
        if(playedCard.getCostCard().canBuyCard(wonderBoard.getCoin()) ){//Si il a assez pour l'acheter
            wonderBoard.removeCoin(playedCard.getCostCard().getCoinCost());
            haveBuild = true;
            currentDeck.removeCard(indexOfCard);
            return;
        }
        else if (isPlayJoker){ //Si il n'a pas assez pour l'acheter mais qu'il a un joker étape de la merveille
            for (WonderStep wonderStep: wonderBoard.getWonderSteps() ) {
                if (wonderStep.getBuilt() && wonderStep.isHaveJoker() && !wonderStep.isUsedJoker()) {
                    //on ne peut utiliser le joker que si l'étape est construite et qu'elle a un joker et qu'il n'ai pas utilisé dans cette age
                    haveBuildWithJoker = true;
                    currentDeck.removeCard(indexOfCard);
                    wonderStep.setUsedJoker(true);
                    return;
                }
            }
        }
        //Il ne peut pas payer.
        action = new DiscardAction(indexOfCard);
        action.playAction(playerName,currentDeck,wonderBoard,discardingDeck,leftNeigthbour,rightNeigthbour);//On Discard.
        return;

    }


    private void buyWithMaterial(String playerName, Deck currentDeck, WonderBoard wonderBoard, Deck discardingDeck, WonderBoard leftNeigthbour, WonderBoard rightNeigthbour){
        if(playedCard.getCostCard().canBuyCard(wonderBoard.getAllEffects())){//Peut l'acheter.
            haveBuild = true;
            currentDeck.removeCard(indexOfCard);
            return;
        }
        else if (isPlayJoker){ //Si il n'a pas assez pour l'acheter mais qu'il a un joker étape de la merveille
            for (WonderStep wonderStep: wonderBoard.getWonderSteps() ) {
                if (wonderStep.getBuilt() && wonderStep.isHaveJoker() && !wonderStep.isUsedJoker()) {
                    //on ne peut utiliser le joker que si l'étape est construite et qu'elle a un joker et qu'il n'ai pas utilisé dans cette age
                    haveBuildWithJoker = true;
                    currentDeck.removeCard(indexOfCard);
                    wonderStep.setUsedJoker(true);
                    return;
                }
            }
        }
        //Ne peux pas l'acheter par ses moyens.
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
            action = new DiscardAction(indexOfCard);
            action.playAction(playerName, currentDeck, wonderBoard, discardingDeck, leftNeigthbour, rightNeigthbour);//On joue la défausse.
            return;//Action terminée.
        }
        else tradePossibility = purchasePossibility;//On met à jour les possibilités d'achat.

    }


    @Override
    public void logAction(String playerName, WonderBoard wonderBoard, Deck discardingDeck, WonderBoard leftNeigthbour, WonderBoard rightNeigthbour) {
        if(haveBuildWithJoker){
            GameLogger.getInstance().log(playerName+ " a construit la carte "+playedCard.getName() +" gratuitement avec le joker de sa merveille.");
        }
        else if(haveBuild){
            if(action != null) //Si on a acheter chez le voisin.
                action.logAction(playerName,wonderBoard,discardingDeck,leftNeigthbour,rightNeigthbour);//On log le paiement des voisins.
            GameLogger.getInstance().log(playerName+ " a construit la carte "+playedCard.getName());
        }
        else{//N'a pas pu construire.
            GameLogger.getInstance().log(playerName+" ne peut pas construire/payer la carte "+playedCard.getName(), ConsoleColors.ANSI_RED);
            action.logAction(playerName,wonderBoard,discardingDeck,leftNeigthbour,rightNeigthbour);//On log le discard (ou discard de tradeAction).
        }
    }

    @Override
    public void finishAction(String playerName, WonderBoard wonderBoard, Deck discardingDeck, WonderBoard leftNeigthbour, WonderBoard rightNeigthbour, Card card, RequestToPlayer ai) {
        if(!haveBuild && !haveBuildWithJoker) return;//CARTE NON ACHETEE.

        wonderBoard.addCardToBuilding(card);//On construit après que tout le monde ai joué.

        //CARTE COMME TAVERNE
        if(card.getCardEffect().getNumberOfCoin()!=0){
            GameLogger.getInstance().logSpaceBefore(playerName+" gagne "+card.getCardEffect().getNumberOfCoin()+" pieces grâce au batiment "+card.getName(), ConsoleColors.ANSI_GREEN);
            wonderBoard.addCoin(card.getCardEffect().getNumberOfCoin());//Ajout des pièces.
        }

        //CARTE COMME CASERNE
        if(card.getCardEffect().getMilitaryEffect() != 0){
            GameLogger.getInstance().logSpaceBefore(playerName+ " gagne "+card.getCardEffect().getMilitaryEffect() + " de puissance millitaire grâce au batiment "+card.getName(), ConsoleColors.ANSI_GREEN);
            wonderBoard.addMilitaryPower(card.getCardEffect().getMilitaryEffect());
        }

        //CARTE COMME VIGNOBLE
        if(card.getCardEffect().getEarnWithCardEffect() != null){
            EarnWithCardCompute(playerName,wonderBoard,leftNeigthbour,rightNeigthbour,card);
        }

        //CARTE COMME ARÈNE
        if(card.getCardEffect().getEarnWithWonderEffect() != null){
            EarnWithWonderCompute(playerName,wonderBoard,leftNeigthbour,rightNeigthbour,card);
        }
    }

    /**
     * Application de l'effet comme la carte Arene.
     */
    private void EarnWithWonderCompute(String playerName, WonderBoard wonderBoard, WonderBoard leftNeigthbour, WonderBoard rightNeigthbour, Card card){
        EarnWithWonder earnWithWonder = card.getCardEffect().getEarnWithWonderEffect();

        //Pieces gagné chez soit x le facteur de pièces.
        int coinEarned = wonderBoard.countStepBuild() * earnWithWonder.getCoinEarn();

        if(earnWithWonder.getAffectedNeightbour() == TargetType.ME_AND_NEIGHTBOUR){
            //On gagne des pièces pour les cartes construites chez lui et ses deux voisins.
            coinEarned += leftNeigthbour.countStepBuild() * earnWithWonder.getCoinEarn();
            coinEarned += rightNeigthbour.countStepBuild() * earnWithWonder.getCoinEarn();
        }

        wonderBoard.addCoin(coinEarned);
        GameLogger.getInstance().logSpaceBefore(playerName+ " gagne "+coinEarned+" pièces grâce au batiment "+card.getName(), ConsoleColors.ANSI_GREEN);
    }

    /**
     * Application de l'effet comme la carte Vignoble.
     */
    private void EarnWithCardCompute(String playerName, WonderBoard wonderBoard, WonderBoard leftNeigthbour, WonderBoard rightNeigthbour, Card card) {
        EarnWithCard earnWithCard = card.getCardEffect().getEarnWithCardEffect();
        int coinEarned = 0;

        //Pieces gagné chez soit x le facteur de pièces.
        coinEarned += wonderBoard.countCard(earnWithCard.getCardType()) * earnWithCard.getCoinEarn();

        //LE PHARE NE DOIS PAS SE COMPTER ELLE MEME RETIRER 1 COIN;
        if(card.getName().equals("PHARE")){ coinEarned -= 1;}

        if(earnWithCard.getAffectedNeightbour() == TargetType.ME_AND_NEIGHTBOUR){
            //On gagne des pièces pour les cartes construites chez nous et nos deux voisins.
            coinEarned += leftNeigthbour.countCard(earnWithCard.getCardType()) * earnWithCard.getCoinEarn();
            coinEarned += rightNeigthbour.countCard(earnWithCard.getCardType()) * earnWithCard.getCoinEarn();
        }
        if(earnWithCard.getAffectedNeightbour() == TargetType.BOTH_NEIGHTBOUR){
            //On gagne des pièces pour les cartes construites chez nos deux voisins.
            coinEarned += leftNeigthbour.countCard(earnWithCard.getCardType()) * earnWithCard.getCoinEarn();
            coinEarned += rightNeigthbour.countCard(earnWithCard.getCardType()) * earnWithCard.getCoinEarn();
            coinEarned -= wonderBoard.countCard(earnWithCard.getCardType()) * earnWithCard.getCoinEarn();

        }

        wonderBoard.addCoin(coinEarned);
        GameLogger.getInstance().logSpaceBefore(playerName+ " gagne "+coinEarned+" pièces grâce au batiment "+card.getName(), ConsoleColors.ANSI_GREEN);
    }

    @Override
    public void nextAction(String playerName, Deck currentDeck, WonderBoard wonderBoard, Deck discardingDeck, WonderBoard leftNeigthbour, WonderBoard rightNeigthbour, Integer[] AIChoice) {
        action = new TradeAction(AIChoice, indexOfCard);
        action.playAction(playerName,currentDeck,wonderBoard,discardingDeck,leftNeigthbour,rightNeigthbour);//On joue l'action de trade.
        haveBuild = ((TradeAction) action).hasBuy();

        if(haveBuild){
            currentDeck.removeCard(indexOfCard);
        }
    }
}
