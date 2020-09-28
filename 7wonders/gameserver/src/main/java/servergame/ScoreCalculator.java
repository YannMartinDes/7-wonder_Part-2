package servergame;

import commun.player.Player;
import commun.wonderboard.WonderBoard;
import log.GameLogger;
import java.util.*;


public class ScoreCalculator {

    /**
     *
     * @param wonderBoard La merveille qui va me permettre de recupérer les cartes à fin de pouvoir calculer le score.
     * @return Retourne le score final
     */
    public Integer getScore(WonderBoard wonderBoard)
    {
        if (wonderBoard == null)
            return 0;
        int score = 0;
        for (int i = 0 ; i < wonderBoard.getBuilding().getLength() ; i++)
        {
            score +=  wonderBoard.getBuilding().getCard(i).getCardEffect().getScore();
        }
        return score;
    }

    public List<Player> computeFinalScore(List<Player> players)
    {
        List<Player> copyPlayer = new ArrayList<Player>();
        copyPlayer.addAll(players);
        for(Player player: copyPlayer){
            player.setFinalScore(getScore(player.getWonderBoard()));
        }
        Collections.sort(copyPlayer, Collections.reverseOrder());
        return copyPlayer;
    }


    /**
     * Cette methode permet d'afficher le classement des joueurs
     * @param allPlayers List des joueurs
     *
     */
    public void printRanking(List<Player> allPlayers)
    {
        List<Player> ranking = computeFinalScore(allPlayers);
        for (int i=0; i < ranking.size(); i++ ) {//0
            GameLogger.log((i+1) + " : " + ranking.get(i).getName() + " avec un score de "+ ranking.get(i).getFinalScore());
        }
        GameLogger.logSpaceBefore("Le vainqueur est : "+ ranking.get(0).getName());
    }
}