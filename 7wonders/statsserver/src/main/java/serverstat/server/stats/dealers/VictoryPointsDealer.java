package serverstat.server.stats.dealers;

import java.util.ArrayList;

/** VictoryPointsDealer permet de traiter les donnees liees aux points de victoire */
public class VictoryPointsDealer
{
    private String title;

    public VictoryPointsDealer (String title)
    { this.title = title; }

    public VictoryPointsDealer ()
    { this("Points de victoire"); }

    /** Permet de traiter les stats des points de victoire */
    public ArrayList<String> deal (ArrayList<Integer> victoryPointsStats)
    {
        ArrayList<String> output = new ArrayList<String>();

        output.add(this.title);
        for (Integer integer : victoryPointsStats)
        {
            output.add(Integer.toString(integer));
        }
        return output;
    }
}
