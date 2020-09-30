package servergame.card;

import commun.card.Card;
import commun.card.Deck;

import java.util.ArrayList;
import java.util.Random;

public class CardManager {
    private CardFactory cardFactory;//Créateur de cartes.
    private Random r;
    private ArrayList<Deck> hands; //Les paquets de cartes.
    private final Deck discarding; //defausse
    private int numberPlayer;
    private static int maxNbCard = 7;

    public CardManager(int numberPlayer){
        this.numberPlayer = numberPlayer;
        cardFactory = new CardFactory();
        r = new Random();
        hands = new ArrayList<Deck>();
        discarding = new Deck();
    }

    public void setHands(ArrayList<Deck> hands){
        this.hands = hands;
    }

    public ArrayList<Deck> getHands(){
        return hands;
    }
    
    public Deck getHand(int index) {
    	return hands.get(index);
    }

    /**
     * Effectue une rotation sur la liste de deck.
     * @param isClockwise : le sens de rotation
     */
    public void rotateHands(boolean isClockwise){
        rotateHands(isClockwise,hands);
    }

    /**
     * Effectue une rotation sur la liste de deck.
     * @param isClockwise : le sens de rotation
     * @param hands : la liste de deck.
     */
    public void rotateHands(boolean isClockwise, ArrayList<Deck> hands){
        Deck temp;
        int length = hands.size();

        if(length > 1){//Deux éléments pour une rotation.
            if(isClockwise){//Rotation horaire.
                temp = hands.get(length-1);

                for(int i = length-1;i > 0; i--){
                    hands.set(i, hands.get(i-1));//On décale
                }
                hands.set(0,temp);//Le premier deviens le dernier.
            }
            else{//Rotation anti-horaire.
                temp = hands.get(0);

                for(int i = 0;i < length-1; i++){
                    hands.set(i, hands.get(i+1));//On décale
                }
                hands.set(length-1,temp);//Le dernier deviens le premier.
            }
        }
    }


    /**
     * Distribue les cartes en paquet égaux (2 cartes pour le moment)
     * @param ageNumber : age en cours.
     */
    public void createHands(int ageNumber){
        Deck ageDeck;
        ArrayList<Deck> hands = new ArrayList<Deck>();

        switch (ageNumber){
            case 1:
                ageDeck = cardFactory.AgeOneCards();
                break;

            default:
                ageDeck = null;
        }

        if(ageDeck != null){
            int nbCardByPlayer = ageDeck.getLength()/numberPlayer;
            for(int i = 0; i < numberPlayer; i++){//On créer un deck pour chaque joueurs.
                hands.add(_createRandomHand(ageDeck,nbCardByPlayer));
            }
            this.hands = hands;
        }
    }

    /**
     * Créer un main de cartes au hasard.
     * @param ageDeck : le deck de l'age en cours.
     * @return la main (Deck)
     */
    private Deck _createRandomHand(Deck ageDeck,int nbCardByPlayer){
        Deck hand = new Deck();
        for(int i =0; i<nbCardByPlayer && i < maxNbCard ;i++){
            hand.addCard(_getRandomCard(ageDeck)); //On tire une carte au hasard.
        }

        return hand;
    }

    /**
     * Permet d'obtenir une carte au hasard et la retire du deck de l'age.
     * @param ageDeck : le deck de l'age en cours.
     * @return la carte.
     */
    private Card _getRandomCard(Deck ageDeck){
        int index = r.nextInt(ageDeck.getLength());

        Card card = ageDeck.getCard(index);//On récupère la carte.
        ageDeck.removeCard(index);//On la retire du paquet.

        return card;
    }

    /**
     * L'age est fini si les mains on un nombre de carte
     * inferieur ou egale a 1 carte (normalement impossible inferieur a 1)
     * @return true -> fin de l'age , false -> pas la fin
     */
    public boolean isEndAge(){
        return hands.get(0).getLength()<=1;
    }

    public Deck getDiscarding() {
        return discarding;
    }
}
