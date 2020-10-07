package commun.wonderboard;

import commun.card.Card;
import commun.cost.ICost;
import commun.effect.IEffect;

/**
 * cette classe represente une etape de la merveille , ses caractéristique sont :
 *
 *
 *      la carte qui marque sa construction
 *      le numero de l'etape dans la merveille
 *      son etat (built= true/false)
 *
 */
public class WonderStep
{

    private IEffect[] effects ;
    //les effets qu'elle produit

    private ICost cost ;
    //son cout

    private Card constructionMarker;
    //la carte qui marque sa construction

    private Boolean built;
    //son etat (built= true/false)

    private int stepNumber;
    //numero de l'etape de la merveille

    private boolean haveJoker;
    // effet qui permet de construire un batiment gratuitement

    private boolean isUsedJoker;
    // effet Joker utiliser ou pas

    private boolean playDiscardedCard;
    //effet jouer une carte deffausser

    private boolean canPlayLastCard;
    // effet jouer la derneiere carte au lieu de la defausser

    private boolean copyNeighborGuild;
    // effet copier la guile d'un voisin


    public WonderStep(ICost cost, int stepNumber, IEffect... effects)
    {
        this.effects = effects;
        this.cost = cost;
        this.built = false;
        this.stepNumber = stepNumber;
        this.haveJoker = false;
        this.isUsedJoker= false;
        this.playDiscardedCard = false;
        this.canPlayLastCard = false ;
        this.copyNeighborGuild = false;
    }

    public Card getConstructionMarker()
    {
        return constructionMarker;
    }

    public void setConstructionMarker(Card constructionMarker)
    {
        this.constructionMarker = constructionMarker;
    }

    public ICost getCost() {
        return cost;
    }

    public void setCost(ICost cost) {
        this.cost = cost;
    }

    public IEffect[] getEffects() {
        return effects;
    }


    public void toBuild(){
        this.built = true;
    }

    public Boolean getBuilt() {
        return built;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }


    public boolean isUsedJoker() {
        return isUsedJoker;
    }

    public void setUsedJoker(boolean usedJoker) {
        isUsedJoker = usedJoker;
    }


    public boolean isHaveJoker() {
        return haveJoker;
    }

    public void setHaveJoker(boolean haveJoker) {
        this.haveJoker = haveJoker;
    }

    public boolean isPlayDiscardedCard() {
        return playDiscardedCard;
    }

    public void setPlayDiscardedCard(boolean playDiscardedCard) {
        this.playDiscardedCard = playDiscardedCard;
    }

    public boolean isCanPlayLastCard() {
        return canPlayLastCard;
    }

    public void setCanPlayLastCard(boolean canPlayLastCard) {
        this.canPlayLastCard = canPlayLastCard;
    }


    public boolean isCopyNeighborGuild() {
        return copyNeighborGuild;
    }

    public void setCopyNeighborGuild(boolean copyNeighborGuild) {
        this.copyNeighborGuild = copyNeighborGuild;
    }

}
