package servergame;

import commun.card.CardType;
import commun.card.Deck;
import commun.communication.StatObject;
import commun.effect.EarnWithCard;
import commun.effect.IEffect;
import commun.effect.ScientificType;
import commun.effect.TargetType;
import commun.wonderboard.WonderStep;
import log.ConsoleColors;
import log.GameLogger;

import servergame.player.Player;

import java.util.*;

/** ScoreCalculator est une classe qui permet de calculer les scores de joueurs */
public class ScoreCalculator {

    private StatObject statObject;

    /** Constructeur
     * @param statObject L'objet de statistiques */
    public ScoreCalculator (StatObject statObject)
    {
        this.statObject = statObject;
    }

    /**
     * @param player La merveille qui va me permettre de recupérer les cartes à fin de pouvoir calculer le score.
     * @return Retourne le score final
     */
    public Integer getScore(Player player)
    {
        if (player.getWonderBoard() == null)
            return 0;
        GameLogger.logSpaceAfter("--- Calcul du score du joueur " + player.getName() + " ---", ConsoleColors.ANSI_YELLOW_BOLD);
        int score = 0;
        int scoreWithCoins = 0;
        int scoreWithConflictsPoints = 0;
        for (int i = 0 ; i < player.getWonderBoard().getBuilding().getLength() ; i++)
        {
            score +=  player.getWonderBoard().getBuilding().getCard(i).getCardEffect().getScore();
            if (player.getWonderBoard().getBuilding().getCard(i).getCardEffect().getScore() > 0)
            {
                GameLogger.log("Le joueur " + player.getName() + " a joué la carte \"" + player.getWonderBoard().getBuilding().getCard(i) + "\"");
                GameLogger.logSpaceAfter("La carte "+ player.getWonderBoard().getBuilding().getCard(i) +" lui fait gagner " + player.getWonderBoard().getBuilding().getCard(i).getCardEffect().getScore() + " points.", ConsoleColors.ANSI_GREEN);
            }
            //CARTE EARN EFFECT
            EarnWithCard earnWithCard = player.getWonderBoard().getBuilding().getCard(i).getCardEffect().getEarnWithCardEffect();
            if (earnWithCard != null){
                if(earnWithCard.getVictoryPointEarn() != 0){//SI LA CARTE RAPPORTE DES POINTS VICTOIRES
                    int vp = 0;//Victory points

                    if(earnWithCard.getAffectedNeightbour() == TargetType.ME){//CONSTRUCTION INTERNE
                        vp += player.getWonderBoard().countCard(earnWithCard.getCardType()) * earnWithCard.getVictoryPointEarn();
                    }
                    if(earnWithCard.getAffectedNeightbour() == TargetType.BOTH_NEIGHTBOUR){//CONSTRUCTION EXTERNE
                        vp += player.getLeftNeightbour().countCard(earnWithCard.getCardType()) * earnWithCard.getVictoryPointEarn();
                        vp += player.getRightNeightbour().countCard(earnWithCard.getCardType()) * earnWithCard.getVictoryPointEarn();
                    }

                    GameLogger.log("Le joueur " + player.getName() + " a joué la carte \"" + player.getWonderBoard().getBuilding().getCard(i) + "\"");
                    GameLogger.logSpaceAfter("La carte "+ player.getWonderBoard().getBuilding().getCard(i) +" lui fait gagner " + vp + " points.", ConsoleColors.ANSI_GREEN);
                    score += vp;
                }
            }
        }
        score += computeScientificScore(player);
        scoreWithCoins += player.getWonderBoard().getCoin() / 3;
        GameLogger.log("Le joueur "+ player.getName() + " a " + player.getWonderBoard().getCoin() + " pièces.");
        if (player.getWonderBoard().getCoin() > 0) {
            GameLogger.logSpaceAfter("Cela lui rapporte un total de " + scoreWithCoins + " points.",ConsoleColors.ANSI_GREEN);
        }

        //Ajout des point de victoire des étape de la merveille
        for (WonderStep wonderStep :  player.getWonderBoard().getWonders() ) {
            if(wonderStep.getBuilt()) {
                for (IEffect effect: Arrays.asList(wonderStep.getEffects())) {
                    score +=  effect.getScore();
                    if (effect.getScore() > 0)
                    {
                        GameLogger.log("Le joueur "+ player.getName() + " a construit l'étape *" + wonderStep.getStep() + "* de la merveille " +player.getWonderBoard().getWonderName());
                        GameLogger.logSpaceAfter("Celle-ci lui rapporte " + effect.getScore() + " points de victoire.", ConsoleColors.ANSI_GREEN);
                    }

                }
            }

        }

        scoreWithConflictsPoints += player.getWonderBoard().getConflictPoints();
        GameLogger.log("Le joueur " + player.getName() + " a " + player.getWonderBoard().getConflictPoints() + " jetons de conflit militaire.");
        if (player.getWonderBoard().getConflictPoints() != 0) {
            GameLogger.logSpaceAfter("Cela lui rapporte un total de " + scoreWithConflictsPoints + " points.",ConsoleColors.ANSI_GREEN);
        }else{
            GameLogger.log("");
        }
        return score + scoreWithCoins + scoreWithConflictsPoints;
    }

