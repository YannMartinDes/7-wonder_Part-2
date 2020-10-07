package commun.cost.solver;

import commun.effect.ChoiceMaterialEffect;
import commun.effect.EffectList;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaterialsCostSolverTest {

    MaterialsCostSolver solver;

    @Test
    void allSoluceFindBase() {
        //on vas voir que quand on ne peut pas construire le cout en sorti est le meme que celui attendue
        //On a pas de production et le cout est pas null (on peut pas construire)
        Material[] cost = new Material[3];
        cost[0] = new Material(MaterialType.WOOD,1);
        cost[1] = new Material(MaterialType.STONE,2);
        cost[2] = new Material(MaterialType.FABRIC,3);


        solver = new MaterialsCostSolver(cost,new EffectList());
        assertFalse(solver.canBuyCard());

        //on doit avoir que une seul solution car comme on a pas de ressource il manque quoi qu'il arrive les
        // ressource que l'on a mis en entrée
        List<MaterialsCostArray> allSoluce = solver.allSoluceFind();
        assertEquals(1,allSoluce.size());
        MaterialsCostArray uniqueCost = allSoluce.get(0);
        for(MaterialType type : MaterialType.values()){ // on retrouve bien le meme valeur que l'on a donner
            if(type.equals(MaterialType.WOOD) ) assertEquals(1,uniqueCost.get(type));
            else if(type.equals(MaterialType.STONE) ) assertEquals(2,uniqueCost.get(type));
            else if(type.equals(MaterialType.FABRIC) ) assertEquals(3,uniqueCost.get(type));
            else assertEquals(0,uniqueCost.get(type));
        }

    }

    @Test
    void allSoluceFindSimpleMaterial() {
        //on a deux ressource normale on a une seul solution carpas de choix multiple
        Material[] cost = new Material[3];
        cost[0] = new Material(MaterialType.WOOD,1);
        cost[1] = new Material(MaterialType.STONE,2);
        cost[2] = new Material(MaterialType.FABRIC,3);


        EffectList playerEffect = new EffectList();
        playerEffect.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.FABRIC,2))));
        playerEffect.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,1))));

        solver = new MaterialsCostSolver(cost,playerEffect);

        //on doit avoir que une seul solution car comme on a pas de ressource a choix
        //on retire les ressource que l'on a mis en entrée soir 2 de tissu et 1 de pierre
        // donc la solution est 1 au bois, a la pierre et au tissu et 0 au autre
        List<MaterialsCostArray> allSoluce = solver.allSoluceFind();
        assertEquals(1,allSoluce.size());
        System.out.println(Arrays.toString(allSoluce.get(0).getCost()));
        MaterialsCostArray uniqueCost = allSoluce.get(0);
        for(MaterialType type : MaterialType.values()){ // on retrouve bien le meme valeur que l'on a donner
            if(type.equals(MaterialType.WOOD) ) assertEquals(1,uniqueCost.get(type));
            else if(type.equals(MaterialType.STONE) ) assertEquals(1,uniqueCost.get(type));
            else if(type.equals(MaterialType.FABRIC) ) assertEquals(1,uniqueCost.get(type));
            else assertEquals(0,uniqueCost.get(type));
        }

    }

    @Test
    void allSoluceFindChoiceMaterial() {
        //on a deux ressource normale on a une seul solution carpas de choix multiple
        Material[] cost = new Material[3];
        cost[0] = new Material(MaterialType.WOOD,1);
        cost[1] = new Material(MaterialType.STONE,2);
        cost[2] = new Material(MaterialType.FABRIC,3);


        EffectList playerEffect = new EffectList();
        playerEffect.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.FABRIC,2)))); // il nous rest 1,2,1
        playerEffect.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,1))));// il nous rest 1,1,1
        playerEffect.add(new ChoiceMaterialEffect(new ChoiceMaterial(
                new Material(MaterialType.STONE,1),
                new Material(MaterialType.WOOD,1))
        )); //il nous reste 0,1,1 ou 1,0,1 (2 choix)

        solver = new MaterialsCostSolver(cost,playerEffect);

        //on a donc 2 choix 0,1,1 ou 1,0,1 (bois,pierre,tissu
        List<MaterialsCostArray> allSoluce = solver.allSoluceFind();
        assertEquals(2,allSoluce.size());
        int[] posibility1 = new int[MaterialType.values().length];
        posibility1[MaterialType.STONE.getIndex()]=1;
        posibility1[MaterialType.FABRIC.getIndex()]=1;
        int[] posibility2 = new int[MaterialType.values().length];
        posibility2[MaterialType.WOOD.getIndex()]=1;
        posibility2[MaterialType.FABRIC.getIndex()]=1;

        for(MaterialsCostArray current: allSoluce){
            assertTrue(Arrays.equals(posibility1,current.getCost())||Arrays.equals(posibility2,current.getCost())); //on a les 2 possibilité
        }
        assertFalse(Arrays.equals(allSoluce.get(1).getCost(),allSoluce.get(0).getCost())); //cela prouve que les 2 solution precedentes on bien etait trouver

        //peut importe l'ordre des carte en entrèe on a le meme resultat:
        playerEffect = new EffectList();
        playerEffect.add(new ChoiceMaterialEffect(new ChoiceMaterial(
                new Material(MaterialType.STONE,1),
                new Material(MaterialType.WOOD,1))
        ));
        playerEffect.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.FABRIC,2))));
        playerEffect.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,1))));

        //on solve de nouveau
        solver = new MaterialsCostSolver(cost,playerEffect);

        //on a donc 2 choix 0,1,1 ou 1,0,1 (bois,pierre,tissu) soit le meme resultat que le precedent
        allSoluce = solver.allSoluceFind();

        assertEquals(2,allSoluce.size());
        for(MaterialsCostArray current: allSoluce){
            assertTrue(Arrays.equals(posibility1,current.getCost())||Arrays.equals(posibility2,current.getCost())); //on a les 2 possibilité
        }
        assertFalse(Arrays.equals(allSoluce.get(1).getCost(),allSoluce.get(0).getCost())); //cela prouve que les 2 solution precedentes on bien etait trouver

    }

    private EffectList infinitRessourceList(){
        EffectList playerEffect = new EffectList();
        for(MaterialType type : MaterialType.values()){
            playerEffect.add(new ChoiceMaterialEffect(new ChoiceMaterial(new Material(type,100))));
        }
        return playerEffect;
    }

    @Test
    void soluceBuyNeighbours() {
        EffectList leftNeighbours = infinitRessourceList();
        EffectList rightNeighbours = infinitRessourceList();

        //on a deux ressource normale on a une seul solution carpas de choix multiple
        Material[] cost = new Material[1];
        cost[0] = new Material(MaterialType.WOOD,1);

        solver = new MaterialsCostSolver(cost,new EffectList()); //on a pas de ressource donc il nous manque a acheter 1 bois
        assertEquals(1,solver.allSoluceFind().size());

        //on dois acheter 1 bois on a 2 voisin on a donc 2 solution,
        //acheter a droite ou acheter a gauche
        List<MaterialsCostArray[]> allSoluce = solver.soluceBuyNeighbours(leftNeighbours,rightNeighbours);

        assertEquals(2,allSoluce.size()); //soit droite soit gauche

        int[] p1 = new int[MaterialType.values().length];
        p1[MaterialType.WOOD.getIndex()]=1;
        int[] p2 = new int[MaterialType.values().length];
        //nos deux posibilité payée du bois a gauche ou a droite
        MaterialsCostArray[] posibility1 = new MaterialsCostArray[]{new MaterialsCostArray(p1),new MaterialsCostArray(p2)};
        MaterialsCostArray[] posibility2 = new MaterialsCostArray[]{new MaterialsCostArray(p2),new MaterialsCostArray(p1)};

        assertEquals(2,allSoluce.size());
        for(MaterialsCostArray[] current: allSoluce){
            //on a bien l'une des 2 solution
            assertTrue((Arrays.equals(posibility1[0].getCost(),current[0].getCost())
                        &&Arrays.equals(posibility1[1].getCost(),current[1].getCost()))
                        ||(Arrays.equals(posibility2[0].getCost(),current[0].getCost())
                        &&Arrays.equals(posibility2[1].getCost(),current[1].getCost())));
        }
        assertFalse(Arrays.equals(allSoluce.get(1)[0].getCost(),allSoluce.get(0)[0].getCost())); //cela prouve que les 2 solution precedentes on bien etait trouver
        

    }

    @Test
    void canBuyCard() {
        //Cas de base le cout est null et on a pas de production
        solver = new MaterialsCostSolver(new Material[0],new EffectList());
        assertTrue(solver.canBuyCard());

        //On a pas de production et le cout est pas null (on peut pas construire)
        Material material = new Material(MaterialType.WOOD,1);
        solver = new MaterialsCostSolver(new Material[]{material},new EffectList());
        assertFalse(solver.canBuyCard());


    }
}