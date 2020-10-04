package serverstat.server.stats.dealers;

import java.util.ArrayList;

public class CardFrequencyDealer extends DealerBase
{
    public CardFrequencyDealer(String title)
    { super(title); }

    public CardFrequencyDealer()
    { this("Freq Carte"); }

    public ArrayList<String> deal (ArrayList<Integer> victoryFrequencyStat)
    { return this.deal(victoryFrequencyStat, 1); }
}
