package Prisoner_Dilemma;


public class Cooperate implements Strategy{
    public boolean cooperate(Prisoner me) {
        return true;
    }

    public String getName() {
        return "Cooperate";
    }
}
