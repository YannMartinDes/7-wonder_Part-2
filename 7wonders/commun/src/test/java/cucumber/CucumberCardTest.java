package cucumber;

import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.cost.ICost;
import commun.cost.MaterialCost;
import commun.effect.AddingMaterialEffet;
import commun.effect.EffectList;
import commun.effect.VictoryPointEffect;
import commun.material.Material;
import commun.material.MaterialType;
import io.cucumber.java8.En;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CucumberCardTest implements En{
    EffectList effects;
    ICost cost;

    public CucumberCardTest(){
        Given("j'ai selectionner une carte bâtiment dans le deck qui coûte {int} de bois", (Integer woodCost) ->
        {
            cost = new MaterialCost(new Material(MaterialType.WOOD,woodCost));

        });
        When("j'ai actuellement une carte qui produit {int} bois", (Integer woodNumber) -> {
            effects = new EffectList();
            effects.add(new AddingMaterialEffet(new Material(MaterialType.WOOD,woodNumber)));
        });

        Then("la construction dois être effectuée", () -> {
            boolean canBuy = cost.canBuyCard(effects);
            assertEquals(true,canBuy);
        });
        Then("la construction dois ne dois pas être effectuée", () -> {
            boolean canBuy = cost.canBuyCard(effects);
            assertEquals(false,canBuy);
        });


    }

}
