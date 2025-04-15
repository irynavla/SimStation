package PrisonerDilemma;

import simstation.*;
import mvc.*;

import java.awt.*;

public class PrisonersDilemmaView extends SimulationView {
    public PrisonersDilemmaView(Model model) {
        super(model);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Simulation sim = (Simulation) model;
        for (Agent a : sim.getAgents()) {
            if (a instanceof Prisoner p) {
                g.setColor(p.getColor());
                g.fillOval(p.getXc(), p.getYc(), 5, 5);
            }
        }

        drawLegend(g);
    }

    private void drawLegend(Graphics g) {
        int x = 10, y = 10;
        int size = 10;
        int spacing = 20;

        g.setColor(Color.BLACK);
        g.drawString("Legend:", x, y);

        g.setColor(Color.RED);
        g.fillRect(x, y + spacing, size, size);
        g.setColor(Color.BLACK);
        g.drawString("Cheat", x + 15, y + spacing + 10);

        g.setColor(Color.GREEN);
        g.fillRect(x, y + 2 * spacing, size, size);
        g.drawString("Cooperate", x + 15, y + 2 * spacing + 10);

        g.setColor(Color.BLUE);
        g.fillRect(x, y + 3 * spacing, size, size);
        g.drawString("Tit-for-Tat", x + 15, y + 3 * spacing + 10);

        g.setColor(Color.MAGENTA);
        g.fillRect(x, y + 4 * spacing, size, size);
        g.drawString("Random", x + 15, y + 4 * spacing + 10);
    }
}
