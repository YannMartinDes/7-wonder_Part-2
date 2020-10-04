package commun.cost.solver;

import commun.material.MaterialType;

public class TradeTrace {

    private String logTrade;
    private MaterialType type;
    private int numberMat;
    private RessourceLocalisation loc;

    public TradeTrace(String logTrade, MaterialType type, int numberMat)
    {
        this.logTrade = logTrade;
        this.type = type;
        this. numberMat = numberMat;
    }


    public String getLogTrade() {
        return logTrade;
    }

    public void setLogTrade(String logTrade) {
        this.logTrade = logTrade;
    }

    public MaterialType getType() {
        return type;
    }

    public void setType(MaterialType type) {
        this.type = type;
    }

    public int getNumberMat() {
        return numberMat;
    }

    public void setNumberMat(int numberMat) {
        this.numberMat = numberMat;
    }

}
