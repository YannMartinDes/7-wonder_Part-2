package commun.cost.solver;

import commun.material.Material;

import java.util.List;

public interface LogTraceForTrade {
    public String traceForTrade();
    public Material[] getMaterialChoice();
    public boolean canBeUseForTrade();
}
