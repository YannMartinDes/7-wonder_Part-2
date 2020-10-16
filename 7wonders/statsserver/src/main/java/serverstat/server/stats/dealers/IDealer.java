package serverstat.server.stats.dealers;

import java.util.ArrayList;

/** Interface qui permet de representer un Dealer */
public interface IDealer<T>
{
    /** Fonction qui permet de traiter les donnees et de renvoyer une ArrayList&lt;String&gt; */
    public ArrayList<String> deal (T data, Integer divisor);
}
