package simstation;

import mvc.*;

public class StatsCommand extends Command{
    public StatsCommand(Model model){
        super(model);
    }

    public void execute(){
        Simulation sim  = (Simulation) model;
        int totalAgents = sim.getAgents().size();
        int runningAgents = sim.getRunningAgents();
        Utilities.inform("Total Agents: " + totalAgents + "\nRunning Agents: " + runningAgents);
    }
}