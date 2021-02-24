package commun.action;

import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.cost.MaterialCost;
import commun.cost.solver.MaterialsCostArray;
import commun.effect.IEffect;
import commun.material.Material;
import commun.request.RequestToPlayer;
import commun.wonderboard.WonderBoard;
import commun.wonderboard.WonderStep;
import log.Logger;
import log.coloring.ConsoleColors;

import java.util.ArrayList;
import java.util.List;

public class BuildStepAction extends AbstractAction {

    private WonderStep currentStep;//Etape courante de la merveille.
    private boolean haveBuildStep = false;//Variable si le joueur a réussis à construire.
    private List<Integer[]> tradePossibility;//Liste des possibilité d'echange

    public BuildStepAction(int indexOfCard){
        super(indexOfCard);
        this.tradePossibility = new ArrayList<>();
    }

    @Override
    public void playAction(String playerName, Deck currentDeck, WonderBoard wonderBoard, Deck discardingDeck, WonderBoard leftNeigthbour, WonderBoard rightNeigthbour) {
        playedCard = currentDeck.getCard(indexOfCard);
        currentStep = wonderBoard.getCurrentStep();

        if(currentStep == null){//Il ne reste aucune étapes à construire.
            action = new DiscardAction(indexOfCard);
            action.playAction(playerName, currentDeck,wonderBoard,discardingDeck,leftNeigthbour,rightNeigthbour);//On joue la défausse.
            return;//Action terminée.
        }

        Material[] materialCost = currentStep.getCost().getMaterialCost() ; // le cout est toujours en materiel
        if(materialCost != null) {
            //savoir si j'ai assez de ressource pour construire cette etape de la merveille
            if (currentStep.getCost().canBuyCard(wonderBoard.getAllEffects())) {

                currentStep.setConstructionMarker(playedCard); // le marqueur de l'etape
                currentStep.toBuild(); //l'etape est construite
                haveBuildStep = true;
                currentDeck.removeCard(indexOfCard);//On retire la carte de la main.
            }
            else {//Ne peux pas l'acheter par ses moyens.

                //On regarde les possibilités d'achats chez ses voisins.
                List<MaterialsCostArray[]> materialPurchasePossibility = ((MaterialCost) currentStep.getCost()).soluceBuyNeighbours(
                        wonderBoard.getAllEffects(), leftNeigthbour.getAllEffects(), rightNeigthbour.getAllEffects());

                //On regarde le prix a payer chez chaque voisins
                List<Integer[]> purchasePossibility = ((MaterialCost) currentStep.getCost()).costListBuyNeightbour(materialPurchasePossibility, wonderBoard.getAllEffects().filterOneCoinNeighborEffect());

                if (purchasePossibility.size() == 0) {//Il ne peut pas construire meme avec ses voisins
                    action = new DiscardAction(indexOfCard);
                    action.playAction(playerName, currentDeck, wonderBoard, discardingDeck, leftNeigthbour, rightNeigthbour);//On joue la défausse.
                    return;//Action terminée.
                }
                else tradePossibility = purchasePossibility;//On met à jour les possibilités d'achat.
            }
        }
    }


    @Override
    public List<Integer[]> getTradePossibility() {
        return tradePossibility;
    }

    @Override
    public void logAction(String playerName, WonderBoard wonderBoard, Deck discardingDeck, WonderBoard leftNeigthbour, WonderBoard rightNeigthbour) {
        if(haveBuildStep){
            if(action != null) //Si on a acheter chez le voisin.
                action.logAction(playerName,wonderBoard,discardingDeck,leftNeigthbour,rightNeigthbour);//On log le paiement des voisins.
            Logger.logger.log(playerName+ " a construit l'étape  *"+currentStep.getStepNumber()+"* de la merveille.");
        }
        else{//N'a pas pu construire.
            if(currentStep == null) Logger.logger.log(playerName+ " ne peut plus construire d'étape de sa merveille.", ConsoleColors.ANSI_RED);
            else Logger.logger.log(playerName+" n'as pas pu construire l'étape "+currentStep.getStepNumber()+" de sa merveille.", ConsoleColors.ANSI_RED);
            action.logAction(playerName,wonderBoard,discardingDeck,leftNeigthbour,rightNeigthbour);//On log le discard (ou discard de tradeAction).
        }

    }

