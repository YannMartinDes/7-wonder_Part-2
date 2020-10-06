package commun.material;

/** Enumeration des types de materiaux */
public enum MaterialType {
    WOOD(0),CLAY(1),STONE(2),
    ORES(3),GLASS(4),PAPYRUS(5),
    FABRIC(6);

    private final int index;
    private MaterialType(int index)
    {
        this.index = index;
    }


    public int getIndex() {
        return index;
    }

}
