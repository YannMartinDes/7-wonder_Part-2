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

    public WonderStep(ICost cost, int stepNumber, IEffect... effects)
    {
        this.effects = effects;
        this.cost = cost;
        this.built = false;
        this.stepNumber = stepNumber;
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
}
