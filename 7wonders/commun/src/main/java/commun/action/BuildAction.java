package commun.action;

import commun.card.Card;
import commun.card.Deck;
import commun.wonderboard.WonderBoard;
import commun.wonderboard.WonderStep;
import log.ConsoleColors;
import log.GameLogger;

import java.util.ArrayList;
import java.util.List;

public class BuildAction extends AbstractAction {

    private int indexOfCard;
    private Card playedCard;
    private boolean haveBuild = false;//Variable si le joueur a réussis à construire.
    private boolean haveBuildWithJoker = false;//Si il a construit avec son joker.
    private AbstractAction action;
    private List<Integer[]> tradePossibility;//Liste des possibilité d'echange

    public BuildAction(int indexOfCard){
        this.indexOfCard = indexOfCard;
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
        if(playedCard.getCostCard() == null){
            haveBuild = true;
        }

        //Carte coutant des pièces.
        int cost = playedCard.getCostCard().getCoinCost();

        if(cost > 0){
            if(playedCard.getCostCard().canBuyCard(wonderBoard.getCoin()) ){//Si il a assez pour l'acheter
                wonderBoard.removeCoin(playedCard.getCostCard().getCoinCost());
                haveBuild = true;
            }
            //TODO
            else if (action.isPlayJoker()){ //Si il n'a pas assez pour l'acheter mais qu'il a un joker étape de la merveille
                for (WonderStep wonderStep: wonderBoard.getWonderSteps() ) {
                    if (wonderStep.getBuilt() && wonderStep.isHaveJoker() && !wonderStep.isUsedJoker()) {
                        //on ne peut utiliser le joker que si l'étape est construite et qu'elle a un joker et qu'il n'ai pas utilisé dans cette age
                        haveBuild = true;
                        haveBuildWithJoker = true;
                        wonderStep.setUsedJoker(true);
                    }
                }
            }
            else{//Il ne peut pas payer.
                action = new DiscardAction(indexOfCard);
                action.playAction(playerName,currentDeck,wonderBoard,discardingDeck,leftNeigthbour,rightNeigthbour);//On Discard.
            }
        }

    }

    @Override
    public void logAction(String playerName, WonderBoard wonderBoard, Deck discardingDeck, WonderBoard leftNeigthbour, WonderBoard rightNeigthbour) {
        if(haveBuild){
            if(action != null) //Si on a acheter chez le voisin.
                action.logAction(playerName,wonderBoard,discardingDeck,leftNeigthbour,rightNeigthbour);//On log le paiement des voisins.
            GameLogger.getInstance().log("A construit la carte "+playedCard.getName());
        }
        else{//N'a pas pu construire.
            GameLogger.getInstance().log("Ne peut pas construire/payer la carte "+playedCard.getName(), ConsoleColors.ANSI_RED);
            action.logAction(playerName,wonderBoard,discardingDeck,leftNeigthbour,rightNeigthbour);//On log le discard (ou discard de tradeAction).
        }

    }

    @Override
    public void finishAction(String playerName, WonderBoard wonderBoard, Deck discardingDeck, WonderBoard leftNeigthbour, WonderBoard rightNeigthbour, Card card, AI ai) {
        wonderBoard.addCardToBuilding(playedCard);//On construit après que tout le monde ai joué.
    }
}
