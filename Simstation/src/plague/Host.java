package plague;

import mvc.Utilities;
import simstation.Agent;
import simstation.Heading;

public class Host extends Agent {
    public boolean infected;
    private int recoveryTime = 200;
    private boolean isFatal = true;
    private int infectionClock = 0;

    public void setRecoveryTime(int t) {
        recoveryTime = t;
    }

    public void setFatal(boolean f) {
        isFatal = f;
    }

    public Host(boolean infected) {
        super("");
        heading = Heading.random();
        this.infected = infected;
    }

    @Override
    public void update() throws Exception {
        heading = Heading.random();
        int steps = Utilities.rng.nextInt(10) + 1;
        move(steps);

        if (infected) {
            infectionClock++;

            if (infectionClock >= recoveryTime) {
                if (isFatal) {
                    stop(); // agent dies
                    return;
                } else {
                    infected = false; // agent recovers
                    infectionClock = 0;
                }
            }

            setPartner(25);
            Host p = (Host) getPartner();
            if (p == null || p.infected) return; // skip if no partner or already infected

            // Corrected infection logic
            float roll = Utilities.rng.nextFloat() * 100;
            if (roll < PlagueSimulation.VIRULENCE) {
                float resistanceRoll = Utilities.rng.nextFloat() * 100;
                if (resistanceRoll > PlagueSimulation.RESISTANCE) {
                    p.infected = true;
                }
            }
        }
    }


    @Override
    public void onStart() {

    }

    @Override
    public void onInterrupted() {

    }

    @Override
    public void onExit() {

    }
}