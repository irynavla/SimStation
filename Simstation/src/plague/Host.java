package plague;


import mvc.Utilities;
import simstation.Agent;
import simstation.Heading;


public class Host extends Agent {
    public boolean infected;


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
            setPartner(25);
            Host p = (Host)getPartner();
            if (p == null) return; // stop if we dont have a partner
            if (Utilities.rng.nextFloat() * 100 > PlagueSimulation.VIRULENCE) {
                // if we roll in favor of infection
                if (Utilities.rng.nextFloat() * 100 > PlagueSimulation.RESISTANCE) {
                    // if we roll in favor of defeating the resistance
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
