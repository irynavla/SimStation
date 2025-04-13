package simstation;

import mvc.*;

public class StatsCommand extends Command{
    public StatsCommand(Model model){
        super(model);
    }

    public void execute(){
        World world = (World) model;
        int totalAgents = world.getAgents().size();
        int runningAgents = world.getRunningAgents();
        Utilities.inform("Total Agents: " + totalAgents + "\nRunning Agents: " + runningAgents);
    }
}