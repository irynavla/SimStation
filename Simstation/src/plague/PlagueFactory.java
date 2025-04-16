package plague;

import mvc.Model;
import mvc.View;
import simstation.SimStationFactory;

public class PlagueFactory extends SimStationFactory {
    public Model makeModel() {
        return new PlagueSimulation();
    }

    public String getTitle() {
        return "Plague";
    }

    public View makeView(Model model) {
        return new PlagueView(model); }

}