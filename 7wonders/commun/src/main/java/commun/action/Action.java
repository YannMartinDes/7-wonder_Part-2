package commun.action;

import commun.wonderboard.WonderStep;

/** Action est une classe qui permet a l'IA de faire une action */
public class Action
{
    /* Champs */
    private ActionType actionType;
    private int indexOfCard;


    private WonderStep wonderStep;

    public Action(ActionType actionType, int indexOfCard, WonderStep wonderStep){
    /** Constructeur
     * @param actionType Le type de l'action
     * @param indexOfCard La position de la carte
     */
        this.indexOfCard = indexOfCard;
        this.actionType = actionType;
        this.wonderStep = wonderStep;
    }

    /* Getters - Setters */

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public int getIndexOfCard() {
        return indexOfCard;
    }

    public void setIndexOfCard(int indexOfCard) {
        this.indexOfCard = indexOfCard;
    }

    public WonderStep getWonderStep() {
        return wonderStep;
    }

    public void setWonderStep(WonderStep wonderStep) {
        this.wonderStep = wonderStep;
    }

}
