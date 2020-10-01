package commun.communication.statobjects;

import commun.communication.StatObject;

import java.util.ArrayList;

public class StatVictoryFrequency extends StatIntegerBase
{
    public StatVictoryFrequency ()
    { super(); }

    public void add (StatObject statObject, ArrayList<String> added)
    {
        if (this.stat.size() == 0)
        {
            for (int i = 0; i < added.size(); i++)
            {
                this.stat.add(0);
            }
        }
        else if (this.stat != null && added.size() != this.stat.size())
            throw new IllegalArgumentException("Les tailles sont differentes");

        int index = statObject.getUsernames().indexOf(added.get(0)) - 1;
        this.stat.set(index, this.stat.get(index) + 1);
    }
}