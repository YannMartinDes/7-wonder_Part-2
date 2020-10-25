package commun.communication.statobjects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class StatIntegerBasesTest
{
    private StatIntegerBase [] statIntegerBase;

    @BeforeEach
    void init ()
    {
        this.statIntegerBase = new StatIntegerBase[]
                {
                        new StatCard(),
                        new StatConflicts(),
                        new StatMoney(),
                        new StatRessource(),
                        new StatScientificScore(),
                        new StatSoldCards(),
                        new StatVictoryPoints(),
                        new StatWonderProgression()
                };
    }

    @Test
    void testAdd ()
    {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);

        for (int i = 0; i < this.statIntegerBase.length; i++)
        {
            assertEquals(this.statIntegerBase[i].getStat().size(), 1);
            assertEquals(this.statIntegerBase[i].getStat().get(0), 0);
        }

        for (int i = 0; i < this.statIntegerBase.length; i++)
        {
            this.statIntegerBase[i].add(arrayList);
        }

        for (int i = 0; i < this.statIntegerBase.length; i++)
        {
            assertEquals(this.statIntegerBase[i].getStat().size(), 1);
            assertEquals(this.statIntegerBase[i].getStat().get(0), 1);
        }

        for (int i = 0; i < this.statIntegerBase.length; i++)
        {
            this.statIntegerBase[i].add(arrayList);
        }

        for (int i = 0; i < this.statIntegerBase.length; i++)
        {
            assertEquals(this.statIntegerBase[i].getStat().size(), 1);
            assertEquals(this.statIntegerBase[i].getStat().get(0), 2);
        }
    }
}
