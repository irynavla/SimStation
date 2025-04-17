package Prisoner_Dilemma;

import simstation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class PrisonersDilemmaTournament extends Simulation {
    private List<Prisoner> prisoners = new ArrayList<>();

    @Override
    public void populate() throws Exception {
        // Create 10 prisoners for each strategy (cheat, cooperate, etc.)
        for (int i = 0; i < 10; i++) {
            prisoners.add(new Prisoner("CheatPrisoner-" + i, new Cheat()));       // Cheat strategy
            prisoners.add(new Prisoner("CooperatePrisoner-" + i, new Cooperate())); // Cooperate strategy
            prisoners.add(new Prisoner("RandomPrisoner-" + i, new Randomly()));    // Random strategy
            prisoners.add(new Prisoner("TitForTatPrisoner-" + i, new Tit4Tat()));   // TitForTat strategy
        }

        // Add all prisoners to the simulation
        for (Prisoner prisoner : prisoners) {
            addAgent(prisoner);
        }
    }

    @Override
    public String[] getStats() {
        // Calculate average fitness for each strategy
        int totalFitnessCheat = 0, totalFitnessCooperate = 0, totalFitnessRandom = 0, totalFitnessTitForTat = 0;
        int countCheat = 0, countCooperate = 0, countRandom = 0, countTitForTat = 0;

        for (Prisoner prisoner : prisoners) {
            if (prisoner.getStrategy() instanceof Cheat) {
                totalFitnessCheat += prisoner.getFitness();
                countCheat++;
            } else if (prisoner.getStrategy() instanceof Cooperate) {
                totalFitnessCooperate += prisoner.getFitness();
                countCooperate++;
            } else if (prisoner.getStrategy() instanceof Randomly) {
                totalFitnessRandom += prisoner.getFitness();
                countRandom++;
            } else if (prisoner.getStrategy() instanceof Tit4Tat) {
                totalFitnessTitForTat += prisoner.getFitness();
                countTitForTat++;
            }
        }

        return new String[]{
                "Cheat Strategy Avg Fitness: " + (countCheat == 0 ? 0 : totalFitnessCheat / countCheat),
                "Cooperate Strategy Avg Fitness: " + (countCooperate == 0 ? 0 : totalFitnessCooperate / countCooperate),
                "Random Strategy Avg Fitness: " + (countRandom == 0 ? 0 : totalFitnessRandom / countRandom),
                "TitForTat Strategy Avg Fitness: " + (countTitForTat == 0 ? 0 : totalFitnessTitForTat / countTitForTat)
        };
    }

}
