package Prisoner_Dilemma;

import mvc.*;

public interface Strategy {
    boolean cooperate(Prisoner me);
    String getName();
}
