package plague;

import simstation.*;
import mvc.*;

public class PlagueSimulation extends Simulation {
    public static int VIRULENCE = 50;  // This can still be controlled through the sliders
    public static int RESISTANCE = 2; // You may want to make this adjustable as well, but for now it's static

    // Remove hardcoded values and make them dynamic based on slider input
    private int initialInfected;
    private int initialPopulation;
    private int fatalTime;
    private boolean isFatal;

    @Override
    public void populate() {
        int numInfected = (int)((initialInfected / 100.0) * initialPopulation);

        for (int i = 0; i < initialPopulation; i++) {
            boolean infected = i < numInfected;
            Host h = new Host(infected);
            h.setRecoveryTime(fatalTime);
            h.setFatal(isFatal);
            addAgent(h);
        }
    }

    public String[] getStats() {
        return new String[]{
                "#agents = " + getAgents().size(),
                "clock = " + getClock(),
                "% infected = " + String.format("%.2f", getInfected())
        };
    }

    public double getInfected() {
        int i = 0;
        for (Agent a : agents) {
            if (((Host) a).infected) i++;
        }
        return 100.0 * i / agents.size();
    }

    // Setters called from PlaguePanel
    public void setInitialInfected(int percent) {
        this.initialInfected = percent;
    }

    public void setInitialPopulation(int size) {
        this.initialPopulation = size;
    }

    public void setFatalTime(int time) {
        this.fatalTime = time;
    }

    public void setIsFatal(boolean fatal) {
        this.isFatal = fatal;
    }

    public static void main(String[] args) {
        SimulationPanel panel = new PlaguePanel(new PlagueFactory());
        panel.display();
    }
}
