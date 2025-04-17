package Prisoner_Dilemma;

public class Tit4Tat implements Strategy{
    public boolean cooperate(Prisoner me) {
        return !me.getPartnerCheated(); // cooperate if last partner cooperated
    }

    public String getName() {
        return "Tit4Tat";
    }
}