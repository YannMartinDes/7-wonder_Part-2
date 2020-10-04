package commun.communication.statobjects;

import commun.communication.StatObject;

import java.util.ArrayList;

public class StatVictoryFrequency extends StatIntegerBase
{
    public StatVictoryFrequency (int size)
    { super(size); }

    public StatVictoryFrequency ()
    { super(1); }

    public void add (StatObject statObject, ArrayList<String> added)
    {
        if (this.stat != null && added.size() != this.stat.size())
            throw new IllegalArgumentException("Les tailles sont differentes");

        int index = statObject.getUsernames().indexOf(added.get(0)) - 1;
        this.stat.set(index, this.stat.get(index) + 1);
    }
}