package serverstat.server.stats.dealers;

import java.util.ArrayList;

public class VictoryFrequencyDealer extends DealerBase
{
    public VictoryFrequencyDealer (String title)
    { super(title); }

    public VictoryFrequencyDealer ()
    { this("Taux de victoires"); }

    public ArrayList<String> deal (ArrayList<Integer> victoryFrequencyStat)
    { return this.deal(victoryFrequencyStat, 1); }
}
