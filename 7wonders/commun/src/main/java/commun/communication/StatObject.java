package commun.communication;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class StatObject
{
    private ArrayList<Integer> victoryPointsStats;

    public StatObject () {}

    public void setVictoryPointsStats (ArrayList<Integer> victoryPointsStats)
    { this.victoryPointsStats = victoryPointsStats; }

    public ArrayList<Integer> getVictoryPointsStats ()
    { return this.victoryPointsStats; }
}