    @Override
    public void finishAction(String playerName, WonderBoard wonderBoard, Deck discardingDeck, WonderBoard leftNeigthbour, WonderBoard rightNeigthbour,Card card ,RequestToPlayer ai) {
        if(currentStep==null || !haveBuildStep) return; // plus d'etape a construire
        if(currentStep.isPlayDiscardedCard()){ //gagner une carte dans la défausse grace à un étape de la merveille
            playDiscardCard(playerName,wonderBoard,discardingDeck,leftNeigthbour,rightNeigthbour,ai);
        }

        if(currentStep.isCopyNeighborGuild()){ // copier une carte guilde chez un voisin
            copyGuildCard(playerName,wonderBoard,leftNeigthbour,rightNeigthbour,ai);
        }

        for (IEffect effect: currentStep.getEffects()) {
            if(effect.getNumberOfCoin() != 0){
                Logger.logger.logSpaceBefore(playerName+" gagne "+effect.getNumberOfCoin()+" pieces grâce à l'étape  *"+currentStep.getStepNumber()+"* de la merveille.");
                wonderBoard.addCoin(effect.getNumberOfCoin());//Ajout des pièces.
            }
            if(effect.getMilitaryEffect() != 0){
                Logger.logger.logSpaceBefore(playerName+ " gagne "+effect.getMilitaryEffect() + " de puissance millitaire grâce à l'étape  *"+currentStep.getStepNumber()+"* de la merveille.");
                wonderBoard.addMilitaryPower(effect.getMilitaryEffect()); //ajout des carte millitaire
            }
        }
    }

    /**
     * Effet de pioche dans la défausse.
     */
    private void playDiscardCard(String playerName, WonderBoard wonderBoard, Deck discardingDeck, WonderBoard leftNeigthbour, WonderBoard rightNeigthbour , RequestToPlayer ai){
        if(discardingDeck.getLength() == 0) return;//Si il n'y a pas de cartes.

        int index = ai.chooseCard(discardingDeck);//l'IA Choisi la carte dans la défausse.
        Card chooseCard = discardingDeck.getCard(index);

        Logger.logger.log(playerName+" a construit la carte "+chooseCard.getName()+" parmis les carte défaussées grâce à la merveille.");
        action = new BuildAction(indexOfCard, false);
        //Deck manuel pour mettre haveBuild à true.
        Deck fakeDeck = new Deck(); fakeDeck.addCard(new Card("fakeCard",CardType.MANUFACTURED_PRODUCTS,null,-1,null));
        action.playAction("fakeName",fakeDeck,wonderBoard,discardingDeck,leftNeigthbour,rightNeigthbour);
        action.finishAction(playerName,wonderBoard,discardingDeck,leftNeigthbour,rightNeigthbour,chooseCard,ai);//On joue les effets (et ajoute) la carte construite.

        discardingDeck.remove(chooseCard);//La carte n'est plus dans la défausse
    }

    /**
     * Effet de copy de carte guilde chez les voisins.
     */
    private void copyGuildCard(String playerName, WonderBoard wonderBoard, WonderBoard leftNeigthbour, WonderBoard rightNeigthbour,RequestToPlayer ai){
        Deck neighborCards = new Deck();
        Deck neighborGuilds = new Deck();
        neighborCards.addAll(leftNeigthbour.getBuilding());
        neighborCards.addAll(rightNeigthbour.getBuilding());

        for (Card _card: neighborCards) {//FILTRE DES CARTES GUILDES.
            if(_card.getType() == CardType.GUILD_BUILDINGS){
                neighborGuilds.add(_card);
            }
        }

        int index = ai.chooseCard(neighborCards);//TODO FAIRE UNE METHODE PARTICULIERE
        //Pas d'effet immédiat donc pas besoin de regarder la carte choisie.
        wonderBoard.addCardToBuilding(neighborCards.get(index));
        Logger.logger.log(playerName+" a copier la carte guilde "+neighborCards.get(index)+" parmis celles de ses voisins grâce à sa merveille.");
    }

    @Override
    public void nextAction(String playerName, Deck currentDeck, WonderBoard wonderBoard, Deck discardingDeck, WonderBoard leftNeigthbour, WonderBoard rightNeigthbour, Integer[] AIChoice) {
        action = new TradeAction(AIChoice, indexOfCard);
        action.playAction(playerName,currentDeck,wonderBoard,discardingDeck,leftNeigthbour,rightNeigthbour);//On joue l'action de trade.
        haveBuildStep = ((TradeAction) action).hasBuy();

        if(haveBuildStep){
            currentStep.setConstructionMarker(playedCard); // le marqueur de l'etape
            currentStep.toBuild(); //l'etape est construite
            currentDeck.removeCard(indexOfCard);//On retire la carte de la main.
        }
    }
}
