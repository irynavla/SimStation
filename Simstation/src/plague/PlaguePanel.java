package plague;

import simstation.*;
import mvc.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlaguePanel extends SimulationPanel {

    private JSlider infectedSlider, virulenceSlider, populationSlider, recoverySlider;
    private JButton fatalityButton;
    private boolean isFatal = true;

    public PlaguePanel(SimStationFactory factory) {
        super(factory);

        // Ensure layout is BorderLayout
        setLayout(new BorderLayout());

        // === Controls Panel (Left Side) ===
        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
        controls.setBackground(Color.PINK);
        controls.setPreferredSize(new Dimension(250, 600));

        // === Simulation Control Buttons (GridLayout) ===
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5));  // 1 row, 5 columns

        // Create Start Button
        JButton startBtn = new JButton("Start");
        startBtn.addActionListener(ae -> {
            try {
                System.out.println("Start button pressed");

                new StartCommand(model).execute();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        buttonPanel.add(startBtn);

        // Create Pause Button
        JButton pauseBtn = new JButton("Pause");
        pauseBtn.addActionListener(ae -> {
            try {
                new SuspendCommand(model).execute();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        buttonPanel.add(pauseBtn);

        // Create Resume Button
        JButton resumeBtn = new JButton("Resume");
        resumeBtn.addActionListener(ae -> {
            try {
                new ResumeCommand(model).execute();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        buttonPanel.add(resumeBtn);

        // Create Stop Button
        JButton stopBtn = new JButton("Stop");
        stopBtn.addActionListener(ae -> {
            try {
                new StopCommand(model).execute();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        buttonPanel.add(stopBtn);

        // Create Stats Button
        JButton statsBtn = new JButton("Stats");
        statsBtn.addActionListener(ae -> {
            try {
                new StatsCommand(model).execute();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        buttonPanel.add(statsBtn);

        // Add the button panel to the controls section
        controls.add(Box.createRigidArea(new Dimension(0, 20)));
        controls.add(new JLabel("Simulation Controls:"));
        controls.add(Box.createRigidArea(new Dimension(0, 5)));
        controls.add(buttonPanel);  // Adding the button panel

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
        fatalityButton.addActionListener(ae -> {
            isFatal = !isFatal;
            fatalityButton.setText(isFatal ? "Not Fatal" : "Fatal");
        });
        controls.add(fatalityButton);
        controls.add(Box.createRigidArea(new Dimension(0, 20)));

        // Add the controls panel to the WEST side
        add(controls, BorderLayout.WEST);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        super.actionPerformed(ae);

        PlagueSimulation sim = (PlagueSimulation) model;
        sim.setInitialInfected(infectedSlider.getValue());
        sim.setInitialPopulation(populationSlider.getValue());
        sim.setFatalTime(recoverySlider.getValue());
        sim.setIsFatal(isFatal);
        PlagueSimulation.VIRULENCE = virulenceSlider.getValue();

        System.out.println("Setting values:");
        System.out.println("Infected: " + infectedSlider.getValue());
        System.out.println("Population: " + populationSlider.getValue());
        System.out.println("Fatal: " + isFatal);
        System.out.println("Virulence: " + virulenceSlider.getValue());

        sim.populate();
    }
}
