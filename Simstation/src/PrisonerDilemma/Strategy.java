package PrisonerDilemma;
import mvc.*;

public interface Strategy {
    boolean cooperate(Prisoner me);
    String getName();
}
