package PrisonerDilemma;

import simstation.*;
import java.util.HashMap;
import java.util.Map;

public abstract class PrisonersDilemmaTournament extends Simulation {
    private Map<String, Integer> strategyFitness = new HashMap<>();

    public PrisonersDilemmaTournament() {
        super();
    }



    @Override
    public String[] getStats() {
        // Format each strategy and its corresponding fitness into an array of strings
        String[] stats = new String[strategyFitness.size()];
        int i = 0;
        for (Map.Entry<String, Integer> entry : strategyFitness.entrySet()) {
            stats[i++] = entry.getKey() + ": " + entry.getValue();
        }
        return stats;
    }


}
