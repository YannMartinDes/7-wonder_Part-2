package commun.material;

/** Matieral est une classe qui represente un materiau */
public class Material
{
    /* Champs */
    private final int number; //Nombre de ressource.
    private final MaterialType type;//Type de matériel

    /** Constructeur
     * @param type Le type du materiau
     * @param number Le nombre d'occurence */
    public Material (MaterialType type, int number)
    {
        this.type = type;
        this.number = number;
    }

    /* Getters */
    public int getNumber ()
    { return number; }

    public MaterialType getType ()
    { return type; }
}
