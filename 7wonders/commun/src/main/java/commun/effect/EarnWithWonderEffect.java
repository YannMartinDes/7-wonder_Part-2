package commun.effect;

public class EarnWithWonderEffect implements IEffect {

    private EarnWithWonder earnWithWonder;

    public EarnWithWonderEffect(EarnWithWonder earnWithWonder){
        this.earnWithWonder = earnWithWonder;
    }

    @Override
    public EarnWithWonder getEarnWithWonderEffect() {
        return earnWithWonder;
    }
}
