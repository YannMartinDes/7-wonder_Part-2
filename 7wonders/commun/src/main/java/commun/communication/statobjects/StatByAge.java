package commun.communication.statobjects;

/** Classe qui represente les statistiques recuperables par age */
public class StatByAge
{
    private StatMoney moneyStats;
    private StatConflicts statConflict;
    private StatCard [] statCards;
    private StatVictoryPoints statVictoryPoints;
    private StatRessource [] statRessources;
    private StatWonderProgression statWonderProgression;
    private StatSoldCards statSoldCards;

    /** Constructeur */
    public StatByAge ()
    {
        this.statVictoryPoints = new StatVictoryPoints(1);
        this.moneyStats = new StatMoney(1);
        this.statCards = new StatCard [7]; // Nombre de types de cartes
        for (int i = 0; i < this.statCards.length; i++)
        { this.statCards[i] = new StatCard(1); }
        this.statRessources = new StatRessource[7];
        for (int i = 0; i < this.statRessources.length; i++)
        { this.statRessources[i] = new StatRessource(); }
        this.statWonderProgression = new StatWonderProgression();
        this.statSoldCards = new StatSoldCards();
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
        this.statRessources = new StatRessource[7];
        for (int i = 0; i < this.statRessources.length; i++)
        { this.statRessources[i] = new StatRessource(nbPlayers); }
        this.statWonderProgression = new StatWonderProgression(nbPlayers);
        this.statSoldCards = new StatSoldCards(nbPlayers);
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

    public StatRessource [] getStatRessources ()
    { return this.statRessources; }

    public StatRessource getStatRessources (int index)
    { return this.statRessources[index]; }

    public StatWonderProgression getStatWonderProgression ()
    { return this.statWonderProgression; }

    public StatSoldCards getStatSoldCards ()
    { return this.statSoldCards; }
}
