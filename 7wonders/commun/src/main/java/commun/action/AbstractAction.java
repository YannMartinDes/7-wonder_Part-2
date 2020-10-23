package commun.action;

import commun.card.Card;
import commun.card.Deck;
import commun.request.RequestToPlayer;
import commun.wonderboard.WonderBoard;

import java.util.ArrayList;
import java.util.List;

/** Action est une classe qui permet a l'IA de faire une action */
public abstract class AbstractAction {//TODO ajouter champ commun.

    protected int indexOfCard;//Index de la carte choisie par l'ia
    protected Card playedCard;//Carte jouée
    protected AbstractAction action;//Sous-action
    protected ActionType type;//POUR LES STATS todo Gerer autrement.

    protected AbstractAction(int indexOfCard){
        this.indexOfCard = indexOfCard;
    }

    /**
     * Permet de jouer l'action
     * @param playerName le nom du joueur
     * @param currentDeck la main du joueur
     * @param wonderBoard le plateau merveille
     * @param discardingDeck la défausse
     * @param leftNeigthbour le voisin de gauche
     * @param rightNeigthbour le voisin de droite
     */
    public abstract void playAction(String playerName, Deck currentDeck, WonderBoard wonderBoard, Deck discardingDeck,
                                   WonderBoard leftNeigthbour, WonderBoard rightNeigthbour);

    /**
     * Permet de log l'action
     * @param playerName le nom du joueur
     * @param wonderBoard le plateau merveille
     * @param discardingDeck la défausse
     * @param leftNeigthbour le voisin de gauche
     * @param rightNeigthbour le voisin de droite
     */
    public abstract void logAction(String playerName, WonderBoard wonderBoard, Deck discardingDeck,
                                  WonderBoard leftNeigthbour, WonderBoard rightNeigthbour);

    /**
     * Permet de finir l'action et les effets de ce qui est construit
     * @param playerName le nom du joueur
     * @param wonderBoard le plateau merveille
     * @param discardingDeck la défausse
     * @param leftNeigthbour le voisin de gauche
     * @param rightNeigthbour le voisin de droite
     * @param card la carte construite
     * @param ai l'ia.
     */
    public void finishAction(String playerName, WonderBoard wonderBoard, Deck discardingDeck,
                                     WonderBoard leftNeigthbour, WonderBoard rightNeigthbour, Card card, RequestToPlayer ai){

    }

    /**
     * Les possibilité pour le commerce
     * @return la liste de possibilité
     */
    public List<Integer[]> getTradePossibility(){return new ArrayList<>();}

    /**
     * Action complémentaire d'une action comme le commerce
     * @param playerName le nom du joueur
     * @param wonderBoard le plateau merveille
     * @param discardingDeck la défausse
     * @param leftNeigthbour le voisin de gauche
     * @param rightNeigthbour le voisin de droite
     * @param AIChoice le choix de l'ia pour l'action complémentaire
     */
    public void nextAction(String playerName, Deck currentDeck, WonderBoard wonderBoard, Deck discardingDeck,
                           WonderBoard leftNeigthbour, WonderBoard rightNeigthbour,Integer[] AIChoice){

    }

    /**
     * Renvoie la carte jouée par l'action
     * @return la carte.
     */
    public Card getPlayedCard(){return playedCard;}

    /**
     * Type de l'action //TODO DELETE
     * @return le type
     */
    public ActionType getType(){
        return this.type;
    }


    /**
     *
     * @return index de la carte choisie par l'IA
     */
    public int getIndexOfCard() {
        return indexOfCard;
    }

}
