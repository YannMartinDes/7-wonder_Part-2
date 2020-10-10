package commun.communication;

import commun.card.CardType;
import commun.communication.statobjects.*;

import java.util.ArrayList;

/** StatObject represente l'ensemble des statistiques, il est
 * envoye au serveur de statistiques pour analyse */
public class StatObject
{
    /* Champs */
    private ArrayList<String> usernames;
    private StatVictoryFrequency victoryFrequency;
    private StatDefeatFrequency defeatFrequency;

    private StatByAge [] statByAge;
    private int currentAge;

    /** Constructeur */
    public StatObject ()
    {
        this.usernames = new ArrayList<String>();
        this.victoryFrequency = new StatVictoryFrequency(1);
        this.defeatFrequency = new StatDefeatFrequency(1);

        this.statByAge = new StatByAge[3]; // Nombre d'ages
        for (int i = 0; i < this.statByAge.length; i++) {
            this.statByAge[i] = new StatByAge();
        }
        this.currentAge = 0;
    }

    /** Bypass Jackson */
    public void construct (int nbPlayers)
    {
        this.usernames = new ArrayList<String>(nbPlayers);
        this.victoryFrequency = new StatVictoryFrequency(nbPlayers);
        this.defeatFrequency = new StatDefeatFrequency(nbPlayers);

        this.statByAge = new StatByAge[3];
        for (int i = 0; i < this.statByAge.length; i++)
        { this.statByAge[i] = new StatByAge(nbPlayers); }
        this.currentAge = 0;
    }

    /* Getters - Setters */

    public ArrayList<String> getUsernames ()
    { return this.usernames; }

    public void setUsernames (ArrayList<String> usernames)
    { this.usernames = usernames; }

    public StatVictoryFrequency getVictoryFrequency ()
    { return this.victoryFrequency; }

    public StatDefeatFrequency getDefeatFrequency ()
    { return this.defeatFrequency; }

    public StatByAge [] getStatByAge ()
    { return this.statByAge; }

    public StatByAge getStatByAge (int index)
    { return this.statByAge[index]; }

    public void incrementAge ()
    { this.currentAge++; }

    public int getCurrentAge ()
    { return this.currentAge; }
}
