package cucumber;

import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.cost.ICost;
import commun.cost.MaterialCost;
import commun.effect.IEffect;
import commun.effect.VictoryPointEffect;

import commun.material.Material;
import commun.material.MaterialType;
import io.cucumber.java8.En;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CucumberCardTest implements En{
    Deck deck;
    Card card;

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

        When("j'ai choisi une carte {string} de l'age {int} qui s'appelle {string} qui rapporte {int} {string} et qui coûte {int} {string}",
                (String type,Integer age,String name, Integer gain, String typeGain, Integer costNumber, String cost) ->{
            CardType cardType = null;
            IEffect effect = null;
            ICost finalCost = null;

            if(type.equals("BATIMENT CIVIL"))
                cardType = CardType.CIVIL_BUILDING;

            if(typeGain.equals("POINTS VICTOIRE"))
                effect = new VictoryPointEffect(gain);

            if(cost.equals("PIERRE"))
                finalCost = new MaterialCost(new Material(MaterialType.STONE,costNumber));

            card = new Card(name,cardType,effect,age,finalCost);
            deck.addCard(card);
        });

        Then("la carte est bien ajoutée à ma merveille", () ->{
            assertTrue(deck.getCard(deck.getLength()-1).equals(card));
        });
    }

}
