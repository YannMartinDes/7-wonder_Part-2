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
        Given("J'ai selectionner une carte batiment dans le deck qui coute {int} de bois", (Integer woodCost) ->
        {
            cost = new MaterialCost(new Material(MaterialType.WOOD,woodCost));

        });
        When("j'ai actuellement une carte de {int} bois", (Integer woodNumber) -> {
            effects = new EffectList();
            effects.add(new AddingMaterialEffet(new Material(MaterialType.WOOD,woodNumber)));
        });

        //TODO voir commun créer les type personaliser pour remplacer 0 et 1
        Then("La construction dois etre {int} avec 0 refuser et 1 accepter", (Integer bool) -> {
            boolean canBuy = cost.canBuyCard(effects);
            assertEquals(bool==1,canBuy);
        });


    }

}
