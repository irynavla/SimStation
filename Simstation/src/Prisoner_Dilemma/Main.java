package Prisoner_Dilemma;

import simstation.*;
import mvc.*;

public class Main {
    public static void main(String[] args) {
        SimStationFactory factory = new PrisonerDilemmaFactory();
        AppPanel panel = new PrisonersDilemmaPanel(factory);
        panel.display();
    }
}
