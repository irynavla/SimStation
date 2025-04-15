package PrisonerDilemma;

public class Cheat implements Strategy{
    public boolean cooperate(Prisoner me) {
        return false;
    }

    public String getName() {
        return "Cheat";
    }
}
