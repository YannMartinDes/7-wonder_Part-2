package effect;

import commun.effect.*;
import commun.effect.guild.ScientistsGuildEffect;
import commun.effect.guild.StrategistsGuild;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EffectListTest {
    EffectList effectList ;
    @BeforeEach
    void init ()
    {
        effectList = new EffectList();

    }

    @Test
    void testIsStrategistsGuild ()
    {
        effectList.add(new StrategistsGuild());
        effectList.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD,1))));

        assertEquals(effectList.get(0).iSStrategistsGuild(), true);
        assertEquals(effectList.get(1).iSStrategistsGuild(), false);
    }

    /**
     * On teste si la methode renvois une liste qui ne contient que les ChoiceMateriel avec 2 ou + Materiel
     */
    @Test
    void filterChoiceMaterialEffectTest() {
        ChoiceMaterialEffect choiceMaterial1 = new ChoiceMaterialEffect( new ChoiceMaterial(new Material(MaterialType.STONE,1)));
        ChoiceMaterialEffect choiceMaterial2 = new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.CLAY,2),new Material(MaterialType.WOOD,1)));
        ChoiceMaterialEffect choiceMaterial3 = new ChoiceMaterialEffect(new ChoiceMaterial());

        effectList.add(choiceMaterial1); //ne doit pas contenir ça
        effectList.add(choiceMaterial2); //doit contenir ça
        effectList.add(new ScientificEffect(ScientificType.LITERATURE));
        effectList.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD,1))));
        effectList.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,1),new Material(MaterialType.CLAY,2))));
        effectList.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,1),new Material(MaterialType.CLAY,2),new Material(MaterialType.WOOD,1))));
        effectList.add(new MilitaryEffect(1));
        //On lance la methode
        EffectList newEffectList = effectList.filterChoiceMaterialEffect();

        //tests
        assertEquals(1,choiceMaterial1.getMaterialLength());
        assertEquals(0,choiceMaterial3.getMaterialLength());
        assertEquals(newEffectList.size() , 2);
        assertFalse(newEffectList.contains(choiceMaterial1));
        assertTrue(newEffectList.contains(choiceMaterial2));

    }
    /**
     * On teste si la methode renvois une liste qui ne contient que les carte avec 1 materiel comme effet
     */
    @Test
    void filterMaterialEffectTest(){
        ChoiceMaterialEffect choiceMaterial1 = new ChoiceMaterialEffect( new ChoiceMaterial(new Material(MaterialType.STONE,1)));
        ChoiceMaterialEffect choiceMaterial2 = new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.CLAY,2),new Material(MaterialType.WOOD,1)));
        ChoiceMaterialEffect choiceMaterial3 = new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD,5)));
        effectList.add(choiceMaterial1); //doit contenir ça
        effectList.add(choiceMaterial2);  //ne doit pas contenir ça
        effectList.add(new ScientificEffect(ScientificType.LITERATURE));
        effectList.add(choiceMaterial3);
        effectList.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,1),new Material(MaterialType.CLAY,2),new Material(MaterialType.WOOD,1))));
        effectList.add(new MilitaryEffect(1));
        //On lance la methode
        EffectList newEffectList = effectList.filterMaterialEffect();

        //tests
        assertEquals(newEffectList.size() , 2);
        assertTrue(newEffectList.contains(choiceMaterial1));
        assertFalse(newEffectList.contains(choiceMaterial2));
        assertTrue(newEffectList.contains(choiceMaterial3));

    }
}
