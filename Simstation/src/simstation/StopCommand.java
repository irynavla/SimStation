package simstation;

import mvc.Command;
import mvc.Model;

public class StopCommand extends Command {
    public StopCommand(Model model) {
        super(model);
    }

    public void execute() throws Exception {
        Simulation sim = (Simulation) model;
        sim.stop();
    }
}