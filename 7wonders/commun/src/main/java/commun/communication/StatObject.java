package commun.communication;

import commun.card.CardType;
import commun.communication.statobjects.*;

import java.util.ArrayList;

public class StatObject
{
    private ArrayList<String> usernames;
    private StatVictoryPoints statVictoryPoints;
    private StatVictoryFrequency victoryFrequency;
    private StatDefeatFrequency defeatFrequency;
    private StatMoney moneyStats;
    private StatConflicts [] statConflicts;
    private StatCard [] statCards;

    public StatObject ()
    {
        this.usernames = new ArrayList<String>();
        this.statVictoryPoints = new StatVictoryPoints(1);
        this.victoryFrequency = new StatVictoryFrequency(1);
        this.defeatFrequency = new StatDefeatFrequency(1);
        this.moneyStats = new StatMoney(1);
        this.statCards = new StatCard [6]; // Nombre de types de cartes
        for (int i = 0; i < this.statCards.length; i++)
        { this.statCards[i] = new StatCard(1); }
        this.statConflicts = new StatConflicts [2]; // Nombre d'ages
        for (int i = 0; i < this.statConflicts.length; i++)
        { this.statConflicts[i] = new StatConflicts(1); }}

    /** Bypass Jackson */
    public void construct (int nbPlayers)
    {
        this.usernames = new ArrayList<String>(nbPlayers);
        this.statVictoryPoints = new StatVictoryPoints(nbPlayers);
        this.victoryFrequency = new StatVictoryFrequency(nbPlayers);
        this.defeatFrequency = new StatDefeatFrequency(nbPlayers);
        this.moneyStats = new StatMoney(nbPlayers);
        this.statCards = new StatCard [6]; // Nombre de types de cartes
        for (int i = 0; i < this.statCards.length; i++)
        { this.statCards[i] = new StatCard(nbPlayers); }
        this.statConflicts = new StatConflicts [2]; // Nombre d'ages
        for (int i = 0; i < this.statConflicts.length; i++)
        { this.statConflicts[i] = new StatConflicts(nbPlayers); }
    }

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

    public StatConflicts [] getStatConflicts ()
    { return this.statConflicts; }

    public StatConflicts getStatConflics (int index)
    { return this.statConflicts[index]; }

    public StatCard [] getStatCards ()
    { return this.statCards; }

    public StatCard getStatCards (int index)
    { return this.statCards[index]; }
}
