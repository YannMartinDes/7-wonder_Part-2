package commun.player;


import commun.wonderboard.WonderBoard;
import log.GameLogger;

public class PlayerVisible {

    /* Fields */
    protected final String name;
    protected WonderBoard wonderBoard;


    /** Constructeur */
    public PlayerVisible (String name, WonderBoard wondersBoard)
    {
        this.name = name;
        this.setWonderBoard(wondersBoard);
    }

    public PlayerVisible(String name)
    { this(name, null); }

    /* Getters - Setters */

    public String getName ()
    { return name; }

    public WonderBoard getWonderBoard ()
    { return wonderBoard; }


    /**
     * @param wondersBoard the wondersBoard to set
     */
    public void setWonderBoard (WonderBoard wondersBoard)
    { this.wonderBoard = wondersBoard; }


    public void information(){
        GameLogger.getInstance().log("Pièces : "+getWonderBoard().getCoin());
        GameLogger.getInstance().log("Puissance millitaire : "+getWonderBoard().getMilitaryPower());
        GameLogger.getInstance().log("Jetons conflits : "+getWonderBoard().getConflictPoints());
        GameLogger.getInstance().log("Constructions :");
        GameLogger.getInstance().log(getWonderBoard().getBuilding().toString());
    }
}
