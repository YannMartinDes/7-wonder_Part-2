package commun.material;

public class ChoiceMaterial {
    private final Material material1;
    private final Material material2;

    public ChoiceMaterial(Material material1, Material material2){
        this.material1 = material1;
        this.material2 = material2;
    }

    public Material getMaterial1() {
        return material1;
    }

    public Material getMaterial2() {
        return material2;
    }
}
