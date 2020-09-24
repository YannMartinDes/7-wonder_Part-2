package servergame.card;

import commun.card.Card;
import commun.card.WonderBoard;
import java.util.ArrayList;


public class ScoreCalculator {
    private Integer score;

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
    public Player winner(ArrayList<PLayer> players)
    {
        int winnerScore = getScore(players.getWonderBoard.get(1));
        Player winner= players.get(1);
        for (Player player : players
        ) {
            if(winnerScore < getScore(player.getWonderBoard())){
                winnerScore = getScore(player.getWonderBoard());
                winner = player.getWonderBoard();

            }

        }
        return winner;

    }



}