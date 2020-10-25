package cucumber;

import commun.cost.ICost;
import commun.cost.MaterialCost;
import commun.effect.ChoiceMaterialEffect;
import commun.effect.EffectList;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import io.cucumber.java8.En;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CucumberCostTest implements En{
    EffectList effects = new EffectList();
    ICost cost;
    ArrayList<Material> materialList = new ArrayList<>();


    public CucumberCostTest(){

        ParameterType("material","bois|argile|minerai|pierre|papyrus|tissu|verre",(String value)->
        {
            switch(value){
                case "bois":
                    return MaterialType.WOOD;
                case "argile":
                    return MaterialType.CLAY;
                case "minerai":
                    return MaterialType.ORES;
                case "pierre":
                    return MaterialType.STONE;
                case "papyrus":
                    return MaterialType.PAPYRUS;
                case "tissu":
                    return MaterialType.FABRIC;
                case "verre":
                    return MaterialType.GLASS;
                default :
                    throw new Exception("Mauvaise ressource");
            }
        });

        Given("j'ai selectionné une carte bâtiment dans le deck qui coûte {int} de {material}", (Integer matCost,MaterialType mat) ->
        {
            materialList.add(new Material(mat,matCost));
        });
        Given("{int} de {material}", (Integer matCost,MaterialType mat) ->
        {
            materialList.add(new Material(mat,matCost));
        });
        When("j'ai actuellement une carte qui produit {int} {material}", (Integer woodNumber, MaterialType mat) -> {
            effects.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(mat,woodNumber))));
        });
        When("j'ai actuellement une carte qui produit {int} {material} ou {int} {material}", (Integer matNumber, MaterialType mat,Integer matNumber2, MaterialType mat2) -> {
            effects.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(mat,matNumber),new Material(mat2,matNumber2))));
        });

        Then("la construction doit être effectuée", () -> {
            Material[] mats = new Material[materialList.size()];
            materialList.toArray(mats);
            cost = new MaterialCost(mats);
            boolean canBuy = cost.canBuyCard(effects);
            assertTrue(canBuy);
        });
        Then("la construction ne doit pas être effectuée", () -> {
            Material[] mats = new Material[materialList.size()];
            materialList.toArray(mats);
            cost = new MaterialCost(mats);
            boolean canBuy = cost.canBuyCard(effects);
            assertFalse(canBuy);
        });

    }


}
