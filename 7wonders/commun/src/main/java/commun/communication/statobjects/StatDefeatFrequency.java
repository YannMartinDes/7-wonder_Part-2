package commun.communication.statobjects;

import commun.communication.StatObject;

import java.util.ArrayList;

public class StatDefeatFrequency extends StatIntegerBase
{
    public StatDefeatFrequency ()
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
        // i = 1 car le premier est le vainqueur
        for (int i = 1; i < added.size(); i++)
        {
            int index = statObject.getUsernames().indexOf(added.get(i)) - 1;
            this.stat.set(index, this.stat.get(index) + 1);
        }
    }
}