package serverstat.server.stats;

import commun.communication.StatObject;
import log.GameLogger;
import serverstat.server.stats.dealers.VictoryPointsDealer;

import java.util.ArrayList;
import java.util.List;

public class StatObjectOrchestrer
{
    private VictoryPointsDealer victoryPointDealer;

    /** Constructeur */
    public StatObjectOrchestrer()
    {
        this.victoryPointDealer = new VictoryPointsDealer("Points de victoire");
    }

    /** Renvoie les donnees vers les bonnes classes */
    public void distribute (StatObject statObject)
    {
        ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
        List<List<String>> listsT;

        // Redirection
        lists.add(this.victoryPointDealer.deal(statObject.getVictoryPointsStats()));

        listsT = this.transpose(lists);

        // Parsing
        StringBuilder stringBuilder = new StringBuilder();
        for (List<String> strings : listsT)
        {
            for (String string : strings)
            {
                stringBuilder.append(string);
                stringBuilder.append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append("\n");
        }

        // Sauvegarde
        this.save(stringBuilder.toString());
    }

    public void save (String string)
    {
        GameLogger.important("Affichage en cours.."); // Saving..
        GameLogger.put(string);
    }

    // From: https://stackoverflow.com/questions/28057683/transpose-arraylistarrayliststring-in-java
    public List<List<String>> transpose (ArrayList<ArrayList<String>> matrixIn)
    {
        List<List<String>> matrixOut = new ArrayList<List<String>>();
        if (!matrixIn.isEmpty())
        {
            int noOfElementsInList = matrixIn.get(0).size();
            for (int i = 0; i < noOfElementsInList; i++)
            {
                List<String> col = new ArrayList<String>();
                for (List<String> row : matrixIn)
                {
                    col.add(row.get(i));
                }
                matrixOut.add(col);
            }
        }

        return matrixOut;
    }
}
