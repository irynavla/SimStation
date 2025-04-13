package simstation;

import mvc.Model;
import mvc.Subscriber;
import mvc.View;

import java.awt.*;

// SimulationView is a custom view class for rendering agents in the simulation.
// It extends the general View class and implements the Subscriber interface to observe changes.
public class SimulationView extends View implements Subscriber {

    // A reference to the simulation model, cast from the generic Model.
    protected Simulation sim;

    // Constructor takes a model, sets up the view, and casts the model to Simulation.
    public SimulationView(Model model) {
        super(model);
        sim = (Simulation) model; // Store reference to the simulation
    }

    // Updates the model used by this view, and ensures it's treated as a Simulation.
    @Override
    public void setModel(Model newModel) {
        super.setModel(newModel);        // Call superclass method
        sim = (Simulation) newModel;     // Cast and store the simulation-specific model
    }

    // This method is called automatically whenever the view needs to be redrawn (e.g., window resize or update).
    @Override
    public void paintComponent(Graphics gc) {
        super.paintComponent(gc); // First paint the background and any default components

        // Draw each agent in the simulation as a small red circle (dot)
        for (Agent agent : sim.agents) {
            gc.setColor(Color.RED);                        // Set drawing color to red
            Color oldColor = gc.getColor();                 // (Backs up current color)
            gc.fillOval(agent.getXc(), agent.getYc(), 10, 10); // Draw a filled circle at the agent’s (x, y)
            gc.setColor(oldColor);                          // Restore the previous color (optional here)
        }
    }
}
