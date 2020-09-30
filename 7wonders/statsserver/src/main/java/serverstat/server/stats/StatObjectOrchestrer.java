package serverstat.server.stats;

import commun.communication.StatObject;
import log.GameLogger;
import serverstat.file.FileManager;
import serverstat.server.stats.dealers.VictoryPointsDealer;

import java.util.ArrayList;
import java.util.List;

public class StatObjectOrchestrer
{
    private VictoryPointsDealer victoryPointDealer;
    private StatObject statObject;

    /** Constructeur */
    public StatObjectOrchestrer()
    {
        this.victoryPointDealer = new VictoryPointsDealer("Points de victoire");
        this.statObject = new StatObject();
    }

    /** Addition d'un StatObject a un autre */
    public void addStatObject (StatObject statObjectAdded)
    {
        this.statObject.addVictoryPointsStats(statObjectAdded.getVictoryPointsStats());
        this.statObject.setUsernames(statObjectAdded.getUsernames());
    }

    /** Finir la reception de nouveaux StatObject */
    public void finish (Integer divisor)
    {
        GameLogger.log("Les statistiques vont etre calculees..");
        this.distribute(this.statObject, divisor);
    }

    /** Renvoie les donnees vers les bonnes classes */
    public void distribute (StatObject statObject, Integer divisor)
    {
        ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
        List<List<String>> listsT;

        // Redirection
        lists.add(this.statObject.getUsernames());
        lists.add(this.victoryPointDealer.deal(statObject.getVictoryPointsStats(), divisor));

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

    /** StatObject to CSV */
    public void save (String string)
    {
        GameLogger.important("Sauvegarde en cours..");
        FileManager fileManager = new FileManager("statsserver/stats.txt");

        if (fileManager.exists())
        { fileManager.deleteFile(); }

        fileManager.write(string);
        GameLogger.important("Sauvegarde terminée");
    }

    /** CSV to StatObject */
    public StatObject retreive (String filename)
    {
        return null;
    }

    // From: https://stackoverflow.com/questions/28057683/transpose-arraylistarrayliststring-in-java
    /** Transposer une matrice de String */
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
