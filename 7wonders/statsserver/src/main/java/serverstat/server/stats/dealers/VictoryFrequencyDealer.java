package serverstat.server.stats.dealers;

import java.util.ArrayList;

public class VictoryFrequencyDealer extends DealerBase
{
    public VictoryFrequencyDealer (String title)
    { super(title); }

    public VictoryFrequencyDealer ()
    { this("Taux de victoires"); }

    public ArrayList<String> deal (ArrayList<Integer> victoryPointsStat)
    { return this.deal(victoryPointsStat, 1); }
}
