package PrisonerDilemma;
import mvc.*;
import simstation.*;

public class Main {
    public static void main(String[] args) {
        SimStationFactory factory = new PrisonerDilemmaFactory();
        AppPanel panel = new PrisonersDilemmaPanel(factory);
        panel.display();
    }
}
