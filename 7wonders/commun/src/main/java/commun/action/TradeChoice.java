package commun.action;

import commun.material.Material;
import commun.material.MaterialType;

public class TradeChoice {

    private LogTrade logTrade;
    private MaterialType type;
    private int numberMat;

    public LogTrade getLogTrade() {
        return logTrade;
    }

    public void setLogTrade(LogTrade logTrade) {
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
