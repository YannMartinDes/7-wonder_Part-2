package commun.wonderboard;

import commun.card.Card;
import commun.card.Deck;
import commun.effect.AddingMaterialEffet;
import commun.material.Material;

public class WonderBoard
{
    private String wonderName;
    private Deck building;
    private AddingMaterialEffet materialEffect;

    /**
     * Représente une carte Merveille dans 7wonders
     * @param wonderName: Nom de la merveille
     */
    public WonderBoard(String wonderName, AddingMaterialEffet materialEffect)
    {
        this.wonderName = wonderName;
        this.building = new Deck();
        this.materialEffect = materialEffect;
    }

    public String getWonderName()
    {
        return wonderName;
    }

    public Deck getBuilding()
    {
        return building;
    }

    public AddingMaterialEffet getMaterialEffect(){
        return materialEffect;
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
