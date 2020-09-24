package commun.card;

public class WonderBoard
{
    private String wonderName;
    private Deck building;

    /**
     * Représente une carte Merveille dans 7wonders
     * @param wonderName: Nom de la merveille
     */
    public WonderBoard(String wonderName)
    {
        this.wonderName = wonderName;
        this.building = new Deck();
    }

    public String getWonderName()
    {
        return wonderName;
    }

    public Deck getBuilding()
    {
        return building;
    }

    /**
     * Ajoute une carte à la liste des batiments de la merveille.
     * @param card
     */
    public void addCardToBuilding(Card card)
    {
        getBuilding().addCard(card);
    }
}
