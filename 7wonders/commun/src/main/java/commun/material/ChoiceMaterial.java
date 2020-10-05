package commun.material;

/** ChoiceMaterial est une classe qui permet le choix entre plusieurs materiaux */
public class ChoiceMaterial
{
    /* Champs */
    private Material [] materials;

    /** Constructeur
     * @param materials Les materiaux a choix */
    public ChoiceMaterial (Material... materials)
    { this.materials = materials; }

    public Material[] getMaterials ()
    { return materials; }
}
