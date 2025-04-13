package simstation;

import mvc.Command;
import mvc.Model;


public class ResumeCommand extends Command {

    public ResumeCommand(Model model) { //resume constructor
        super(model); //super
    }

    @Override //Override from command
    public void execute() { //execute method
        Simulation simulation = (Simulation) model; //set the simulation
        simulation.resume(); //resume the simulation
    }
}
