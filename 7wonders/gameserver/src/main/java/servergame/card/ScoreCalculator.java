package servergame.card;

import commun.player.Player;
import commun.wonderboard.WonderBoard;
import log.GameLogger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScoreCalculator {
    private int score;

    /**
     *
     * @param wonderBoard La merveille qui va me permettre de recupérer les cartes à fin de pouvoir calculer le score.
     * @return Retourne le score final
     */
    public Integer getScore(WonderBoard wonderBoard)
    {

        for (int i = 0 ; i < wonderBoard.getBuilding().getLength() ; i++)
        {
            score += wonderBoard.getBuilding().getCard(i).getCardEffect().getScore();
        }
        return score;
    }

    /**
     *
     * @param players Prend en parametre la list de tous les joueurs
     * @return Retourne le joueur qui à le score le plus élevé
     */
    public Player winner(List<Player> players)
    {

        int winnerScore = getScore(players.get(1).getWonderBoard());
        Player winner= players.get(1);
        for (Player player : players)
        {
            if(winnerScore < getScore(player.getWonderBoard())){
                winnerScore = getScore(player.getWonderBoard());
                winner = player;

            }

        }
        return winner;

    }

    /**
     *
     * Cette methode permet de classer les joueurs
     * @param players List de tous les joueurs
     * @return Retourne le classement dans une map , les clefs sont le classement
     *
     */
    public Map<Integer,Player> ranking(List<Player> players)
    {
        Map<Integer, Player> ranking = new HashMap<Integer, Player>();
        for(int i =1 ; i+1 < players.size();i++){
            Player player = winner(players);
            ranking.put(i,player);
            players.remove(player);
        }

        return ranking;


    }

    /**
     * Cette methode permet d'afficher le classement des joueurs
     * @param allPlayers List des joueurs
     *
     */
    public void printRanking(List<Player> allPlayers)
    {
        Map<Integer, Player> ranking = ranking(allPlayers);
        GameLogger.log("Classement des joueurs : " );

        for (int i=1; i< ranking.size(); i++ ) {
            GameLogger.log(i + " : " + ranking.get(i).getName());
        }


    }



}