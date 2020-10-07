package commun.wonderboard;

import java.util.LinkedList;
import java.util.List;

/**
 * Represente les point
 */
public class BattlePoint {
    private int conflictPoints = 0;

    private int victoryToken = 0;

    public BattlePoint(){ }

    public int getConflictPoints ()
    { return conflictPoints; }

    public int getVictoryToken(){
        return victoryToken;
    }


    /** Ajouter des points de conflits miitaires
     * @param conflictPoints Nombre de points a ajouter */
    public void addToken (int conflictPoints)
    {
        this.conflictPoints += conflictPoints;
        // si on a gagner on a un jeton de victoire en plus (victoire = conflict point positive)
        if(conflictPoints>0) victoryToken++;
    }


}
