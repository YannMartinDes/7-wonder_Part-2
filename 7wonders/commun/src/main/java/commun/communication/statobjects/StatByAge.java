package commun.communication.statobjects;

/** Classe qui represente les statistiques recuperables par age */
public class StatByAge
{
    private StatMoney moneyStats;
    private StatConflicts statConflict;
    private StatCard [] statCards;
    private StatVictoryPoints statVictoryPoints;

    /** Constructeur */
    public StatByAge ()
    {
        this.statVictoryPoints = new StatVictoryPoints(1);
        this.moneyStats = new StatMoney(1);
        this.statCards = new StatCard [7]; // Nombre de types de cartes
        for (int i = 0; i < this.statCards.length; i++)
        { this.statCards[i] = new StatCard(1); }
    }

    /** Bypass Jackson */
    public StatByAge (int nbPlayers)
    {
        this.moneyStats = new StatMoney(nbPlayers);
        this.statCards = new StatCard [7]; // Nombre de types de cartes
        for (int i = 0; i < this.statCards.length; i++)
        { this.statCards[i] = new StatCard(nbPlayers); }
        this.statVictoryPoints = new StatVictoryPoints(nbPlayers);
        this.statConflict = new StatConflicts(nbPlayers);
    }

    /* Getters */

    public StatVictoryPoints getStatVictoryPoints ()
    { return this.statVictoryPoints; }

    public StatMoney getMoneyStats ()
    { return this.moneyStats; }

    public StatConflicts getStatConflict ()
    { return this.statConflict; }

    public StatCard [] getStatCards ()
    { return this.statCards; }

    public StatCard getStatCards (int index)
    { return this.statCards[index]; }
}
