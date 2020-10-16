package servergame.score;

import commun.card.Card;
import commun.communication.StatObject;
import commun.effect.*;
import commun.material.Material;
import commun.material.MaterialType;
import commun.player.Player;
import commun.wonderboard.WonderStep;
import log.ConsoleColors;
import log.GameLogger;


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
        GameLogger.getInstance().logSpaceAfter("--- Calcul du score du joueur " + player.getName() + " ---", ConsoleColors.ANSI_YELLOW_BOLD);
        int score = 0;
        int scoreWithCoins = 0;
        int scoreWithConflictsPoints = 0;
        for (int i = 0 ; i < player.getWonderBoard().getBuilding().getLength() ; i++)
        {
            score +=  player.getWonderBoard().getBuilding().getCard(i).getCardEffect().getScore();
            if (player.getWonderBoard().getBuilding().getCard(i).getCardEffect().getScore() > 0)
            {
                GameLogger.getInstance().log("Le joueur " + player.getName() + " a joué la carte \"" + player.getWonderBoard().getBuilding().getCard(i) + "\"");
                GameLogger.getInstance().logSpaceAfter("La carte "+ player.getWonderBoard().getBuilding().getCard(i) +" lui fait gagner " + player.getWonderBoard().getBuilding().getCard(i).getCardEffect().getScore() + " points.", ConsoleColors.ANSI_GREEN);
            }
            //CARTE EARN CARD EFFECT
            EarnWithCard earnWithCard = player.getWonderBoard().getBuilding().getCard(i).getCardEffect().getEarnWithCardEffect();
            score += EarnWithCardScore(earnWithCard,player,player.getWonderBoard().getBuilding().getCard(i));
            //CARTE EARN WONDER EFFECT
            EarnWithWonder earnWithWonder = player.getWonderBoard().getBuilding().getCard(i).getCardEffect().getEarnWithWonderEffect();
            score += EarnWithWonderScore(earnWithWonder, player, player.getWonderBoard().getBuilding().getCard(i));

        }
        score += computeScientificScore(player);
        scoreWithCoins += player.getWonderBoard().getCoin() / 3;
        GameLogger.getInstance().log("Le joueur "+ player.getName() + " a " + player.getWonderBoard().getCoin() + " pièces.");
        if (player.getWonderBoard().getCoin() > 0) {
            GameLogger.getInstance().logSpaceAfter("Cela lui rapporte un total de " + scoreWithCoins + " points.",ConsoleColors.ANSI_GREEN);
        }

        //Ajout des point de victoire des étape de la merveille
        for (WonderStep wonderStep :  player.getWonderBoard().getWonderSteps() ) {
            if(wonderStep.getBuilt()) {
                for (IEffect effect: wonderStep.getEffects()) {
                    score +=  effect.getScore();
                    if (effect.getScore() > 0)
                    {
                        GameLogger.getInstance().log("Le joueur "+ player.getName() + " a construit l'étape *" + wonderStep.getStepNumber() + "* de la merveille " +player.getWonderBoard().getWonderName());
                        GameLogger.getInstance().logSpaceAfter("Celle-ci lui rapporte " + effect.getScore() + " points de victoire.", ConsoleColors.ANSI_GREEN);
                    }

                }
            }
        }

        //calcule le score des battaille
        scoreWithConflictsPoints += new BattleScore().computeScore(player);

        return score + scoreWithCoins + scoreWithConflictsPoints;
    }

    /**
     * Renvoie le score gagné grace au EarnWithCardEffect
     * @param earnWithCard l'effet EarnWithCardEffect
     * @param player le joueur
     * @param card la carte
     * @return le score rapporté par la carte.
     */
    private int EarnWithCardScore(EarnWithCard earnWithCard, Player player, Card card){
        if(earnWithCard != null && earnWithCard.getVictoryPointEarn() != 0){//SI LA CARTE RAPPORTE DES POINTS VICTOIRES
            int vp = 0;//Victory points

            if(earnWithCard.getAffectedNeightbour() == TargetType.ME){//CONSTRUCTION INTERNE
                vp += player.getWonderBoard().countCard(earnWithCard.getCardType()) * earnWithCard.getVictoryPointEarn();
            }
            if(earnWithCard.getAffectedNeightbour() == TargetType.BOTH_NEIGHTBOUR){//CONSTRUCTION EXTERNE
                vp += player.getLeftNeightbour().countCard(earnWithCard.getCardType()) * earnWithCard.getVictoryPointEarn();
                vp += player.getRightNeightbour().countCard(earnWithCard.getCardType()) * earnWithCard.getVictoryPointEarn();
            }

            GameLogger.getInstance().log("Le joueur " + player.getName() + " a joué la carte \"" + card + "\"");
            GameLogger.getInstance().logSpaceAfter("La carte "+ card +" lui fait gagner " + vp + " points.", ConsoleColors.ANSI_GREEN);
            return vp;
        }
        return 0;
    }

    /**
     * Renvoie le score gagné grace au EarnWithWonderEffect
     * @param earnWithWonder l'effet EarnWithWonder
     * @param player le joueur
     * @param card la carte
     * @return le score rapporté par la carte.
     */
    private int EarnWithWonderScore(EarnWithWonder earnWithWonder, Player player, Card card){
        if(earnWithWonder != null && earnWithWonder.getVictoryPointEarn() != 0){//SI LA CARTE RAPPORTE DES POINTS VICTOIRES
            int vp = 0;//Victory points

            if(earnWithWonder.getAffectedNeightbour() == TargetType.ME){//CONSTRUCTION INTERNE
                vp += player.getWonderBoard().countStepBuild() * earnWithWonder.getVictoryPointEarn();
            }
            else if(earnWithWonder.getAffectedNeightbour() == TargetType.ME_AND_NEIGHTBOUR){//CONSTRUCTION EXTERNE ET INTERNE
                vp += player.getWonderBoard().countStepBuild() * earnWithWonder.getVictoryPointEarn();

                vp += player.getLeftNeightbour().countStepBuild() * earnWithWonder.getVictoryPointEarn();
                vp += player.getRightNeightbour().countStepBuild() * earnWithWonder.getVictoryPointEarn();
            }

            GameLogger.getInstance().log("Le joueur " + player.getName() + " a joué la carte \"" + card + "\"");
            GameLogger.getInstance().logSpaceAfter("La carte "+ card +" lui fait gagner " + vp + " points.", ConsoleColors.ANSI_GREEN);
            return vp;
        }
        return 0;
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

        GameLogger.getInstance().logSpaceBefore("--- Classement ---",ConsoleColors.ANSI_RED_BOLD_BRIGHT);
        for (int i=0; i < ranking.size(); i++ ) {//0
            GameLogger.getInstance().log((i+1) + " : " + ranking.get(i).getName() + " avec un score de "+ ranking.get(i).getFinalScore());
        }

        this.endGameStatistics(allPlayers, ranking);
        GameLogger.getInstance().logSpaceBefore("Le vainqueur est : "+ ranking.get(0).getName(),ConsoleColors.ANSI_GREEN_BOLD_BRIGHT);
    }

    /** midGameStatistics permet d'avoir les statistiques au milieu d'une partie
     * @param allPlayers La liste des joueurs */
    public void midGameStatistics (List<Player> allPlayers)
    {
        boolean oldVerbose = GameLogger.verbose;
        GameLogger.verbose = false;//Mute du calcul de mi-partie
        List<Player> ranking = computeFinalScore(allPlayers);
        ArrayList<Integer> victoryPoints = new ArrayList<Integer>();
        ArrayList<Integer> money = new ArrayList<Integer>();
        ArrayList<Integer> scientificScore = new ArrayList<Integer>();

        ArrayList<Integer>[] ressources = new ArrayList[7];
        for (int x = 0; x < ressources.length; x++)
        {
            ressources[x] = this.generateEmptyRessourceList(allPlayers.size());
        }

        /** Statistiques */
        /** Liste des noms des jueurs sur le ranking*/
        ArrayList<String> players = new ArrayList<String>();

        /** VictoryPoints & Money & Ressources & ScientificScore */
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
            scientificScore.add(this.computeScientificScore(ranking.get(rightIndex)));

            /** Ressources */
            EffectList effectList = ranking.get(rightIndex).getWonderBoard().getAllEffects();
            for (IEffect effect : effectList)
            {
                if (effect.getMaterials() != null)
                {
                    for (int i = 0; i < effect.getMaterials().length; i++)
                    {
                        ArrayList<Integer> array = new ArrayList<Integer>();
                        this.fillStatisticsArray(rightIndex, this.statObject, array,effect.getMaterials()[i].getNumber());
                        ressources[effect.getMaterials()[i].getType().getIndex()] = array;
                    }
                }
            }
        }

        for (Player p : ranking)
        {
            players.add(p.getName());
        }

        // Ajout dans les statistiques
        this.statObject.getStatByAge(this.statObject.getCurrentAge()).getStatVictoryPoints().add(victoryPoints);
        this.statObject.getStatByAge(this.statObject.getCurrentAge()).getMoneyStats().add(money);
        this.statObject.getStatByAge(this.statObject.getCurrentAge()).getStatScientificScore().add(scientificScore);
        for (int i = 0; i < ressources.length; i++)
        {
            this.statObject.getStatByAge(this.statObject.getCurrentAge()).getStatRessources(i).add(ressources[i]);
        }
        GameLogger.verbose = oldVerbose;//Mute du calcul de mi-partie
    }

    private ArrayList<Integer> generateEmptyRessourceList (int size)
    {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (int i = 0; i < size; i++)
        {
            arrayList.add(0);
        }
        return arrayList;
    }

    /**
     * Permet de gerer les statistiques en fin de partie
     * @param ranking le rang des joueurs
     */
    private void endGameStatistics (List<Player> allPlayers, List<Player> ranking)
    {
        this.midGameStatistics(allPlayers);
        ArrayList<String> players = new ArrayList<String>();
        for (Player p : ranking)
        {
            players.add(p.getName());
        }
        /** VictoryFrequency */
        this.statObject.getDefeatFrequency().add(this.statObject, players);
        /** DefeatFrequency */
        this.statObject.getVictoryFrequency().add(this.statObject, players);
    }

    /**
     * Stats
     * @param index index
     * @param statObject l'objet de stats
     * @param array liste
     */
    private void fillStatisticsArray (int index, StatObject statObject, ArrayList<Integer> array, Integer x)
    {
        // - 1 a cause du username '/'
        for (int i = 0; i < statObject.getUsernames().size() - 1; i++)
        {
            if (i == index) { array.add(x); }
            else { array.add(0); }
        }
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
        EffectList effects = player.getWonderBoard().getAllEffects();

        for (int i = 0 ; i <  effects.size(); i++) {
            if (effects.get(i) != null &&effects.get(i).getScientificType() != null )
            {
                if (scientificCards.containsKey(effects.get(i).getScientificType())) {
                    scientificCards.replace(effects.get(i).getScientificType(), scientificCards.get(effects.get(i).getScientificType()) + 1);
                } else {
                    scientificCards.put(effects.get(i).getScientificType(), 1);
                }
            }

        }

        for (ScientificType type : scientificCards.keySet()) {
            int point = (int)Math.pow(scientificCards.get(type),2);
            score += point;

            GameLogger.getInstance().log("Le joueur " + player.getName() + " posséde "+ scientificCards.get(type) +" bâtiment scientifique de symboles "+type.getName() +" identiques.");
            GameLogger.getInstance().logSpaceAfter("Ce qui lui fait gagner " + point + " points.",ConsoleColors.ANSI_GREEN);
        }

        while (scientificCards.size()==3){
            score += 7;

            GameLogger.getInstance().log("Le joueur " + player.getName() + " posséde un groupe de 3 symboles scientifique différents.");
            GameLogger.getInstance().logSpaceAfter("Ce qui lui fait gagner 7 points.",ConsoleColors.ANSI_GREEN);

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