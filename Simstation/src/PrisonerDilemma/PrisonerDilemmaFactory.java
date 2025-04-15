package PrisonerDilemma;

import simstation.*;
import mvc.*;

 public class PrisonerDilemmaFactory extends SimStationFactory {

        @Override
        public Model makeModel() {
            return new ConcretePrisonersDilemmaTournament(); // Use a concrete subclass
        }

        @Override
        public View makeView(Model m) {
            return new PrisonersDilemmaView((PrisonersDilemmaTournament) m);
        }

        @Override
        public String[] getEditCommands() {
            return new String[0]; // If you don't have any edit commands, return an empty array
        }

        @Override
        public Command makeEditCommand(Model model, String type) {
            // Return null or a default command if you don't want any specific editing functionality
            return null;
        }

        @Override
        public String getTitle() {
            return "Prisoner's Dilemma Tournament";
        }

        @Override
        public String[] getHelp() {
            return new String[] { "Simulates Prisoner's Dilemma strategies in a social network" };
        }

        @Override
        public String about() {
            return "Prisoner's Dilemma Tournament Simulation by [Your Name]";
        }

}

