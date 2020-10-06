package commun.communication.statobjects;

/** Interface qui represente l'ajout d'une statistique aux statistiques */
public interface IStat<T>
{
    /** Addition d'une statistiques */
    public void add (T arrayList);
}
