package commun.communication.statobjects;

import java.util.ArrayList;

/** Represente la base d'une statistique */
public abstract class StatBase<T>
{
    /* Champs */
    protected ArrayList<T> stat;

    /** Constructeur */
    protected StatBase ()
    { this.stat = new ArrayList<T>(); }

    /* Getters - Setters */

    public void setStat (ArrayList<T> stat)
    { this.stat = stat; }

    public ArrayList<T> getStat ()
    { return this.stat; }
}
