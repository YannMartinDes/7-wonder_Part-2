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
    private StatConflicts [] statConflicts;
    private StatCardBuilding statCardBuilding;
    private StatCardCommercialBuildings statCardCommercialBuildings;
    private StatCardManufacturedProducts statCardManufacturedProducts;
    private StatCardMilitaryBuildings statCardMilitaryBuildings;
    private StatCardRawMaterials statCardRawMaterials;
    private StatCardScientificBuildings statCardScientificBuildings;

    public StatObject ()
    {
        this.usernames = new ArrayList<String>();
        this.statVictoryPoints = new StatVictoryPoints();
        this.victoryFrequency = new StatVictoryFrequency();
        this.defeatFrequency = new StatDefeatFrequency();
        this.moneyStats = new StatMoney();
        this.statCardBuilding = new StatCardBuilding();
        this.statCardCommercialBuildings = new StatCardCommercialBuildings();
        this.statCardManufacturedProducts = new StatCardManufacturedProducts();
        this.statCardMilitaryBuildings = new StatCardMilitaryBuildings();
        this.statCardRawMaterials = new StatCardRawMaterials();
        this.statCardScientificBuildings = new StatCardScientificBuildings();
        // Nombre d'ages
        this.statConflicts = new StatConflicts [] {new StatConflicts(), new StatConflicts()};
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

    public StatCardBuilding getStatCardBuilding ()
    { return this.statCardBuilding; }

    public StatCardCommercialBuildings getStatCardCommercialBuildings ()
    { return this.statCardCommercialBuildings; }

    public StatCardManufacturedProducts getstatCardManufacturedProducts ()
    { return this.statCardManufacturedProducts; }

    public StatCardMilitaryBuildings getStatCardMilitaryBuildings ()
    { return this.statCardMilitaryBuildings; }

    public StatCardRawMaterials getStatCardRawMaterials ()
    { return this.statCardRawMaterials; }

    public StatCardScientificBuildings getStatCardScientificBuildings ()
    { return this.statCardScientificBuildings; }
}
