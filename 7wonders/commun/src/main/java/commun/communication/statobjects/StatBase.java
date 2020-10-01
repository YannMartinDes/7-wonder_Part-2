package commun.communication.statobjects;

import java.util.ArrayList;

public abstract class StatBase<T>
{
    protected ArrayList<T> stat;

    protected StatBase ()
    { this.stat = new ArrayList<T>(); }

    public void setStat (ArrayList<T> stat)
    { this.stat = stat; }

    public ArrayList<T> getStat ()
    { return this.stat; }

    public abstract void add (ArrayList<T> added);
}
