package serverstat.server.stats.dealers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DealerTest
{
    private DealerBase [] dealers;

    @BeforeEach
    public void init ()
    {
        this.dealers = new DealerBase[]
                {
                        new CardFrequencyDealer(),
                        new ConflictsDealer(1),
                        new DefeatFrequencyDealer(),
                        new MoneyDealer(),
                        new RessourceDealer(),
                        new SoldCardsDealer(),
                        new VictoryFrequencyDealer(),
                        new VictoryPointsDealer(),
                        new WonderProgressionDealer(),
                        new ScientificScoreDealer()
                };
    }

    @Test
    public void dealTest ()
    {
        for (DealerBase dealerBase : this.dealers)
        {
            ArrayList<String> before = new ArrayList<String>();
            before.add("Taux de défaites");
            before.add("1.0");
            before.add("2.0");
            before.add("3.0");

            ArrayList<Integer> now = new ArrayList<Integer>();
            now.add(1);
            now.add(2);
            now.add(3);

            ArrayList<String> after = dealerBase.deal(now, 1);

            for (int i = 1; i < after.size(); i++)
            {
                assertEquals(before.get(i), after.get(i));
            }
        }
    }
}
