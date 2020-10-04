package serverstat.server.stats.dealers;

import java.util.ArrayList;

/** VictoryPointsDealer permet de traiter les donnees liees aux points de victoire */
public class VictoryPointsDealer extends DealerBase
{
    public VictoryPointsDealer (String title)
    { super(title); }

    public VictoryPointsDealer ()
    { this("Points de victoire"); }

    public ArrayList<String> deal (ArrayList<Integer> victoryPointsStat)
    { return this.deal(victoryPointsStat, 1); }
}
