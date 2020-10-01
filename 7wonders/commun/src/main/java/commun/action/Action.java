package commun.action;

public class Action {
    private ActionType actionType;
    private int indexOfCard;

    public Action(ActionType actionType, int indexOfCard){
        this.indexOfCard = indexOfCard;
        this.actionType = actionType;
    }


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
