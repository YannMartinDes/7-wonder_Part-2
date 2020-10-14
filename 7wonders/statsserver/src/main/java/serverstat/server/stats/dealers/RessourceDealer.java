package serverstat.server.stats.dealers;

import java.util.ArrayList;

public class RessourceDealer extends DealerBase
{
    public RessourceDealer (String title)
    { super(title); }

    public RessourceDealer ()
    { this("Progression Merveille"); }

    public ArrayList<String> deal (ArrayList<Integer> ressourceDealerStat)
    { return this.deal(ressourceDealerStat, 1); }
}
