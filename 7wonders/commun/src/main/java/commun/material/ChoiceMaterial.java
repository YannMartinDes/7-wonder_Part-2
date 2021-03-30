package commun.material;
import commun.effect.IEffect;

/**
 * ChoiceMaterial est une classe qui permet le choix entre plusieurs materiaux
 */
public class ChoiceMaterial implements IEffect {
  
    /* Champs */
    private Material [] materials;
    
     /** Constructeur
     * @param materials Les materiaux a choix */
    public ChoiceMaterial(Material... materials){
        this.materials = materials;
    }

    public ChoiceMaterial(){}//JSON serialisation

    @Override
    public Material[] getMaterials() {
        return materials;
    }

}
