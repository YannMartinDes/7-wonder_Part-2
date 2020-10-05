package commun.card;

public enum CardType {

    CIVIL_BUILDING (0),
    RAW_MATERIALS (1),
    MANUFACTURED_PRODUCTS (2),
    SCIENTIFIC_BUILDINGS (3),
    MILITARY_BUILDINGS (4),
    COMMERCIAL_BUILDINGS (5),
    GUILD_BUILDINGS(6);

    private int index;

    private CardType (int index)
    { this.index = index; }

    public int getIndex ()
    { return this.index; }
}
