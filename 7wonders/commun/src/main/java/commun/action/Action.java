package commun.action;

/** Action est une classe qui permet a l'IA de faire une action */
public class Action
{
    /* Champs */
    private ActionType actionType;
    private int indexOfCard;

    /** Constructeur
     * @param actionType Le type de l'action
     * @param indexOfCard La position de la carte
     */
    public Action(ActionType actionType, int indexOfCard){
        this.indexOfCard = indexOfCard;
        this.actionType = actionType;
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
}
