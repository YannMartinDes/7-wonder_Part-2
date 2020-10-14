package servergame.engine;

import commun.effect.EffectList;
import commun.effect.IEffect;
import commun.effect.ScientificType;
import commun.effect.guild.ScientistsGuildEffect;
import commun.player.Player;
import log.ConsoleColors;
import log.GameLogger;
import servergame.player.PlayerController;


import java.util.LinkedList;
import java.util.List;

/**
 * permet de gerer si necessaire l'effet Guilde des Scientifique
 */
public class ScientistsGuildAction {
    private List<PlayerController> allPlayers;

    public ScientistsGuildAction(List<PlayerController> allPlayers){
        this.allPlayers = allPlayers;
    }

    public void useScientistsGuildEffect(){
        for(PlayerController playerController:allPlayers){
            Player player = playerController.getPlayer();
            List<ScientistsGuildEffect> effects = selectScientistsGuildEffect(player.getWonderBoard().getAllEffects());
            if(effects.size()>0){
                GameLogger.getInstance().log("Le joueur " + player.getName() + " a " + effects.size() + " Guilde des Scientifiques.");
                for(ScientistsGuildEffect effect : effects)
                {
                    ScientificType type = playerController.useScientificsGuildEffect(player.getWonderBoard());
                    GameLogger.getInstance().logSpaceAfter("Il a choisie de prendre " + type.getName() + ".", ConsoleColors.ANSI_GREEN);
                    effect.setSelectedScientificType(type);
                }
            }
        }
    }

    private List<ScientistsGuildEffect> selectScientistsGuildEffect(EffectList allEffect){
        List<ScientistsGuildEffect> effects = new LinkedList<>();
        for(IEffect effect : allEffect){
            if (effect==null) continue;
            if(effect.getScientistsGuild()!=null) effects.add(effect.getScientistsGuild());
        }
        return effects;
    }
}
