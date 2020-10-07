package commun.cost.solver;

import commun.effect.EffectList;
import commun.material.Material;
import commun.material.MaterialType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaterialsCostSolverTest {

    MaterialsCostSolver solver;

    @Test
    void allSoluceFind() {
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
    void soluceBuyNeighbours() {
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