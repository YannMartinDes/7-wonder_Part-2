package commun.wonderboard;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class BattlePointTest {
    BattlePoint battlePoint;

    @Test
    void addTokensTest(){
        battlePoint = new BattlePoint();

        //si on a gagné 0 point de conflit militaire
        battlePoint.addToken(0);
        assertEquals(0, battlePoint.getConflictPoints());
        assertEquals(0, battlePoint.getVictoryToken());

        //si on a gagné 1 points de conflit militaire
        battlePoint.addToken(1);
        assertEquals(1, battlePoint.getConflictPoints());
        assertEquals(1, battlePoint.getVictoryToken());

        //si on a gagné 3 points de conflit militaire
        battlePoint.addToken(3);
        assertEquals(4, battlePoint.getConflictPoints());
        assertEquals(2, battlePoint.getVictoryToken());

        //si on a gagné 5 points de conflit militaire
        battlePoint.addToken(5);
        assertEquals(9, battlePoint.getConflictPoints());
        assertEquals(3, battlePoint.getVictoryToken());
    }
}
