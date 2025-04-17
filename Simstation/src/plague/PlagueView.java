
package plague;

import mvc.Model;
import simstation.Agent;
import simstation.SimulationView;
import java.awt.*;

public class PlagueView extends SimulationView {

    private static final int FIELD_WIDTH = 500;
    private static final int FIELD_HEIGHT = 500;

    public PlagueView(Model model) {
        super(model);
        setBackground(Color.DARK_GRAY);
    }

    @Override
    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);

        // Draw blue border for the field
        gc.setColor(Color.BLUE);
        gc.drawRect(0, 0, FIELD_WIDTH - 1, FIELD_HEIGHT - 1);

        // Draw agents inside the 500x500 area
        for (Agent agent : sim.getAgents()) {
            int x = Math.min(agent.getXc(), FIELD_WIDTH - 10); // clamp to avoid overflow
            int y = Math.min(agent.getYc(), FIELD_HEIGHT - 10);
            if (((Host) agent).infected) {
                gc.setColor(Color.RED);
            } else {
                gc.setColor(Color.GREEN);
            }
            gc.fillRect(x, y, 10, 10);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(FIELD_WIDTH, FIELD_HEIGHT);
    }
}
