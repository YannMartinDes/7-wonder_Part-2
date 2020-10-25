package servergame.score;

import commun.effect.EffectList;
import commun.effect.IEffect;
import commun.player.Player;
import commun.wonderboard.BattlePoint;
import log.ConsoleColors;
import log.GameLogger;

/**
 * permet de calculer le score des bataille
 */
public class BattleScore {

    public int computeScore(Player player){
        int score = 0;
        score += battleTokenPoints(player.getWonderBoard().getBattlePoint(),player.getName());
        score += guildesStrategiesPoints(player.getWonderBoard().getBattlePoint(),player.getWonderBoard().getAllEffects());
        return score;

    }

    private int battleTokenPoints(BattlePoint point , String playerName){
        int conflictsPoints = 0;
        conflictsPoints += point.getConflictPoints();
        GameLogger.getInstance().log("Le joueur " + playerName + " a " + conflictsPoints + " jetons de conflit militaire.");
        if (conflictsPoints != 0) {
            GameLogger.getInstance().logSpaceAfter("Cela lui rapporte un total de " + conflictsPoints + " points.", ConsoleColors.ANSI_GREEN);
        }else{
            GameLogger.getInstance().log("");
        }
        return conflictsPoints;
    }

    private int guildesStrategiesPoints(BattlePoint point, EffectList allEffects){
        boolean isActive = false;
        for(IEffect current: allEffects){
            if(current.iSStrategistsGuild()){
                isActive = true;
                break;
            }
        }
        if(isActive){ //on a la carte guildes strategies on gagne 1 point par jeton de conflit
            GameLogger.getInstance().logSpaceAfter("Il a la carte Guilde des Stratèges, cela lui rapporte " + point.getVictoryToken() + " points pour ces victoires au conflit.", ConsoleColors.ANSI_GREEN);
            return point.getVictoryToken();
        }
        return 0;
    }
}
