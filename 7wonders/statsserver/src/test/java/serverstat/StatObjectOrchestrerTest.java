package serverstat;

import commun.communication.StatObject;
import log.LoggerComponent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serverstat.file.FileManager;
import serverstat.server.stats.StatObjectOrchestrer;
import serverstat.server.stats.dealers.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatObjectOrchestrerTest {
    StatObjectOrchestrer statObjectOrchestrer;
    DefeatFrequencyDealer defeatFrequencyDealer;
    VictoryFrequencyDealer victoryFrequencyDealer;
    private DealerBase [] ageDealers;

    MoneyDealer moneyDealer;
    StatObject statObject;

    @BeforeEach
    void init ()
    {
        this.statObjectOrchestrer = new StatObjectOrchestrer();
        this.statObject = new StatObject();

        this.victoryFrequencyDealer = new VictoryFrequencyDealer("Taux de victoires");
        this.defeatFrequencyDealer = new DefeatFrequencyDealer("Taux de defaites");

        this.ageDealers = new DealerBase[]
                {
                    new VictoryPointsDealer("A" + Integer.toString(0) + " Pts de victoires"),
                    new ConflictsDealer(0),
                    new MoneyDealer("A" + Integer.toString(0) + " Monnaie"),
                    new WonderProgressionDealer("Progression Merveille"),
                    new SoldCardsDealer("Cartes Vendues"),
                    new RessourceDealer("A" + Integer.toString(0) + " Bois"),
                    new CardFrequencyDealer("A" + Integer.toString(0) + " Cartes Building"),
                };
    }

    @Test
    void addStatObjectTest ()
    {
        ArrayList<Integer> list1 = new ArrayList<Integer>();
        list1.add(3);
        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(6);

        this.statObject.getStatByAge(0).getMoneyStats().add(list1);
        this.statObject.getStatByAge(0).getStatVictoryPoints().add(list1);
        this.statObject.getStatByAge(0).getStatConflict().add(list1);
        this.statObject.getStatByAge(0).getStatSoldCards().add(list1);
        this.statObject.getStatByAge(0).getStatWonderProgression().add(list1);
        for (int i = 0; i < 7; i++)
        {
            this.statObject.getStatByAge(0).getStatRessources(i).add(list1);
            this.statObject.getStatByAge(0).getStatCards(i).add(list1);
        }

        this.statObject.getDefeatFrequency().add(list1);
        this.statObject.getVictoryFrequency().add(list1);

        // on ajoute 2 fois la liste 1 avec statObjectOrchester et on verifie si le statObject contient la liste 2
        this.statObjectOrchestrer.addStatObject(this.statObject);
        this.statObjectOrchestrer.addStatObject(this.statObject);

        assertEquals(this.statObjectOrchestrer.getStatObject().getStatByAge(0).getMoneyStats().getStat(),list2);
        assertEquals(this.statObjectOrchestrer.getStatObject().getDefeatFrequency().getStat(),list2);
        assertEquals(this.statObjectOrchestrer.getStatObject().getStatByAge(0).getStatVictoryPoints().getStat(),list2);
        assertEquals(this.statObjectOrchestrer.getStatObject().getVictoryFrequency().getStat(),list2);

    }

    @Test
    void testRetreive ()
    {
        assertEquals(this.statObjectOrchestrer.retreive(""), null);
    }

    @Test
    void testTranpose ()
    {
        ArrayList<ArrayList<String>> arrayLists = new ArrayList<ArrayList<String>>();
        arrayLists.add(new ArrayList<String>());
        arrayLists.get(0).add("Test");
        arrayLists.get(0).add("tseT");

        List<List<String>> after = this.statObjectOrchestrer.transpose(arrayLists);

        arrayLists = new ArrayList<ArrayList<String>>();
        arrayLists.add(new ArrayList<String>());
        arrayLists.add(new ArrayList<String>());
        arrayLists.get(0).add("Test");
        arrayLists.get(1).add("tseT");
        this.checkTranspose(arrayLists, after);

        arrayLists = new ArrayList<ArrayList<String>>();
        ArrayList<String> r1 = new ArrayList<String>();
        r1.add("1");
        r1.add("2");
        ArrayList<String> r2 = new ArrayList<String>();
        r2.add("3");
        r2.add("4");
        arrayLists.add(r1);
        arrayLists.add(r2);

        after = this.statObjectOrchestrer.transpose(arrayLists);
        arrayLists = new ArrayList<ArrayList<String>>();
        r1 = new ArrayList<String>();
        r1.add("1");
        r1.add("3");
        arrayLists.add(r1);
        r2 = new ArrayList<String>();
        r2.add("2");
        r2.add("4");
        arrayLists.add(r2);

        this.checkTranspose(arrayLists, after);
    }

    void checkTranspose (ArrayList<ArrayList<String>> before, List<List<String>> after)
    {
        for (int i = 0; i < before.size(); i++)
        {
            for (int k = 0; k < before.get(i).size(); k++)
            {
                assertEquals(before.get(i).get(k), after.get(i).get(k));
            }
        }
    }

    private StatObject genStatObject ()
    {
        StatObject statObject;

        statObject = new StatObject();
        statObject.construct(1);

        statObject.setUsernames(new ArrayList<>(Arrays.asList("/", "TEST")));
        statObject.setAIUsed(new ArrayList<>(Arrays.asList("IA Utilisée", "TSET")));
        statObject.getVictoryFrequency().setStat(new ArrayList<>(Arrays.asList(1)));
        statObject.getDefeatFrequency().setStat(new ArrayList<>(Arrays.asList(0)));
        for (int i = 0; i < statObject.getStatByAge().length; i++)
        {
            statObject.getStatByAge(i).getStatWonderProgression().setStat(new ArrayList<>(Arrays.asList(1)));
            statObject.getStatByAge(i).getStatScientificScore().setStat(new ArrayList<>(Arrays.asList(1)));
            statObject.getStatByAge(i).getStatSoldCards().setStat(new ArrayList<>(Arrays.asList(1)));
            statObject.getStatByAge(i).getMoneyStats().setStat(new ArrayList<>(Arrays.asList(1)));
            statObject.getStatByAge(i).getStatVictoryPoints().setStat(new ArrayList<>(Arrays.asList(1)));
            statObject.getStatByAge(i).getStatConflict().setStat(new ArrayList<>(Arrays.asList(1)));

            for (int k = 0; k < statObject.getStatByAge(i).getStatCards().length; k++)
            {
                statObject.getStatByAge(i).getStatCards(k).setStat(new ArrayList<>(Arrays.asList(1)));
            }

            for (int k = 0; k < statObject.getStatByAge(i).getStatRessources().length; k++)
            {
                statObject.getStatByAge(i).getStatRessources(k).setStat(new ArrayList<>(Arrays.asList(1)));
            }
        }

        return statObject;
    }

    @Test
    void testDistribute ()
    {
        PrintStream old = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        LoggerComponent.verbose_socket = false;
        LoggerComponent.verbose = false;

        String expected = "/,IA Utilisée,Taux de victoires,Taux de defaites,A1 Pts de victoires,A1 Score Scientifique,Gains Conflits Age 1,A1 Monnaie,Progression Merveille,Cartes Vendues,A1 Bois,A1 Argile,A1 Pierre,A1 Minerais,A1 Verre,A1 Papyrus,A1 Tissu,A1 Cartes Building,A1 Cartes Commerce,A1 Cartes Militaire,A1 Cartes Produits Manuf,A1 Cartes Scientfique,A1 Cartes Ressource,A1 Cartes Guildes,A2 Pts de victoires,A2 Score Scientifique,Gains Conflits Age 2,A2 Monnaie,Progression Merveille,Cartes Vendues,A2 Bois,A2 Argile,A2 Pierre,A2 Minerais,A2 Verre,A2 Papyrus,A2 Tissu,A2 Cartes Building,A2 Cartes Commerce,A2 Cartes Militaire,A2 Cartes Produits Manuf,A2 Cartes Scientfique,A2 Cartes Ressource,A2 Cartes Guildes,A3 Pts de victoires,A3 Score Scientifique,Gains Conflits Age 3,A3 Monnaie,Progression Merveille,Cartes Vendues,A3 Bois,A3 Argile,A3 Pierre,A3 Minerais,A3 Verre,A3 Papyrus,A3 Tissu,A3 Cartes Building,A3 Cartes Commerce,A3 Cartes Militaire,A3 Cartes Produits Manuf,A3 Cartes Scientfique,A3 Cartes Ressource,A3 Cartes Guildes";
        expected += "\n" + "TEST,TSET,1.0,0.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0";

        StatObject statObject = this.genStatObject();
        // Instancie les usernames dans StatObjectOrchestrer
        statObjectOrchestrer.addStatObject(statObject);
        // Va ecrire dans statsserver/stats.csv
        statObjectOrchestrer.distribute(statObject, 1);

        FileManager fileManager = new FileManager("statsserver/stats.csv");
        String actual = fileManager.getRaw();

        assertEquals(expected, actual);
    }

    @AfterEach
    void end ()
    {
        new File("statsserver/stats.csv").delete();
        new File("statsserver").delete();
    }
}
