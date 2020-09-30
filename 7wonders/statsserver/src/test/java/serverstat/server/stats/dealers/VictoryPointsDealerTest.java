package serverstat.server.stats.dealers;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;

public class VictoryPointsDealerTest
{
    private VictoryPointsDealer victoryPointsDealer;

    @BeforeEach
    public void init ()
    {
        this.victoryPointsDealer = new VictoryPointsDealer();
    }

    @Test
    public void testDeal ()
    {
        ArrayList<String> before = new ArrayList<String>();
        before.add("Points de victoire");
        before.add("1.0");
        before.add("2.0");
        before.add("3.0");

        ArrayList<Integer> now = new ArrayList<Integer>();
        now.add(1);
        now.add(2);
        now.add(3);

        ArrayList<String> after = this.victoryPointsDealer.deal(now, 1);

        for (int i = 0; i < after.size(); i++)
        {
            assertEquals(before.get(i), after.get(i));
        }
    }
}
