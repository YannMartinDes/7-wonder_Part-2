package servergame;

import commun.card.Card;
import commun.card.CardType;
import commun.communication.StatObject;
import log.ConsoleColors;
import log.GameLogger;

import servergame.player.Player;

import java.util.*;


public class ScoreCalculator {

    private StatObject statObject;

    public ScoreCalculator (StatObject statObject)
    {
        this.statObject = statObject;
    }
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
        int scoreWithConflictsPoints = 0;
        for (int i = 0 ; i < player.getWonderBoard().getBuilding().getLength() ; i++)
        {
            score +=  player.getWonderBoard().getBuilding().getCard(i).getCardEffect().getScore();
            if (player.getWonderBoard().getBuilding().getCard(i).getCardEffect().getScore() > 0)
            {
                GameLogger.log("Le joueur " + player.getName() + " a joué la carte \"" + player.getWonderBoard().getBuilding().getCard(i) + "\"");
                GameLogger.logSpaceAfter("La carte "+ player.getWonderBoard().getBuilding().getCard(i) +" lui fait gagner" + player.getWonderBoard().getBuilding().getCard(i).getCardEffect().getScore() + " points.");
            }
        }
        score += computeScientificScore(player);
        scoreWithCoins += player.getWonderBoard().getCoin() / 3;
        GameLogger.log("Le joueur "+ player.getName() + " a " + player.getWonderBoard().getCoin() + " pièces.");
        if (player.getWonderBoard().getCoin() > 0) {
            GameLogger.logSpaceAfter("Cela lui rapporte un total de " + scoreWithCoins + " points.");
        }

        scoreWithConflictsPoints += player.getWonderBoard().getConflictPoints();
        GameLogger.log("Le joueur " + player.getName() + " a " + player.getWonderBoard().getConflictPoints() + " jetons de conflit militaire.");
        if (player.getWonderBoard().getConflictPoints() > 0) {
            GameLogger.logSpaceAfter("Cela lui rapporte un total de " + scoreWithConflictsPoints + " points.");
        }
        return score + scoreWithCoins + scoreWithConflictsPoints;
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
        ArrayList<Integer> victoryPoints = new ArrayList<Integer>();

        GameLogger.log("--- Classement ---",ConsoleColors.ANSI_RED_BOLD_BRIGHT);
        for (int i=0; i < ranking.size(); i++ ) {//0
            GameLogger.log((i+1) + " : " + ranking.get(i).getName() + " avec un score de "+ ranking.get(i).getFinalScore());
        }

        for (Player p : ranking)
        {
            victoryPoints.add(p.getFinalScore());
        }

        // Ajout dans les statistiques
        this.statObject.addVictoryPointsStats(victoryPoints);
        GameLogger.logSpaceBefore("Le vainqueur est : "+ ranking.get(0).getName(),ConsoleColors.ANSI_GREEN_BOLD);
    }

    /**
     *
     * Cette methode permet de calculer les points gagner grace aux cartes batiments scientifique
     * @param player le joueur
     * @return nombre de point gagner
     */
    private int computeScientificScore(Player player)
    {
        int score = 0;
        Map< Integer , Integer> scientificCards =new HashMap<>();

        for (int i = 0 ; i <  player.getWonderBoard().getBuilding().getLength(); i++) {
            if(player.getWonderBoard().getBuilding().getCard(i).getType()== CardType.SCIENTIFIC_BUILDINGS) {
                if (scientificCards.containsKey(player.getWonderBoard().getBuilding().getCard(i).getCardEffect().getScientificType().getIndex())) {
                    scientificCards.replace(player.getWonderBoard().getBuilding().getCard(i).getCardEffect().getScientificType().getIndex(), scientificCards.get(player.getWonderBoard().getBuilding().getCard(i).getCardEffect().getScientificType().getIndex()) + 1);
                } else {
                    scientificCards.put(player.getWonderBoard().getBuilding().getCard(i).getCardEffect().getScientificType().getIndex(), 1);
                }
            }

        }

        for ( int type : scientificCards.keySet()
        ) {
            int point = (int)Math.pow(scientificCards.get(type),2);
            score += point;

            GameLogger.log("Le joueur " + player.getName() + " a joué "+ scientificCards.get(type) +" bâtiment scientifique de symboles identiques." + "\"");
            GameLogger.logSpaceAfter("Ce qui lui fait gagner " + point + " points.");

        }

        while (scientificCards.size()==3){
            score += 7;

            GameLogger.log("Le joueur " + player.getName() + " a joué un groupe de 3 symboles scientifique différents. " +"\"");
            GameLogger.logSpaceAfter("Ce qui lui fait gagner 7 points.");
            for ( int s: scientificCards.keySet()
            ) {
                scientificCards.replace(s,scientificCards.get(s)-1);
                if ( scientificCards.get(s) <=0 ){
                    scientificCards.remove(s);
                }
            }
        }
        return score;
    }

}