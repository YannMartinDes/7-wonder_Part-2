package commun.material;

/** Matieral est une classe qui represente un materiau */
public class Material
{
    /* Champs */
    private int number; //Nombre de ressource.
    private MaterialType type;//Type de matériel

    /** Constructeur
     * @param type Le type du materiau
     * @param number Le nombre d'occurence */
    public Material (MaterialType type, int number)
    {
        this.type = type;
        this.number = number;
    }

    public Material(){}//JSON serialisation

    /* Getters */
    public int getNumber ()
    { return number; }

    public MaterialType getType ()
    { return type; }
}
