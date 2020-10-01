package serverstat.server.stats;

import commun.communication.StatObject;
import log.GameLogger;
import serverstat.file.FileManager;
import serverstat.server.stats.dealers.DefeatFrequencyDealer;
import serverstat.server.stats.dealers.MoneyDealer;
import serverstat.server.stats.dealers.VictoryFrequencyDealer;
import serverstat.server.stats.dealers.VictoryPointsDealer;

import java.util.ArrayList;
import java.util.List;

public class StatObjectOrchestrer
{
    /** Dealers */
    private VictoryPointsDealer victoryPointDealer;
    private VictoryFrequencyDealer victoryFrequencyDealer;
    private MoneyDealer moneyDealer;
    private DefeatFrequencyDealer defeatFrequencyDealer;

    /** StatObject */
    private StatObject statObject;

    /** Constructeur */
    public StatObjectOrchestrer()
    {
        this.victoryPointDealer = new VictoryPointsDealer("Points de victoire");
        this.victoryFrequencyDealer = new VictoryFrequencyDealer("Taux de victoires");
        this.moneyDealer = new MoneyDealer("Monnaie a la fin");
        this.defeatFrequencyDealer = new DefeatFrequencyDealer("Taux de defaites");
        this.statObject = new StatObject();
    }

    /** Addition d'un StatObject a un autre
     * pour les 1000 parties*/
    public void addStatObject (StatObject statObjectAdded)
    {
        this.statObject.getStatVictoryPoints().add(statObjectAdded.getStatVictoryPoints().getStat());
        this.statObject.getDefeatFrequency().add(statObjectAdded.getDefeatFrequency().getStat());
        this.statObject.getMoneyStats().add(statObjectAdded.getMoneyStats().getStat());
        this.statObject.getVictoryFrequency().add(statObjectAdded.getVictoryFrequency().getStat());

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
        lists.add(this.victoryPointDealer.deal(statObject.getStatVictoryPoints().getStat(), divisor));
        lists.add(this.victoryFrequencyDealer.deal(statObject.getVictoryFrequency().getStat(), divisor));
        lists.add(this.defeatFrequencyDealer.deal(statObject.getDefeatFrequency().getStat(), divisor));
        lists.add(this.moneyDealer.deal(statObject.getMoneyStats().getStat(), divisor));

        listsT = this.transpose(lists);

        /** Affichage resultats en console */
        for (List<String> list : listsT)
        {
            for (String string : list)
            {
                System.out.printf("%-20s ", string);
            }
            System.out.println();
        }

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
        FileManager fileManager = new FileManager("statsserver/stats.csv");

        if (fileManager.exists())
        { fileManager.deleteFile(); }

        fileManager.write(string);
        GameLogger.important("Le fichier est sauvegarde a: " + fileManager.getFile().getAbsolutePath());
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
