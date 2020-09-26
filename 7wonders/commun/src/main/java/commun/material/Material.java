package commun.material;

public class Material {
    private final int number; //Nombre de ressource.
    private final MaterialType type;//Type de matériel

    public Material(MaterialType type, int number){
        this.type = type;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public MaterialType getType() {
        return type;
    }
}
