package serverstat.server.stats.dealers;

import java.util.ArrayList;

public interface IDealer<T>
{
    public ArrayList<String> deal (ArrayList<T> victoryPointsStats, Integer divisor);
    public ArrayList<String> deal (ArrayList<T> victoryPointsStat);
}
