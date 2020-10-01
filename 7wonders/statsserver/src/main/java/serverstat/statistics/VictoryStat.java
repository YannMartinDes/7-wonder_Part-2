package serverstat.statistics;

import java.util.HashMap;

public class VictoryStat {

    HashMap<String, Integer> numberOfVictory = new HashMap<String, Integer>();

    public VictoryStat(HashMap<String, Integer> numberOfVictory) {
        for(String name : numberOfVictory.keySet()){
            this.numberOfVictory.put(name, 0);
        }
    }

    /**
     * Permet de récuperer le nombre de victoires d'un joueur.
     * @param playerName : nom du joueur
     * @return nombre de victoires
     */
    public int getVictoryNumber(String playerName){
        return numberOfVictory.getOrDefault(playerName, 0);
    }

    /**
     * Ajoute 1 au nombre de victoires d'un joueur.
     * @param playerName
     */
    public void addVictoryTo(String playerName){
        if (!numberOfVictory.containsKey(playerName)) numberOfVictory.put(playerName, 1);
        else numberOfVictory.put(playerName, numberOfVictory.get(playerName) + 1);
    }

    /**
     * Permet d'avoir le pourcentage de victoire d'un joueur.
     * @param playerName : nom du joueur
     * @param totalGames : nombre total de parties
     * @return Pourcentage de victoire
     */
    public float victoryPercentage(String playerName, int totalGames){
        return ((float) numberOfVictory.get(playerName) / totalGames) * 100;
    }

    /**
     * Permet d'avoir le pourcentage de défaite d'un joueur.
     * @param playerName : nom du joueur
     * @param totalGames : nombre total de parties
     * @return Pourcentage de défaite
     */
    public float defeatPercentage(String playerName, int totalGames){
        return (100 - victoryPercentage(playerName, totalGames));
    }
}
