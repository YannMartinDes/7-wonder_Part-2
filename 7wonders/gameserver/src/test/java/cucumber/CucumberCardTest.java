package cucumber;

import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.effect.VictoryPointEffect;
import io.cucumber.java8.En;
import servergame.card.CardManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CucumberCardTest implements En{
    Deck deck;
    Card card;
    CardManager cardManager;
    int age;
    int nbPlayer;
    int nbCard;
    ArrayList<Deck> hand = new ArrayList<>();

    public CucumberCardTest(){
        Given("j'ai {int} cartes avec ma merveille", (Integer number) ->
        {
            deck = new Deck();

            for(int i=0; i<number;i++){
                deck.addCard(new Card("EmptyCard", CardType.CIVIL_BUILDING,new VictoryPointEffect(0),1,null));
            }
        });
        When("j'ajoute {int} cartes", (Integer numberAdded) -> {
            for(int i=0; i<numberAdded;i++){
                deck.addCard(new Card("EmptyCard", CardType.CIVIL_BUILDING,new VictoryPointEffect(0),1,null));
            }
        });
        Then("je dois avoir {int} cartes avec ma merveille", (Integer size) -> {
            assertEquals(size,deck.getLength());
        });

        Given("j'ai {int} joueurs avec chacun {int} cartes",(Integer nbPlayer, Integer nbCard) ->
        {
            cardManager = new CardManager(nbPlayer);
            this.nbPlayer =nbPlayer;
            this.nbCard = nbCard;

            for(int i =0; i<nbPlayer;i++){
                Deck deck = new Deck();

                //Ajout d'un ID unique pour identifier la carte. (testi)
                for(int j=0;j<nbCard;j++) {
                    deck.addCard(new Card("test"+i,CardType.CIVIL_BUILDING,null,1,null));
                }
                hand.add(deck);//Ajout au main.
            }
            ArrayList<Deck> handCopy = new ArrayList<>();
            handCopy.addAll(hand);//COPIE

            cardManager.setHands(handCopy);
        });
        When("chaque joueur passe ses cartes à son voisin lors de l'âge {int}",(Integer age) ->
        {
            this.age = age;
            cardManager.rotateHands(age%2 == 1);
        });
        Then("la rotation des mains s'est bien effectuée",() ->
        {
            if(age%2 == 1){//SENS HORAIRE
                for(int i = 0;i<nbCard;i++){//Derniere deviens premiere.
                    assertEquals(hand.get(hand.size()-1).getCard(i).getName(),
                            cardManager.getHand(0).getCard(i).getName());
                }
                for(int i =0; i<nbPlayer-1;i++){//Decalé vers la droite
                    for(int j=0;j<nbCard;j++) {
                        assertEquals(hand.get(i).getCard(j).getName(),
                                cardManager.getHand(i+1).getCard(j).getName());
                    }
                }
            }
            else{//SENS ANTI-HORAIRE
                for(int i = 0;i<nbCard;i++){//Premiere deviens derniere.
                    assertEquals(hand.get(0).getCard(i).getName(),
                            cardManager.getHand(hand.size()-1).getCard(i).getName());
                }
                for(int i =0; i<nbPlayer-1;i++){//Decale vers la gauche
                    for(int j=0;j<nbCard;j++) {
                        assertEquals(hand.get(i+1).getCard(j).getName(),
                                cardManager.getHand(i).getCard(j).getName());
                    }
                }
            }
        });
    }

}
