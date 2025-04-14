package plague;


import mvc.Command;
import mvc.Model;
import mvc.View;
import simstation.SimStationFactory;


public class PlagueFactory extends SimStationFactory {
    public Model makeModel() {
        return new PlagueSimulation();
    }


    public String getTitle() {
        return "SimStation Plague";
    }


    public View makeView(Model model) { return new PlagueView(model); }
}
