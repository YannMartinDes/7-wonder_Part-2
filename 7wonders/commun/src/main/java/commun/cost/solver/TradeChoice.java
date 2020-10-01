package commun.cost.solver;

import commun.action.LogTrade;
import commun.cost.MaterialCost;
import commun.material.MaterialType;

public class TradeChoice {

    private LogTrade logTrade;
    private MaterialType type;
    private int numberMat;
    private MaterialCost currentMaterialCoste;

    TradeChoice(LogTrade logTrade,
            MaterialType type,
            int numberMat,
            MaterialCost currentMaterialCoste){
        this.logTrade = logTrade;
        this.type = type;
        this. numberMat = numberMat;
        this.currentMaterialCoste = currentMaterialCoste;
    }


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

    public MaterialCost getCurrentMaterialCoste() {
        return currentMaterialCoste;
    }

    public void setCurrentMaterialCoste(MaterialCost currentMaterialCoste) {
        this.currentMaterialCoste = currentMaterialCoste;
    }
}
