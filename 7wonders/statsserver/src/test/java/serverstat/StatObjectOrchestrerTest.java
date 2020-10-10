package serverstat;

import commun.communication.StatObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serverstat.server.stats.StatObjectOrchestrer;
import serverstat.server.stats.dealers.DefeatFrequencyDealer;
import serverstat.server.stats.dealers.MoneyDealer;
import serverstat.server.stats.dealers.VictoryFrequencyDealer;
import serverstat.server.stats.dealers.VictoryPointsDealer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StatObjectOrchestrerTest {
    StatObjectOrchestrer statObjectOrchestrer;
    DefeatFrequencyDealer defeatFrequencyDealer;
    VictoryPointsDealer victoryPointDealer;
    VictoryFrequencyDealer victoryFrequencyDealer;

    MoneyDealer moneyDealer;
    StatObject statObject;

    @BeforeEach
    public void init ()
    {
        this.statObjectOrchestrer = new StatObjectOrchestrer();
        this.statObject = new StatObject();

        this.victoryPointDealer = new VictoryPointsDealer("Points de victoire");
        this.victoryFrequencyDealer = new VictoryFrequencyDealer("Taux de victoires");
        this.moneyDealer = new MoneyDealer("Monnaie a la fin");
        this.defeatFrequencyDealer = new DefeatFrequencyDealer("Taux de defaites");
    }
/*
    @Test
    public  void addStatObjectTest()
    {
        ArrayList<Integer> list1 = new ArrayList<Integer>();
        list1.add(3);
        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(6);

        this.statObject.getMoneyStats().add(list1);
        this.statObject.getStatVictoryPoints().add(list1);
        this.statObject.getDefeatFrequency().add(list1);
        this.statObject.getVictoryFrequency().add(list1);
        // on ajoute 2 fois la liste 1 avec statObjectOrchester et on verifie si le statObject contient la liste 2
        this.statObjectOrchestrer.addStatObject(this.statObject);
        this.statObjectOrchestrer.addStatObject(this.statObject);

        assertEquals(this.statObjectOrchestrer.getStatObject().getMoneyStats().getStat(),list2);
        assertEquals(this.statObjectOrchestrer.getStatObject().getDefeatFrequency().getStat(),list2);
        assertEquals(this.statObjectOrchestrer.getStatObject().getStatVictoryPoints().getStat(),list2);
        assertEquals(this.statObjectOrchestrer.getStatObject().getVictoryFrequency().getStat(),list2);

    }
*/

}
