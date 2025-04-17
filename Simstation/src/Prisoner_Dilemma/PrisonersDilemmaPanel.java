package Prisoner_Dilemma;

import simstation.*;
import mvc.*;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;

public class PrisonersDilemmaPanel extends SimulationPanel {
    public PrisonersDilemmaPanel(SimStationFactory factory) {
        super(factory);
        controlPanel.setBackground(Color.PINK);
        view.setBackground(Color.GRAY);
    }
}
