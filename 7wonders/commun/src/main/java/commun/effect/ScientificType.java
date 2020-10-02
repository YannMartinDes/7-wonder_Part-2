package commun.effect;

public enum ScientificType {
    GEOMETRY("Géometrie"), GEOGRAPHY("Géographie"), LITERATURE("Littérature");
    private String name;

    public String getName(){return name;}

    ScientificType(String name) {
        this.name = name;
    }




}
