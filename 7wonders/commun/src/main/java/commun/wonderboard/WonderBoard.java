package commun.wonderboard;

import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.cost.solver.LogTraceForTrade;
import commun.effect.AddingMaterialEffet;
import commun.effect.EffectList;
import commun.material.Material;
import log.GameLogger;

import java.util.LinkedList;
import java.util.List;

public class WonderBoard implements LogTraceForTrade
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

    /**
     * Renvoie true ou false si la carte est deja dans la wonderboard
     * @param cardName : le nom de la carte à ajoutée.
     * @return true ou false.
     */
    public boolean isAlreadyInBuilding(String cardName){
        for(Card card : building){
            if(card.getName().equals(cardName))
                return true;
        }
        return false;
    }

    public int countCard(CardType cardType){
        int sum = 0;
        for(Card card : this.building){
            if(card.getType() == cardType)
                sum++;
        }
        return sum;
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

    //pour acheter des carte a ressource
    @Override
    public String traceForTrade() {
        return "La merveille a produit";
    }

    @Override
    public Material[] getMaterialChoice() {
        return materialEffect.getChoiceMaterial();
    }

    @Override
    public boolean canBeUseForTrade() {
        return true;
    }

    public List<LogTraceForTrade> generateMaterialWithTrace(){
        List<LogTraceForTrade> list = new LinkedList<>();
        list.add(this);
        for(LogTraceForTrade card : building){
            list.add(card);
        }
        return list;
    }


}
