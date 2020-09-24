package servergame.card;

import com.sun.istack.internal.NotNull;
import commun.card.Card;
import commun.card.Deck;

import java.util.ArrayList;
import java.util.Random;

public class CardManager {
    private CardFactory cardFactory;//Créateur de cartes.
    private Random r;
    private ArrayList<Deck> hands; //Les paquets de cartes.

    public CardManager(){
        cardFactory = new CardFactory();
        r = new Random();
        hands = new ArrayList<Deck>();
    }

    public void setHands(ArrayList<Deck> hands){
        this.hands = hands;
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

                for(int i = 0;i < length-1; i++){
                    hands.set(i+1, hands.get(i));//On décale
                }
                hands.set(0,temp);//Le premier deviens le dernier.
            }
            else{//Rotation anti-horaire.
                temp = hands.get(0);

                for(int i = length-1;i > 0; i--){
                    hands.set(i-1, hands.get(i));//On décale
                }
                hands.set(length-1,temp);//Le dernier deviens le premier.
            }
        }
    }


    /**
     * Distribue les cartes en paquet égaux (2 cartes pour le moment)
     * @param ageNumber : age en cours.
     */
    public ArrayList<Deck> createHands(int ageNumber){
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
            for(int i = 0; i < 4; i++){//On créer un deck pour chaque joueurs. TODO nb de joueur variable.
                hands.add(_createRandomHand(ageDeck));
            }

            return hands;
        }
        return null;
    }

    /**
     * Créer un main de cartes au hasard.
     * @param ageDeck : le deck de l'age en cours.
     * @return la main (Deck)
     */
    private Deck _createRandomHand(@NotNull Deck ageDeck){
        Deck hand = new Deck();

        for(int i =0; i<2;i++){//TODO ajouter un nombre de cartes en fonction du nombre de joueur (2 pour le moment)
            hand.addCard(_getRandomCard(ageDeck));//On tire une carte au hasard.
        }

        return hand;
    }

    /**
     * Permet d'obtenir une carte au hasard et la retire du deck de l'age.
     * @param ageDeck : le deck de l'age en cours.
     * @return la carte.
     */
    private Card _getRandomCard(@NotNull Deck ageDeck){
        int index = r.nextInt(ageDeck.getLength());

        Card card = ageDeck.getCard(index);//On récupère la carte.
        ageDeck.removeCard(index);//On la retire du paquet.

        return card;
    }
}
