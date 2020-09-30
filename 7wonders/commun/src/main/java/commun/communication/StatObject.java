package commun.communication;

import java.util.ArrayList;

public class StatObject
{
    private ArrayList<String> usernames;
    private ArrayList<Integer> victoryPointsStats;

    public StatObject () {}

    /** Usernames */
    public ArrayList<String> getUsernames ()
    { return this.usernames; }

    public void setUsernames (ArrayList<String> usernames)
    { this.usernames = usernames; }

    /** VictoryPointsStats */
    public void setVictoryPointsStats (ArrayList<Integer> victoryPointsStats)
    { this.victoryPointsStats = victoryPointsStats; }

    public ArrayList<Integer> getVictoryPointsStats ()
    { return this.victoryPointsStats; }

    public void addVictoryPointsStats (ArrayList<Integer> added)
    {
        if (this.victoryPointsStats != null && added.size() != this.victoryPointsStats.size())
            throw new IllegalArgumentException("Les tailles sont differentes");

        if (this.victoryPointsStats == null)
            this.victoryPointsStats = added;
        else
        {
            for (int i = 0; i < this.victoryPointsStats.size(); i++)
            {
                this.victoryPointsStats.set(i, this.victoryPointsStats.get(i) + added.get(i));
            }
        }
    }
}
