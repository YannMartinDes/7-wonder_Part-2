package serverstat.server.stats.dealers;

import java.util.ArrayList;

public class DefeatFrequencyDealer extends DealerBase
{
    public DefeatFrequencyDealer (String title)
    { super(title); }

    public DefeatFrequencyDealer ()
    { this("Taux de défaites"); }

    public ArrayList<String> deal (ArrayList<Integer> victoryPointsStat)
    { return this.deal(victoryPointsStat, 1); }
}
