package commun.communication;

public class StatModule
{
    private static StatObject inst = null;

    public static StatObject getInstance ()
    {
        if (inst == null)
        { inst = new StatObject(); }
        return inst;
    }

    public static void setInstance (StatObject statObject)
    { inst = statObject; }
}
