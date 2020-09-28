package cucumber;

import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.effect.VictoryPointEffect;

import io.cucumber.java8.En;



import static org.junit.jupiter.api.Assertions.assertEquals;

public class CucumberCardTest implements En{
    Deck deck;

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


    }

}
