package serverstat.server.stats.dealers;

import java.util.ArrayList;

public abstract class DealerBase
{
    protected String title;

    protected DealerBase (String title)
    { this.title = title; }

    /** Integer */
    public ArrayList<String> deal (ArrayList<Integer> arrayList, Integer divisor)
    {
        ArrayList<String> output = new ArrayList<String>();

        output.add(this.title);
        for (Integer integer : arrayList)
        {
            output.add(Double.toString((double) integer / divisor));
        }
        return output;
    }
}
