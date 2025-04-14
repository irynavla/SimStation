package plague;


import mvc.Utilities;
import simstation.Agent;
import simstation.Simulation;
import simstation.SimulationPanel;


public class PlagueSimulation extends Simulation {
    public static int VIRULENCE = 50;
    public static int RESISTANCE = 2;


    @Override
    public void populate() {
        for(int i = 0; i < 50; i++) {
            boolean infected = Utilities.rng.nextFloat() * 100 > VIRULENCE;
            addAgent(new Host(infected));
        }
    }


    public String[] getStats() {
        return new String[]{" #agents = " + getAgents().size(), "clock = " + getClock(), "% infected = " + getInfected()};
    }


    public Double getInfected() {
        int i = 0;
        for (Agent a : agents) {
            if (((Host)a).infected) i++;
        }
        return (double) i / agents.size() * 100;
    }


    public static void main(String[] args) {
        SimulationPanel panel = new SimulationPanel(new PlagueFactory());
        panel.display();
    }
}



