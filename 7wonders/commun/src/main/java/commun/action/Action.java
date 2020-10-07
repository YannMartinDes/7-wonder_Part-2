package commun.action;

import commun.wonderboard.WonderStep;

/** Action est une classe qui permet a l'IA de faire une action */
public class Action
{
    /* Champs */
    private ActionType actionType;
    private int indexOfCard;
    private boolean playJoker;

    public Action(ActionType actionType, int indexOfCard, Boolean playJoker){
    /** Constructeur
     * @param actionType Le type de l'action
     * @param indexOfCard La position de la carte
     * @param playedJoker vouloir jouer son joker de l'etape de la merveille ou non
     */
        this.indexOfCard = indexOfCard;
        this.actionType = actionType;
        this.playJoker = playJoker;
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

    public boolean isPlayJoker() {
        return playJoker;
    }

    public void setPlayJoker(boolean playJoker) {
        this.playJoker = playJoker;
    }

}
