package cucumber;

import commun.cost.ICost;
import commun.cost.MaterialCost;
import commun.effect.ChoiceMaterialEffect;
import commun.effect.EffectList;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import io.cucumber.java8.En;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CucumberCostTest implements En{
    EffectList effects = new EffectList();
    ICost cost;

    public CucumberCostTest(){
        Given("j'ai selectionner une carte bâtiment dans le deck qui coûte {int} de bois", (Integer woodCost) ->
        {
            cost = new MaterialCost(new Material(MaterialType.WOOD,woodCost));

        });
        When("j'ai actuellement une carte qui produit {int} bois", (Integer woodNumber) -> {
            effects.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD,woodNumber))));
        });

        When("j'ai actuellement une carte qui produit {int} argile", (Integer clayNumber) -> {
            effects.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.CLAY,clayNumber))));
        });

        When("j'ai actuellement une carte qui produit {int} minerai", (Integer oresNumber) -> {
            effects.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.ORES,oresNumber))));
        });

        When("j'ai actuellement une carte qui produit {int} pierre", (Integer stoneNumber) -> {
            effects.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,stoneNumber))));
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
