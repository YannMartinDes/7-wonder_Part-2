package commun.communication.statobjects;

import java.util.ArrayList;

public class StatIntegerBase extends StatBase<Integer> implements IStat<ArrayList<Integer>>
{
    protected StatIntegerBase ()
    { super(); }

    public void add (ArrayList<Integer> added)
    {
        if (this.stat == null || this.stat.size() == 0)
            this.stat = added;
        else if (this.stat != null && added.size() != this.stat.size())
            throw new IllegalArgumentException("Les tailles sont differentes");
        else
        {
            for (int i = 0; i < this.stat.size(); i++)
            {
                this.stat.set(i, this.stat.get(i) + added.get(i));
            }
        }
    }
}
