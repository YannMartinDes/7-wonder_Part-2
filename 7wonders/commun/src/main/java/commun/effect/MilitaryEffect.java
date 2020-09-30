package commun.effect;

public class MilitaryEffect implements  IEffect{
    private int nbEffect;

    public MilitaryEffect(int nbEffect)
    {
        this.nbEffect = nbEffect;
    }

    @Override
    public int getMilitaryEffect()
    {
        return nbEffect;
    }
}
