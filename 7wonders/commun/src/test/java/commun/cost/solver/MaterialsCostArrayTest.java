package commun.cost.solver;

import commun.material.MaterialType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaterialsCostArrayTest {

    MaterialsCostArray cost;
    int[] allMaterialsCost;

    @BeforeEach
    void init(){
        allMaterialsCost = new int[MaterialType.values().length];
    }


    @Test
    void testClone() {
        for(int i = 0; i<allMaterialsCost.length; i++){
            allMaterialsCost[i] = i;
        }
        cost = new MaterialsCostArray(allMaterialsCost);
        cost.computeTotalCost();
        MaterialsCostArray clone = cost.clone();
        //on a bien les meme valeur
        for(MaterialType type: MaterialType.values()){
            assertEquals(cost.get(type),clone.get(type));
        }
        //on change les valeur du clone
        for(MaterialType type: MaterialType.values()){
            cost.put(type,cost.get(type)+1000);
        }
        //on a bien changer les valeur uniquement du clone
        for(MaterialType type: MaterialType.values()){
            assertNotEquals(cost.get(type),clone.get(type));
        }
    }

    @Test
    void putAndGet() {
        for(int i = 0; i<allMaterialsCost.length; i++){
            allMaterialsCost[i] = 0;
        }
        cost = new MaterialsCostArray(allMaterialsCost);
        cost.computeTotalCost();

        //on get bien les valeur comme on les a initialiser
        for(MaterialType type: MaterialType.values()){
            assertEquals(allMaterialsCost[type.getIndex()],cost.get(type));
        }

        //on put des valeur
        int i = 1;
        for(MaterialType type: MaterialType.values()){
            cost.put(type,i);
            i++;
        }

        //on a les meme valeur dans notre tableau et on get les bonne valeur
        i = 1;
        for(MaterialType type: MaterialType.values()){
            assertEquals(allMaterialsCost[type.getIndex()],cost.get(type)); //le tableau est bien modifier
            assertEquals(i,cost.get(type)); //on a bien les bonne valeur
            i++;
        }

        //les valeur put ne peuvent pas etre negative (on test pour plusieur valeur negative)
        i = -1;
        for(MaterialType type: MaterialType.values()){
            cost.put(type,i);
            assertEquals(cost.get(type),0);
            assertTrue(i<0);
            i--;
        }




    }

    @Test
    void sub() {
        cost = new MaterialsCostArray(allMaterialsCost);
        cost.computeTotalCost();

        //on get bien les valeur comme on les a initialiser
        for(MaterialType type: MaterialType.values()){
            cost.sub(type,999); // on soustrait
            //on a bien les valeur limiter a 0
            assertEquals(allMaterialsCost[type.getIndex()],cost.get(type));
        }

        //on put des valeur et on soustrait 1 a chaque fois
        int i = 1;
        for(MaterialType type: MaterialType.values()){
            cost.put(type,i);
            cost.sub(type,3);
            i++;
        }


        //on a les valeur avec -3 avec 0 au minimum
        i = 1;
        for(MaterialType type: MaterialType.values()){
            if(i-3<0) assertEquals(0,cost.get(type));
            else assertEquals(i-3,cost.get(type));
            i++;
        }
    }


    @Test
    void contains() {
        cost = new MaterialsCostArray(allMaterialsCost);
        cost.computeTotalCost();

        // on ajoute 2 effet le bois et la pierre au cout
        //contient le type si le cout de ce type n'est pas null
        cost.put(MaterialType.WOOD,2);
        cost.put(MaterialType.STONE,1);

        for(MaterialType type: MaterialType.values()){
            if(type.equals(MaterialType.WOOD)||type.equals(MaterialType.STONE)) assertTrue(cost.contains(type));
            else assertFalse(cost.contains(type));
        }

    }

    @Test
    void testEquals() {
        int[] costArray = new int[MaterialType.values().length];
        costArray[1] = 8;
        costArray[4] = 4;
        allMaterialsCost[1] = 8;
        allMaterialsCost[4] = 4;

        cost = new MaterialsCostArray(allMaterialsCost);
        cost.computeTotalCost();

        MaterialsCostArray cost2 = new MaterialsCostArray(costArray);
        cost2.computeTotalCost();
        //les 2 sont equivalent car meme prix pour chaque ressource
        assertTrue(cost.equals(cost2));
        assertTrue(cost2.equals(cost));

        //null ou un autre object renvoie false
        assertFalse(cost.equals(null));
        assertFalse(cost.equals(new Object()));

        //on change il on plus les meme coup
        allMaterialsCost[0] = 1;
        assertFalse(cost.equals(cost2));
        assertFalse(cost2.equals(cost));

        //ce n'est pas les meme si c'est pas les meme ressource attendue (meme si la somme totale est la meme)
        costArray[2] = 1;
        assertFalse(cost.equals(cost2));
        assertFalse(cost2.equals(cost));


    }



    @Test
    void itsDone() {
        cost = new MaterialsCostArray(allMaterialsCost);
        cost.computeTotalCost();

        //le cout totale doit etre egale a 0
        assertTrue(cost.itsDone());

        cost.put(MaterialType.STONE,1);

        //c'est pas fini car la somme de tout les cout n'est pas 0
        assertFalse(cost.itsDone());
    }

    /**
     * Fonction cominbatoire
     * @param input le tableau que l'on veut connaitre le nombre de combie
     * @return le nombre de combinaison possible
     */
    int calculeCombinaisonNumber(int[] input){
        int nbCombi = 1;
        for(int i =0; i<input.length;i++){
            nbCombi*=(input[i]+1);
        }
        return nbCombi;
    }
    @Test
    void combinaison() {
        //cas des combinaison ou tout les element sont au meme valeur max
        int size = 5;
        int valueMax = 4;
        int[] costArray = new int[size];
        for(int i = 0; i<size; i++){
            costArray[i] = valueMax;
        }

        cost = new MaterialsCostArray(costArray);

        List<MaterialsCostArray> allCombi =  cost.combinaison();


        assertEquals(Math.pow(valueMax+1,size),allCombi.size());


        //cas ou les combinaison on leur propre valeur max
        for(int i =0; i<valueMax; i++){
            for(int j = 0; j<size; j++){
                costArray[i] = i;
                allCombi = cost.combinaison();

                //on verifie que dans tout les cas on a bien les bon nombre de combinaison
                assertEquals(calculeCombinaisonNumber(costArray),allCombi.size());
            }
        }


    }

    @Test
    void subNewCostArray() {
        int[] result = new int[MaterialType.values().length];
        int[] subArray = new int[MaterialType.values().length];

        subArray[0] = 3;
        subArray[1] = 4;
        allMaterialsCost[0] = 8;
        allMaterialsCost[1] = 3;
        cost = new MaterialsCostArray(allMaterialsCost);
        result[0] = 5; //8-3 = 5
        result[1] = 0; //3-4=-1 -> 0 car pas de cout negative

        //on a bien le resulta de la soustraction attendue
        assertTrue(new MaterialsCostArray(result).equals(cost.subNewCostArray(new MaterialsCostArray(subArray))));



    }
}