package commun.communication;

public class StatModule
{
    private static StatObject inst = null;

    public StatObject getInstance ()
    {
        if (inst == null)
        { inst = new StatObject(); }
        return inst;
    }

    public void setInstance (StatObject statObject)
    { inst = statObject; }
}
