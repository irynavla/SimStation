package Prisoner_Dilemma;

import mvc.Utilities;
import simstation.*;

import java.awt.*;

public class Prisoner extends Agent {
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
        return strategy.cooperate(this);  // Delegate cooperation decision to strategy
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

    @Override
    public void update() {
// Get a random neighbor using the getNeighbor method from the Simulation class
        Prisoner neighbor = (Prisoner) getSimulation().getNeighbor(this, 10);
        if (neighbor != null) {
            boolean myMove = cooperate(); // Decide whether to cooperate or cheat
            boolean theirMove = neighbor.cooperate(); // Neighbor's decision

            // Reward matrix based on moves
            setPartnerCheated(!theirMove);  // Track if the neighbor cheated
            neighbor.setPartnerCheated(!myMove);  // Track if this prisoner cheated

            // Update fitness based on Prisoner's Dilemma rules
            if (myMove && theirMove) {
                updateFitness(3);  // Both cooperate
                neighbor.updateFitness(3);  // Both cooperate
            } else if (!myMove && theirMove) {
                updateFitness(5);  // This prisoner cheats, neighbor cooperates
                neighbor.updateFitness(0);  // Neighbor cooperates, but this one cheats
            } else if (myMove && !theirMove) {
                updateFitness(0);  // This prisoner cooperates, neighbor cheats
                neighbor.updateFitness(5);  // Neighbor cheats, but this one cooperates
            } else {
                updateFitness(1);  // Both cheat
                neighbor.updateFitness(1);  // Both cheat
            }
        }

        // Try to move randomly and catch any potential InterruptedException
        try {
            move(5);  // Move randomly within a range
        } catch (InterruptedException e) {
            e.printStackTrace();  // Handle the exception or log it
        }
    }

    @Override
    public void onExit() {
        // Implement the logic that happens when the prisoner exits
        System.out.println(getName() + " has exited.");
        // You could add any cleanup or additional logic here if needed
    }

    @Override
    public void onInterrupted() {
        // Handle the interruption. You could log it, reset values, or do something else.
        System.out.println("Agent " + getName() + " was interrupted.");
        // Optionally, you could add custom handling logic here, like stopping the agent or changing its state.
    }

    @Override
    public void onStart() {
        // Initialize the agent's state or perform any setup here
        System.out.println("Agent " + getName() + " has started.");
        // Any other initialization or setup logic goes here.
    }

    public Color getColor() {
        if (strategy instanceof Cheat) {
            return Color.RED; // Color for Cheat strategy
        } else if (strategy instanceof Cooperate) {
            return Color.GREEN; // Color for Cooperate strategy
        } else if (strategy instanceof Tit4Tat) {
            return Color.BLUE; // Color for Tit-for-Tat strategy
        } else if (strategy instanceof Randomly) {
            return Color.MAGENTA; // Color for Random strategy
        }
        return Color.BLACK; // Default color if no match
    }


}
