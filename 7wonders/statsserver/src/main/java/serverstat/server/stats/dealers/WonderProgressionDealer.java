package serverstat.server.stats.dealers;

import java.util.ArrayList;

public class WonderProgressionDealer extends DealerBase
{
    public WonderProgressionDealer (String title)
    { super(title); }

    public WonderProgressionDealer ()
    { this("Progression Merveille"); }

    public ArrayList<String> deal (ArrayList<Integer> wonderProgressionDealerStat)
    { return this.deal(wonderProgressionDealerStat, 1); }
}
