package commun.effect;

public class EarnWithWonderEffect implements IEffect {

    private EarnWithWonder earnWithWonder;

    public EarnWithWonderEffect(EarnWithWonder earnWithWonder){
        this.earnWithWonder = earnWithWonder;
    }

    public  EarnWithWonderEffect(){}

    @Override
    public EarnWithWonder getEarnWithWonderEffect() {
        return earnWithWonder;
    }
}
