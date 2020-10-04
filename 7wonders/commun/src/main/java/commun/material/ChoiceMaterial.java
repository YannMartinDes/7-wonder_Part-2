package commun.material;

/**
 * choix entre plusieurs materiels
 */
public class ChoiceMaterial {

    private Material [] materials;


    public ChoiceMaterial(Material... materials){
        this.materials = materials;
    }


    public Material[] getMaterials() {

        return materials;
    }

}
