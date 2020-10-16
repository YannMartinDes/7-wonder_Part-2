package serverstat.server.stats.dealers;

import java.util.ArrayList;

public class SoldCardsDealer extends DealerBase
{
    public SoldCardsDealer (String title)
    { super(title); }

    public SoldCardsDealer ()
    { this("Cartes Vendues"); }

    public ArrayList<String> deal (ArrayList<Integer> soldCardsDealerStat)
    { return this.deal(soldCardsDealerStat, 1); }
}
