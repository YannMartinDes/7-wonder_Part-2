package serverstat.server.stats.dealers;

import java.util.ArrayList;

public class MoneyDealer extends DealerBase
{
    public MoneyDealer (String title)
    { super(title); }

    public MoneyDealer ()
    { this("Monnaie"); }

    public ArrayList<String> deal (ArrayList<Integer> moneyDealStat)
    { return this.deal(moneyDealStat, 1); }
}
