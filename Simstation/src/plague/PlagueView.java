package plague;


import mvc.Model;
import simstation.Agent;
import simstation.SimulationView;
import java.awt.*;


public class PlagueView extends SimulationView {
    public PlagueView(Model model) {
        super(model);
    }


    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);


        for (Agent agent : sim.getAgents()) {
            if (((Host) agent).infected) {
                gc.setColor(Color.RED);
            } else {
                gc.setColor(Color.GREEN);
            }
            gc.fillRect(agent.getXc(),agent.getYc(),10,10);
        }


    }
}
