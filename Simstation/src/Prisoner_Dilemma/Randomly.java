package Prisoner_Dilemma;

import mvc.*;

public class Randomly implements Strategy {
    public boolean cooperate(Prisoner me) {
        return Utilities.rng.nextBoolean();
    }

    public String getName() {
        return "Randomly";
    }
}