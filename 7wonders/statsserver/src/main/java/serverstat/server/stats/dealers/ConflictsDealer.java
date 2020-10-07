package serverstat.server.stats.dealers;

import java.util.ArrayList;

public class ConflictsDealer extends DealerBase
{
    public ConflictsDealer (String title)
    { super(title); }

    public ConflictsDealer (int age)
    { this("Gains Conflits Age " + Integer.toString(age)); }

    public ArrayList<String> deal (ArrayList<Integer> conflictsStats)
    { return this.deal(conflictsStats, 1); }
}
