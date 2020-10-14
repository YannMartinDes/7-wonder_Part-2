package serverstat;

import commun.communication.StatObject;
import log.GameLogger;
import org.junit.jupiter.api.*;
import serverstat.server.stats.StatObjectOrchestrer;
import serverstat.server.stats.dealers.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StatObjectOrchestrerTest {
    StatObjectOrchestrer statObjectOrchestrer;
    DefeatFrequencyDealer defeatFrequencyDealer;
    VictoryFrequencyDealer victoryFrequencyDealer;
    private DealerBase [] ageDealers;

    MoneyDealer moneyDealer;
    StatObject statObject;

    @BeforeEach
    public void init ()
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
    public void addStatObjectTest ()
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
    public void testRetreive ()
    {
        assertEquals(this.statObjectOrchestrer.retreive(""), null);
    }

    @Test
    public void testTranpose ()
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

    public void checkTranspose (ArrayList<ArrayList<String>> before, List<List<String>> after)
    {
        for (int i = 0; i < before.size(); i++)
        {
            for (int k = 0; k < before.get(i).size(); k++)
            {
                assertEquals(before.get(i).get(k), after.get(i).get(k));
            }
        }
    }
}
