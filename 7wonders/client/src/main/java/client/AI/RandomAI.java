package client.AI;

import commun.card.Deck;

import java.util.Random;

public class RandomAI implements client.iainterface.AI{
    Random r;

    public RandomAI(){
        r = new Random();
    }

    public int chooseCardFromDeck(Deck deck) {
        return r.nextInt(deck.getLength());
    }
}
