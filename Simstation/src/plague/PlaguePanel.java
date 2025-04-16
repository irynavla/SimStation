package plague;

import simstation.*;
import mvc.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class PlaguePanel extends SimulationPanel {

    private JSlider infectedSlider, virulenceSlider, populationSlider, recoverySlider;
    private JButton fatalityButton;
    private boolean isFatal = true;

    public PlaguePanel(SimStationFactory factory) {
        super(factory);

        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
        controls.setBackground(Color.PINK);
        controls.setPreferredSize(new Dimension(250, 600)); // Wider panel

        // === Initial % Infected ===
        controls.add(new JLabel("Initial % Infected:"));
        infectedSlider = new JSlider(0, 100, 10);
        infectedSlider.setMajorTickSpacing(10);
        infectedSlider.setPaintTicks(true);
        infectedSlider.setPaintLabels(true);
        controls.add(infectedSlider);
        controls.add(Box.createRigidArea(new Dimension(0, 10)));

        // === Infection Probability (Virulence) ===
        controls.add(new JLabel("Infection Probability:"));
        virulenceSlider = new JSlider(0, 100, PlagueSimulation.VIRULENCE);
        virulenceSlider.setMajorTickSpacing(10);
        virulenceSlider.setPaintTicks(true);
        virulenceSlider.setPaintLabels(true);
        controls.add(virulenceSlider);
        controls.add(Box.createRigidArea(new Dimension(0, 10)));

        // === Population Size ===
        controls.add(new JLabel("Initial Population Size:"));
        populationSlider = new JSlider(0, 200, 50);
        populationSlider.setMajorTickSpacing(20);
        populationSlider.setPaintTicks(true);
        populationSlider.setPaintLabels(true);
        controls.add(populationSlider);
        controls.add(Box.createRigidArea(new Dimension(0, 10)));

        // === Recovery/Fatal Time ===
        controls.add(new JLabel("Fatality / Recovery Time:"));
        recoverySlider = new JSlider(0, 500, 200);
        recoverySlider.setMajorTickSpacing(100);
        recoverySlider.setPaintTicks(true);
        recoverySlider.setPaintLabels(true);
        controls.add(recoverySlider);
        controls.add(Box.createRigidArea(new Dimension(0, 10)));

        // === Fatality Toggle Button ===
        fatalityButton = new JButton("Not Fatal");
        fatalityButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        fatalityButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                isFatal = !isFatal;
                fatalityButton.setText(isFatal ? "Not Fatal" : "Fatal");
            }
        });
        controls.add(fatalityButton);

        this.add(controls, BorderLayout.WEST);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        super.actionPerformed(ae);  // Keep default button behavior

        // Fetch and update simulation parameters
        PlagueSimulation sim = (PlagueSimulation) model;
        sim.setInitialInfected(infectedSlider.getValue());
        sim.setInitialPopulation(populationSlider.getValue());
        sim.setFatalTime(recoverySlider.getValue());
        sim.setIsFatal(isFatal);
        PlagueSimulation.VIRULENCE = virulenceSlider.getValue();

        // Debug logging
        System.out.println("Settings updated:");
        System.out.println("Initial Infected: " + infectedSlider.getValue());
        System.out.println("Population: " + populationSlider.getValue());
        System.out.println("Fatal Time: " + recoverySlider.getValue());
        System.out.println("Fatal?: " + isFatal);
        System.out.println("Virulence: " + PlagueSimulation.VIRULENCE);
    }
}
