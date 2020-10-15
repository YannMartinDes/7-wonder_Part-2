package serverstat.server.stats.dealers;

import java.util.ArrayList;

public class ScientificScoreDealer extends DealerBase
{
    public ScientificScoreDealer (String title)
    { super(title); }

    public ScientificScoreDealer ()
    { this("Score Scientifique"); }

    public ArrayList<String> deal (ArrayList<Integer> soldCardsDealerStat)
    { return this.deal(soldCardsDealerStat, 1); }
}
