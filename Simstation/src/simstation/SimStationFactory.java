package simstation;

import mvc.AppFactory;
import mvc.Command;
import mvc.Model;
import mvc.View;

public abstract class SimStationFactory implements AppFactory {
    public abstract Model makeModel();
    public abstract String getTitle();
    public abstract View makeView(Model model);

    public String[] getHelp() {
        return new String[] {
                "Click Start to start simulation",
                "Click Suspend to pause simulation",
                "Click Stop to stop simulation",
                "Click Stats to show sim info"
        };
    }

    public String about() {
        return "Simulation version 1.0";
    }

    public String[] getEditCommands() {
        return new String[] {"Start","Pause","Resume","Stop","Stats"};
    }

    public Command makeEditCommand(Model model, String type, Object source) {
        if (type.equalsIgnoreCase("Start")) {
            return new StartCommand(model);
        }
        else if (type.equalsIgnoreCase("Pause")) {
            return new SuspendCommand(model);
        }
        else if (type.equalsIgnoreCase("Resume")) {
            return new ResumeCommand(model);
        }
        else if (type.equalsIgnoreCase("Stop")) {
            return new StopCommand(model);
        }
        else if (type.equalsIgnoreCase("Stats")) {
            return new StatsCommand(model);
        }
        else {
            return null;
        }
    }
}