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
        setLayout(new BorderLayout()); // Ensure using BorderLayout

        // Pink controls panel on the left
        JPanel controls = new JPanel();
        controls.setLayout(new BorderLayout()); // Use BorderLayout for controls panel
        controls.setBackground(Color.PINK); // Set background color
        controls.setPreferredSize(new Dimension(500, 500));

        // === Simulation Buttons ===
        JPanel buttonRow = new JPanel(new GridLayout(1, 5));
        buttonRow.setOpaque(false);
        buttonRow.add(wrapButton("Start"));
        buttonRow.add(wrapButton("Pause"));
        buttonRow.add(wrapButton("Resume"));
        buttonRow.add(wrapButton("Stop"));
        buttonRow.add(wrapButton("Stats"));

        JPanel buttonWrapper = new JPanel();
        buttonWrapper.setOpaque(false);
        buttonWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonWrapper.add(buttonRow);
        controls.add(buttonWrapper, BorderLayout.NORTH); // Place buttons at the top
        controls.add(Box.createVerticalStrut(20), BorderLayout.CENTER); // Spacer for better layout

        // === Sliders ===
        JPanel slidersPanel = new JPanel();
        slidersPanel.setLayout(new BoxLayout(slidersPanel, BoxLayout.Y_AXIS));
        slidersPanel.setOpaque(false);

        // === Initial % Infected ===
        slidersPanel.add(new JLabel("Initial % Infected:"));
        infectedSlider = new JSlider(0, 100, 10);
        infectedSlider.setMajorTickSpacing(10);
        infectedSlider.setPaintTicks(true);
        infectedSlider.setPaintLabels(true);
        slidersPanel.add(infectedSlider);
        slidersPanel.add(Box.createVerticalStrut(10));

        // === Infection Probability (Virulence) ===
        slidersPanel.add(new JLabel("Infection Probability:"));
        virulenceSlider = new JSlider(0, 100, PlagueSimulation.VIRULENCE);
        virulenceSlider.setMajorTickSpacing(10);
        virulenceSlider.setPaintTicks(true);
        virulenceSlider.setPaintLabels(true);
        slidersPanel.add(virulenceSlider);
        slidersPanel.add(Box.createVerticalStrut(10));

        // === Population Size ===
        slidersPanel.add(new JLabel("Initial Population Size:"));
        populationSlider = new JSlider(0, 200, 50);
        populationSlider.setMajorTickSpacing(20);
        populationSlider.setPaintTicks(true);
        populationSlider.setPaintLabels(true);
        slidersPanel.add(populationSlider);
        slidersPanel.add(Box.createVerticalStrut(10));

        // === Recovery/Fatal Time ===
        slidersPanel.add(new JLabel("Fatality / Recovery Time:"));
        recoverySlider = new JSlider(0, 500, 200);
        recoverySlider.setMajorTickSpacing(100);
        recoverySlider.setPaintTicks(true);
        recoverySlider.setPaintLabels(true);
        slidersPanel.add(recoverySlider);
        slidersPanel.add(Box.createVerticalStrut(10));

        // === Fatality Toggle Button ===
        fatalityButton = new JButton("Not Fatal");
        fatalityButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        fatalityButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                isFatal = !isFatal;
                fatalityButton.setText(isFatal ? "Not Fatal" : "Fatal");
            }
        });
        slidersPanel.add(fatalityButton);

        // Add slidersPanel to the center of controls panel
        controls.add(slidersPanel, BorderLayout.CENTER);

        // === View Panel ===
        view.setBackground(Color.DARK_GRAY); // Set background color for agent deployment field

        // Add controls panel to the WEST side and view panel to the CENTER
        this.add(controls, BorderLayout.WEST);
        this.add(view, BorderLayout.CENTER);
    }

    // Helper to create buttons with shared ActionListener
    private JPanel wrapButton(String label) {
        JButton button = new JButton(label);
        button.addActionListener(this);
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.add(button);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        super.actionPerformed(ae);  // Default button actions

        // Update sim parameters
        PlagueSimulation sim = (PlagueSimulation) model;
        sim.setInitialInfected(infectedSlider.getValue());
        sim.setInitialPopulation(populationSlider.getValue());
        sim.setFatalTime(recoverySlider.getValue());
        sim.setIsFatal(isFatal);
        PlagueSimulation.VIRULENCE = virulenceSlider.getValue();

        // Debug log
        System.out.println("Settings updated:");
        System.out.println("Initial Infected: " + infectedSlider.getValue());
        System.out.println("Population: " + populationSlider.getValue());
        System.out.println("Fatal Time: " + recoverySlider.getValue());
        System.out.println("Fatal: " + isFatal);
        System.out.println("Virulence: " + PlagueSimulation.VIRULENCE);
    }
}

