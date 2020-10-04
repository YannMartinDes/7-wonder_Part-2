package commun.material;

public class ChoiceMaterial {
    private final Material[] materials;

    public ChoiceMaterial(Material... materials){
        this.materials = materials;
    }

    public Material[] getMaterial() {
        return materials;
    }

}
