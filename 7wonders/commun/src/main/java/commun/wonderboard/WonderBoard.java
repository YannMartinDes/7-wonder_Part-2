package commun.wonderboard;

import commun.card.Card;
import commun.card.Deck;
import commun.effect.AddingMaterialEffet;
import commun.effect.EffectList;
import commun.material.Material;
import log.GameLogger;

public class WonderBoard
{
    private String wonderName;
    private Deck building;//Cartes construites par le joueur
    private AddingMaterialEffet materialEffect;
    private int coin;//Argent du joueur.

    /**
     * Représente une carte Merveille dans 7wonders
     * @param wonderName: Nom de la merveille
     */
    public WonderBoard(String wonderName, AddingMaterialEffet materialEffect)
    {
        this.wonderName = wonderName;
        this.building = new Deck();
        this.materialEffect = materialEffect;
        this.coin = 3;//On commence le jeu avec 3 pièces
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
        if(card.getCardEffect().getNumberOfCoin()!=0){
            GameLogger.log("Vous avez gagner "+card.getCardEffect().getNumberOfCoin()+" pieces pour avoir construit ce batiment.");
        }
    }


    /**
     * Permet de recuperer la liste de tout les effet du joueur
     * @return la liste des effet
     */
    public EffectList getAllEffects()
    {
        EffectList effects = new EffectList();
        effects.add(materialEffect);
        for(int i = 0; i<building.getLength();i++){
            effects.add(building.getCard(i).getCardEffect());
        }
        return effects;
    }

    public void addCoin(int coin){
        this.coin += coin;
    }

    public void removeCoin(int coin){
        this.coin -= coin;
    }

    public int getCoin(){
        return this.coin;
    }
}
