package commun.effect;

public enum ScientificType {
    GEOMETRY(1), GEOGRAPHY(2), LITERATURE(3);
    private int index;

    public int getIndex() {
        return index;
    }

    ScientificType(int i) {
        this.index = i;
    }


}
