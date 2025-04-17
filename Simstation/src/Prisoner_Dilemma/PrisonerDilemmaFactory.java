package Prisoner_Dilemma;

import simstation.*;
import mvc.*;

public class PrisonerDilemmaFactory extends SimStationFactory {


    @Override
    public Model makeModel() {
        return new PrisonersDilemmaTournament();
    }

    @Override
    public View makeView(Model model) {
        return new PrisonersDilemmaView(model);
    }

    @Override
    public String getTitle() {
        return "Prisoner's Dilemma Tournament";
    }

    @Override
    public String[] getHelp() {
        return new String[]{
                "In this simulation, prisoners with different strategies interact using the Prisoner's Dilemma game.",
                "Strategies include: Cheat, Cooperate, Tit-for-Tat, and Random."
        };
    }

    @Override
    public String about() {
        return "SimStation – Prisoner's Dilemma by Group 10, 2025";
    }

    @Override
    public Command makeEditCommand(Model model, String type) {
        return switch (type) {
            case "Start" -> new StartCommand(model);
            case "Stop" -> new StopCommand(model);
            case "Pause" -> new SuspendCommand(model);
            case "Resume" -> new ResumeCommand(model);
            case "Stats" -> new StatsCommand(model);
            default -> throw new IllegalArgumentException("Unknown command: " + type);
        };
    }

}
