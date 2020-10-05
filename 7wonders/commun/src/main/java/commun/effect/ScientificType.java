package commun.effect;

/** Enumeration des types des cartes scientifiques */
public enum ScientificType
{
    /* Champs */
    GEOMETRY("Géometrie"),
    GEOGRAPHY("Géographie"),
    LITERATURE("Littérature");

    private String name;

    /* Getters */
    public String getName ()
    { return name; }

    /** Constructeur
     * @param name Le nom de la science */
    ScientificType (String name)
    { this.name = name; }




}
