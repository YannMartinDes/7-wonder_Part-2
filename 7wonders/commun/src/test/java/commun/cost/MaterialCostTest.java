package commun.cost;

import commun.effect.ChoiceMaterialEffect;
import commun.effect.EffectList;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class MaterialCostTest
{
    private MaterialCost materialCost;
    private Material [] materials;
    private EffectList effects;

    @BeforeEach
    public void init ()
    {
        // Soit 1 de chaque
        // Soit 2 wood + 1 de chaque sauf clay

        this.effects = new EffectList();
        this.effects.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD, 1))));
        this.effects.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE, 1))));
        this.effects.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.ORES, 1))));
        this.effects.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.PAPYRUS, 1))));
        this.effects.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.GLASS, 1))));
        this.effects.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.FABRIC, 1))));
        // Il a un choix ici
        this.effects.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material[] {
                new Material(MaterialType.CLAY, 1),
                new Material(MaterialType.WOOD, 1)
        })));
    }

    @Test
    public void testCanBuyCard ()
    {
//        // Materiaux fixes
//        this.materials = new Material []
//                {new Material(MaterialType.WOOD, 1)};
//        this.materialCost = new MaterialCost(this.materials);
//        assertTrue(this.materialCost.canBuyCard(this.effects));
//
//        this.materials = new Material []
//                {new Material(MaterialType.WOOD, 3)};
//        this.materialCost = new MaterialCost(this.materials);
//        assertFalse(this.materialCost.canBuyCard(this.effects));
//
//        this.materials = new Material []
//                {new Material(MaterialType.PAPYRUS, 2)};
//        this.materialCost = new MaterialCost(this.materials);
//        assertFalse(this.materialCost.canBuyCard(this.effects));
//
//        this.materials = new Material []
//                {
//                        new Material(MaterialType.PAPYRUS, 1),
//                        new Material(MaterialType.WOOD, 1)
//                };
//        this.materialCost = new MaterialCost(this.materials);
//        assertTrue(this.materialCost.canBuyCard(this.effects));
//
//        // Materiaux a choix
//        this.materials = new Material []
//                {new Material(MaterialType.WOOD, 2)};
//        this.materialCost = new MaterialCost(this.materials);
//        assertTrue(this.materialCost.canBuyCard(this.effects));
//
//        this.materials = new Material []
//                {
//                        new Material(MaterialType.WOOD, 2),
//                        new Material(MaterialType.CLAY, 1)
//                };
//        this.materialCost = new MaterialCost(this.materials);
//        assertFalse(this.materialCost.canBuyCard(this.effects));
//
//        this.materials = new Material []
//                {
//                        new Material(MaterialType.WOOD, 1),
//                        new Material(MaterialType.CLAY, 1),
//                        new Material(MaterialType.PAPYRUS, 1)
//                };
//        this.materialCost = new MaterialCost(this.materials);
//        assertTrue(this.materialCost.canBuyCard(this.effects));

        this.materials = new Material []
                {
                        new Material(MaterialType.WOOD, 1),
                        new Material(MaterialType.CLAY, 1),
                        new Material(MaterialType.WOOD, 1)
                };
        this.materialCost = new MaterialCost(this.materials);
        assertFalse(this.materialCost.canBuyCard(this.effects));
    }
}