    /** computeFinalScore permet de calculer les scores a la fin d'une partie
     * @param players la liste des joueurs */
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

        GameLogger.logSpaceBefore("--- Classement ---",ConsoleColors.ANSI_RED_BOLD_BRIGHT);
        for (int i=0; i < ranking.size(); i++ ) {//0
            GameLogger.log((i+1) + " : " + ranking.get(i).getName() + " avec un score de "+ ranking.get(i).getFinalScore());
        }

        this.endGameStatistics(ranking);
        GameLogger.logSpaceBefore("Le vainqueur est : "+ ranking.get(0).getName(),ConsoleColors.ANSI_GREEN_BOLD_BRIGHT);
    }

    /**
     * Permet de gerer les statistiques en fin de partie
     * @param ranking le rang des joueurs
     */
    private void endGameStatistics (List<Player> ranking)
    {
        ArrayList<Integer> victoryPoints = new ArrayList<Integer>();
        ArrayList<Integer> money = new ArrayList<Integer>();

        /** Statistiques */
        /** Liste des noms des jueurs sur le ranking*/
        ArrayList<String> players = new ArrayList<String>();

        /** VictoryPoints & Money */
        for (String user : this.statObject.getUsernames())
        {
            if (user.equals("/")) continue;
            //GameLogger.important(user);
            // Trouver le bon user
            // c'est sale
            int rightIndex = 0;
            for (int k = 0; k < ranking.size(); k++)
            {
                if (ranking.get(k).getName().equals(user))
                {
                    //GameLogger.important(">> " + Integer.toString(k));
                    rightIndex = k;
                    break;
                }
            }
            victoryPoints.add(ranking.get(rightIndex).getFinalScore());
            money.add(ranking.get(rightIndex).getWonderBoard().getCoin());
        }

        for (Player p : ranking)
        {
            players.add(p.getName());
        }

//        GameLogger.put(victoryPoints.toString());
//        GameLogger.put(money.toString());
//        GameLogger.put(players.toString());

        // Ajout dans les statistiques
        this.statObject.getStatVictoryPoints().add(victoryPoints);
        this.statObject.getMoneyStats().add(money);
        /** VictoryFrequency */
        this.statObject.getDefeatFrequency().add(this.statObject, players);
        /** DefeatFrequency */
        this.statObject.getVictoryFrequency().add(this.statObject, players);
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
        Map<ScientificType, Integer> scientificCards =new HashMap<>();
        Deck building = player.getWonderBoard().getBuilding();

        for (int i = 0 ; i <  building.getLength(); i++) {
            if(building.getCard(i).getType() == CardType.SCIENTIFIC_BUILDINGS) {
                if (scientificCards.containsKey(building.getCard(i).getCardEffect().getScientificType())) {
                    scientificCards.replace(building.getCard(i).getCardEffect().getScientificType(), scientificCards.get(building.getCard(i).getCardEffect().getScientificType()) + 1);
                } else {
                    scientificCards.put(building.getCard(i).getCardEffect().getScientificType(), 1);
                }
            }
        }

        for (ScientificType type : scientificCards.keySet()) {
            int point = (int)Math.pow(scientificCards.get(type),2);
            score += point;

            GameLogger.log("Le joueur " + player.getName() + " a joué "+ scientificCards.get(type) +" bâtiment scientifique de symboles "+type.getName() +" identiques.");
            GameLogger.logSpaceAfter("Ce qui lui fait gagner " + point + " points.",ConsoleColors.ANSI_GREEN);
        }

        while (scientificCards.size()==3){
            score += 7;

            GameLogger.log("Le joueur " + player.getName() + " a joué un groupe de 3 symboles scientifique différents.");
            GameLogger.logSpaceAfter("Ce qui lui fait gagner 7 points.",ConsoleColors.ANSI_GREEN);

            Map<ScientificType, Integer> copy =new HashMap<>(scientificCards);

            for (ScientificType s: copy.keySet()) {
                scientificCards.replace(s,scientificCards.get(s)-1);
                if ( scientificCards.get(s) <=0 ){
                    scientificCards.remove(s);
                }
            }
        }
        return score;
    }

}