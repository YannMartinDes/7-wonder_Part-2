package commun.wonderboard;

import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.effect.ChoiceMaterialEffect;
import commun.effect.EffectList;

/** Wonderboard est une classe qui represente le plateau de jeu d'un joueur */
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
public class WonderBoard
{
    /* Champs */
    private String wonderName;
    private Deck building;//Cartes construites par le joueur
    private ChoiceMaterialEffect choiceMaterialEffect;
    private int coin;//Argent du joueur.
    private Random r =new Random();
    private String face; //A ou B
    private List<WonderStep> wonderSteps;
    private int militaryPower; //Points de puissance militaire
    private BattlePoint battlePoint; //Points de conflits

    /** Constructeur
     * Représente une carte Merveille dans 7wonders
     * @param wonderName: Nom de la merveille */
    public WonderBoard(String wonderName, ChoiceMaterialEffect choiceMaterialEffect)
    {
        this.wonderName = wonderName;
        this.building = new Deck();
        this.choiceMaterialEffect = choiceMaterialEffect;
        this.coin = 3;//On commence le jeu avec 3 pièces
        this.battlePoint = new BattlePoint();
        this.wonderSteps = new ArrayList<>();
        this.face = randomFace();
    }

    /* Getters */
    public String getWonderName ()
    { return wonderName; }

    public Deck getBuilding ()
    { return building; }

    public  ChoiceMaterialEffect getMaterialEffect ()
    { return choiceMaterialEffect; }

    /** Ajoute une carte à la liste des batiments de la merveille.
     * @param card */
    public void addCardToBuilding (Card card)
    { getBuilding().addCard(card); }

    /** Permet de recuperer la liste de tout les effet du joueur
     * @return la liste des effet*/
    public EffectList getAllEffects ()
    {
        EffectList effects = new EffectList();
        effects.add(choiceMaterialEffect);
        for(int i = 0; i < building.getLength();i++)
        {
            effects.add(building.getCard(i).getCardEffect());
        }
        for(int i = 0; i < wonderSteps.size(); i++)
        {
            if (wonderSteps.get(i).getBuilt()){
                for(int j = 0; j < wonderSteps.get(i).getEffects().length;j++) {
                    effects.add(wonderSteps.get(i).getEffects()[j]);
                }
            }
        }
        return effects;
    }

    /** Renvoie true ou false si la carte est deja dans la wonderboard
     * @param cardName : le nom de la carte à ajoutée.
     * @return true ou false. */
    public boolean isAlreadyInBuilding (String cardName)
    {
        for(Card card : building)
        {
            if(card.getName().equals(cardName))
                return true;
        }
        return false;
    }

    /** countCard compte le nombre de cartes d'un certain type donne
     * @param cardType le type des cartes */
    public int countCard (CardType[] cardType)
    {
        int sum = 0;
        for(CardType type : cardType)
        {
            for(Card card : this.building)
            {
                if(card.getType() == type)
                    sum++;
            }
        }
        return sum;
    }

    /** Ajouter de la monnaie de jeu
     * @param coin Nombre de monnaie a ajouter */
    public void addCoin (int coin)
    { this.coin += coin; }

    /** Enlever de la monnaie de jeu
     * @param coin Nombre de monnaie a retirer */
    public void removeCoin (int coin)
    { this.coin -= coin; }

    /* Getters */
    public int getCoin ()
    { return this.coin; }

    // -------------------- Battle ---------------------------
    public int getConflictPoints ()
    { return battlePoint.getConflictPoints(); }

    /** Ajouter des points de conflits miitaires
     * @param conflictPoints Nombre de points a ajouter */
    public void addConflictPoints (int conflictPoints)
    { battlePoint.addToken(conflictPoints); }

    /** Retirer des points de conflits miitaires
     * @param conflictPoints Nombre de points a retirer */
    public void removeConflictPoints (int conflictPoints)
    { battlePoint.addToken(-conflictPoints); }

    /**
     * Permet de recuperer les element relative au battle
     * @return le battlePoint
     */
    public BattlePoint getBattlePoint(){
        return battlePoint;
    }

    /* Getters */
    public int getMilitaryPower ()
    { return militaryPower; }
    public List<WonderStep> getWonderSteps()
    {
        return wonderSteps;
    }


  
    /** Ajouter des points de puissance militaire
     * @param addedPower Nombre de points a ajouter */
    public void addMilitaryPower (int addedPower)
    { this.militaryPower += addedPower; }
    public void setWonderSteps(List<WonderStep> wonderSteps)
    {
        this.wonderSteps = wonderSteps;
    }

    public String getFace()
    {
        return face;
    }

    public String randomFace()
    {
        if(this.r.nextBoolean()) return  "B";
        return "A";
    }

    /**
     * Renvoie l'étapes de la merveille actuelle, null si toutes les étapes sont construites.
     * @return l'étape actuelle de la merveille
     */
    public WonderStep getCurrentStep(){
        for(WonderStep step : this.wonderSteps){
            if(!step.getBuilt()){
                return step;
            }
        }
        return null;
    }

    /**
     * Compte le nombre d'étapes construite
     * @return le nombre d'étapes de la merveille construite.
     */
    public int countStepBuild(){
        int i = 0;
        for(WonderStep step : this.wonderSteps){
            if(step.getBuilt())
                i++;
        }
        return i;


    public void resetWonderStepsJokers() {
        for (WonderStep wonderStep : this.wonderSteps) {
            wonderStep.setUsedJoker(false);
        }
    }

}
