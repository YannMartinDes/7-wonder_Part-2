package serverstat.server.stats;

import commun.communication.StatObject;
import log.GameLogger;
import serverstat.file.FileManager;
import serverstat.server.stats.dealers.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StatObjectOrchestrer
{
    /** Dealers */
    private VictoryFrequencyDealer victoryFrequencyDealer;
    private DefeatFrequencyDealer defeatFrequencyDealer;

    private DealerBase [][] ageDealers;

    /** StatObject */
    private StatObject statObject;

    /** Constructeur */
    public StatObjectOrchestrer()
    {
        this.victoryFrequencyDealer = new VictoryFrequencyDealer("Taux de victoires");
        this.defeatFrequencyDealer = new DefeatFrequencyDealer("Taux de defaites");

        // 3 Ages
        this.ageDealers = new DealerBase[3][];
        for (int i = 0, age = 1 ; i < this.ageDealers.length; i++, age++)
        {
            this.ageDealers[i] = this.addAgeDealer(age);
        }

        this.statObject = null;
    }

    private DealerBase[] addAgeDealer (int age)
    {
        return new DealerBase[]
                {
                        new VictoryPointsDealer("A" + Integer.toString(age) + " Pts de victoires"),
                        new ScientificScoreDealer("A" + Integer.toString(age) + " Score Scientifique"),
                        new ConflictsDealer(age),
                        new MoneyDealer("A" + Integer.toString(age) + " Monnaie"),
                        new WonderProgressionDealer("Progression Merveille"),
                        new SoldCardsDealer("Cartes Vendues"),
                        new RessourceDealer("A" + Integer.toString(age) + " Bois"),
                        new RessourceDealer("A" + Integer.toString(age) + " Argile"),
                        new RessourceDealer("A" + Integer.toString(age) + " Pierre"),
                        new RessourceDealer("A" + Integer.toString(age) + " Minerais"),
                        new RessourceDealer("A" + Integer.toString(age) + " Verre"),
                        new RessourceDealer("A" + Integer.toString(age) + " Papyrus"),
                        new RessourceDealer("A" + Integer.toString(age) + " Tissu"),
                        new CardFrequencyDealer("A" + Integer.toString(age) + " Cartes Building"),
                        new CardFrequencyDealer("A" + Integer.toString(age) + " Cartes Commerce"),
                        new CardFrequencyDealer("A" + Integer.toString(age) + " Cartes Militaire"),
                        new CardFrequencyDealer("A" + Integer.toString(age) + " Cartes Produits Manuf"),
                        new CardFrequencyDealer("A" + Integer.toString(age) + " Cartes Scientfique"),
                        new CardFrequencyDealer("A" + Integer.toString(age) + " Cartes Ressource"),
                        new CardFrequencyDealer("A" + Integer.toString(age) + " Cartes Guildes")
                };
    }

    /** Addition d'un StatObject a un autre
     * pour les 1000 parties*/
    public void addStatObject (StatObject statObjectAdded)
    {
        // Initialiser la stat object
        if (this.statObject == null)
        { this.statObject = statObjectAdded; }
        else
        {
            this.statObject.getDefeatFrequency().add(statObjectAdded.getDefeatFrequency().getStat());
            this.statObject.getVictoryFrequency().add(statObjectAdded.getVictoryFrequency().getStat());

            for (int i = 0; i < this.statObject.getStatByAge().length; i++)
            {
                this.statObject.getStatByAge(i).getStatVictoryPoints().add(statObjectAdded.getStatByAge(i).getStatVictoryPoints().getStat());
                this.statObject.getStatByAge(i).getStatScientificScore().add(statObjectAdded.getStatByAge(i).getStatScientificScore().getStat());
                this.statObject.getStatByAge(i).getStatConflict().add(statObjectAdded.getStatByAge(i).getStatConflict().getStat());
                this.statObject.getStatByAge(i).getMoneyStats().add(statObjectAdded.getStatByAge(i).getMoneyStats().getStat());
                this.statObject.getStatByAge(i).getStatWonderProgression().add(statObjectAdded.getStatByAge(i).getStatWonderProgression().getStat());
                this.statObject.getStatByAge(i).getStatSoldCards().add(statObjectAdded.getStatByAge(i).getStatSoldCards().getStat());

                for (int k = 0; k < this.statObject.getStatByAge(i).getStatCards().length; k++)
                {
                    this.statObject.getStatByAge(i).getStatCards(k).add(statObjectAdded.getStatByAge(i).getStatCards(k).getStat());
                }

                for (int k = 0; k < this.statObject.getStatByAge(i).getStatRessources().length; k++)
                {
                    this.statObject.getStatByAge(i).getStatRessources(k).add(statObjectAdded.getStatByAge(i).getStatRessources(k).getStat());
                }

                this.statObject.setAIUsed(statObjectAdded.getAIUsed());
                this.statObject.setUsernames(statObjectAdded.getUsernames());
            }
        }
    }

    /** Finir la reception de nouveaux StatObject */
    public void finish (Integer divisor)
    {
        GameLogger.getInstance().log("Les statistiques vont etre calculees..");
        this.distribute(this.statObject, divisor);
    }

    /** Renvoie les donnees vers les bonnes classes */
    public void distribute (StatObject statObject, Integer divisor)
    {
        ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
        List<List<String>> listsT;

        // Redirection
        lists.add(this.statObject.getUsernames());
        lists.add(this.statObject.getAIUsed());
        lists.add(this.victoryFrequencyDealer.deal(statObject.getVictoryFrequency().getStat(), divisor));
        lists.add(this.defeatFrequencyDealer.deal(statObject.getDefeatFrequency().getStat(), divisor));
        // Pour chaque Age
        for (int i = 0; i < this.statObject.getStatByAge().length; i++)
        {
            lists.add(this.ageDealers[i][0].deal(statObject.getStatByAge(i).getStatVictoryPoints().getStat(), divisor));
            lists.add(this.ageDealers[i][1].deal(statObject.getStatByAge(i).getStatScientificScore().getStat(), divisor));
            lists.add(this.ageDealers[i][2].deal(statObject.getStatByAge(i).getStatConflict().getStat(), divisor));
            lists.add(this.ageDealers[i][3].deal(statObject.getStatByAge(i).getMoneyStats().getStat(), divisor));
            lists.add(this.ageDealers[i][4].deal(statObject.getStatByAge(i).getStatWonderProgression().getStat(), divisor));
            lists.add(this.ageDealers[i][5].deal(statObject.getStatByAge(i).getStatSoldCards().getStat(), divisor));

            // Pour chaque type de ressource
            for (int k = 6; k < 6 + statObject.getStatByAge(i).getStatRessources().length; k++)
            {
                lists.add(this.ageDealers[i][k].deal(statObject.getStatByAge(i).getStatRessources(k - 6).getStat(), divisor));
            }

            // Pour chaque type de carte
            for (int k = 6 + statObject.getStatByAge(i).getStatRessources().length; k < this.ageDealers[i].length; k++)
            {
                lists.add(this.ageDealers[i][k].deal(statObject.getStatByAge(i).getStatCards(k - (6 + statObject.getStatByAge(i).getStatRessources().length)).getStat(), divisor));
            }
        }

        listsT = this.transpose(lists);

        /** Affichage resultats en console */
        for (List<String> list : listsT)
        {
            for (String string : list)
            {
                System.out.printf("%-25s ", string);
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
        GameLogger.getInstance().important("Sauvegarde en cours..");

        new File("statsserver").mkdir();
        FileManager fileManager = new FileManager("statsserver/stats.csv");

        if (fileManager.exists())
        { fileManager.deleteFile(); }

        fileManager.write(string);
        GameLogger.getInstance().important("Le fichier est sauvegarde a: " + fileManager.getFile().getAbsolutePath());
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

    public StatObject getStatObject() {
        return statObject;
    }
}
