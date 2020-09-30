package servergame;

import log.ConsoleColors;
import log.GameLogger;
import servergame.player.Player;

import java.util.*;


public class ScoreCalculator {

    /**
     *
     * @param player La merveille qui va me permettre de recupérer les cartes à fin de pouvoir calculer le score.
     * @return Retourne le score final
     */
    public Integer getScore(Player player)
    {

        if (player.getWonderBoard() == null)
            return 0;
        GameLogger.logSpaceAfter("--- Calcul du score du joueur " + player.getName() + " ---", ConsoleColors.ANSI_GREEN);
        int score = 0;
        int scoreWithCoins = 0;
        for (int i = 0 ; i < player.getWonderBoard().getBuilding().getLength() ; i++)
        {
            score +=  player.getWonderBoard().getBuilding().getCard(i).getCardEffect().getScore();
            GameLogger.log("Le joueur " + player.getName() + " a joué la carte \"" + player.getWonderBoard().getBuilding().getCard(i) + "\"");
            GameLogger.logSpaceAfter("Cette carte lui a fait gagner " + player.getWonderBoard().getBuilding().getCard(i).getCardEffect().getScore() + " points.");
        }
        scoreWithCoins += player.getWonderBoard().getCoin() / 3;
        GameLogger.log("Le joueur "+ player.getName() + " a " + player.getWonderBoard().getCoin() + " pièces.");
        GameLogger.logSpaceAfter("Cela lui rapporte un total de " + scoreWithCoins + " points");


        return score + scoreWithCoins;
    }

    public List<Player> computeFinalScore(List<Player> players)
    {
        List<Player> copyPlayer = new ArrayList<Player>();
        copyPlayer.addAll(players);
        for(Player player: copyPlayer){
            player.setFinalScore(getScore(player)); //les scores des joueurs dans le vrai tableau sont quand même modifiés
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
        GameLogger.log("--- Classement ---",ConsoleColors.ANSI_RED_BOLD_BRIGHT);
        for (int i=0; i < ranking.size(); i++ ) {//0
            GameLogger.log((i+1) + " : " + ranking.get(i).getName() + " avec un score de "+ ranking.get(i).getFinalScore());
        }
        GameLogger.logSpaceBefore("Le vainqueur est : "+ ranking.get(0).getName(),ConsoleColors.ANSI_GREEN_BOLD);
    }
}