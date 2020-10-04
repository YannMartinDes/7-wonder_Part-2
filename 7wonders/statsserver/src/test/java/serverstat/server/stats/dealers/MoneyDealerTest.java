package serverstat.server.stats.dealers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyDealerTest {
    MoneyDealer moneyDealer;
    @BeforeEach
    public void init ()
    {
        this.moneyDealer = new MoneyDealer();
    }

    //Exactement le meme que dans VictoryPointsDealer
    @Test //
    public void testDeal ()
    {
        ArrayList<String> before = new ArrayList<String>();
        before.add("Monnaie");
        before.add("1.0");
        before.add("2.0");
        before.add("3.0");

        ArrayList<Integer> now = new ArrayList<Integer>();
        now.add(1);
        now.add(2);
        now.add(3);

        ArrayList<String> after = this.moneyDealer.deal(now, 1);

        for (int i = 0; i < after.size(); i++)
        {
            assertEquals(before.get(i), after.get(i));
        }
    }
}
