package PrisonerDilemma;

import mvc.Utilities;
import simstation.*;

public abstract class Prisoner extends Agent{
    private int fitness = 0;
    private boolean partnerCheated = false;
    private Strategy strategy;

    // Constructor that accepts a strategy
    public Prisoner(String name, Strategy strategy) {
        super(name);
        this.strategy = strategy;
    }

    public int getFitness() {
        return fitness;
    }

    public void updateFitness(int value) {
        this.fitness += value;
    }

    public boolean cooperate() {
        return strategy.cooperate(this); // Delegate cooperation decision to strategy
    }

    public void setPartnerCheated(boolean cheated) {
        this.partnerCheated = cheated;
    }

    public boolean getPartnerCheated() {
        return this.partnerCheated;
    }

    public Strategy getStrategy() {
        return this.strategy;
    }

}
