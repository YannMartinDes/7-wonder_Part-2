package commun.communication;

import commun.communication.statobjects.*;

import java.util.ArrayList;

public class StatObject
{
    private ArrayList<String> usernames;
    private StatVictoryPoints statVictoryPoints;
    private StatVictoryFrequency victoryFrequency;
    private StatDefeatFrequency defeatFrequency;
    private StatMoney moneyStats;

    public StatObject () {}

    /** Usernames */
    public ArrayList<String> getUsernames ()
    { return this.usernames; }

    public void setUsernames (ArrayList<String> usernames)
    { this.usernames = usernames; }

    /* Getters */
    public StatVictoryPoints getStatVictoryPoints ()
    { return this.statVictoryPoints; }

    public StatVictoryFrequency getVictoryFrequency ()
    { return this.victoryFrequency; }

    public StatDefeatFrequency getDefeatFrequency ()
    { return this.defeatFrequency; }

    public StatMoney getMoneyStats ()
    { return this.moneyStats; }
}
