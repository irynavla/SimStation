package simstation;

import mvc.Command;
import mvc.Model;

public class SuspendCommand extends Command {
    public SuspendCommand(Model model) {
        super(model);
    }

    public void execute() {
        Simulation sim = (Simulation) model;
        sim.suspend();
    }
}
