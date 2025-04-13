package simstation;

import java.util.*;
import java.util.Timer;

import mvc.*;

public abstract class Simulation extends Model {

    transient private Timer timer; // timers aren't serializable
    private int clock = 0;
    protected List<Agent> agents;

    public Simulation() { agents = new ArrayList<>(); }

    public void init() {
        agents.clear();
        clock = 0;
        changed();
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new ClockUpdater(), 1000, 1000);
    }

    private void stopTimer() {
        timer.cancel();
        timer.purge();
    }

    public void addAgent(Agent agent) {
        agents.add(agent);
        agent.setSimulation(this);
    }

    public void start() throws Exception {
        init();
        populate();
        startTimer();

        for (Agent agent : agents) {
            System.out.println(agent.getName());
            agent.start();
            changed();
        }

    }

    public void suspend() {
        stopTimer();
        for (Agent agent : agents) {
            agent.suspend();
            changed();
            System.out.println("agent " + agent.getName() + "suspends");
        }
    }

    public void resume() {
        startTimer();
        for (Agent agent : agents) {
            agent.resume();
            changed();
        }
    }

    public void stop() {
        for (Agent agent : agents) {
            agent.stop();
            changed();
        }

        // wait for agents to die
        for (Agent agent : agents) {
            try {
                agent.join();
            } finally {
                System.out.println(agent.getName() + " has died");
            }
        }
        System.out.println("all done");
        stopTimer();
        init();
    }

    private synchronized double distance(Agent a, Agent b) {
        return Math.sqrt((a.getXc() - b.getXc()) * (a.getXc() - b.getXc()) +
                (a.getYc() - b.getYc()) * (a.getYc() - b.getYc()));
    }

    public synchronized Agent getNeighbor(Agent a, double radius) { // get a random agent in the radius
//        int rand = Utilities.rng.nextInt(agents.size());
//        int i = 0;
//        while (i < agents.size()) {
//            Agent b = agents.get(rand);
//            if (a != b && distance(a, b) < radius) { // need to check this assumption in real time
//                return b;
//            }
//            rand++;
//            if (rand >= agents.size()) {
//                rand = 0;
//            }
//            i++;
//        }
//        return null;
        List<Agent> eligibleAgents = new ArrayList<>();
        for (Agent b : agents) {
            if (a != b && distance(a, b) < radius) {
                eligibleAgents.add(b);
            }
        }
        if (!eligibleAgents.isEmpty()) {
            int rand = Utilities.rng.nextInt(eligibleAgents.size());
            return eligibleAgents.get(rand);
        }
        return null;
    }

    public abstract void populate() throws Exception;

    private class ClockUpdater extends TimerTask {
        public void run() {
            clock++;
        }
    }

    /*public String stats() {
        return "#agents = " + agents.size() + "\n" + "clock = " + clock + "\n";
    }*/

    public abstract String[] getStats();

    public void stats() {
        Utilities.inform(getStats());
        //JOptionPane.showMessageDialog(null,
        //"#agents = " + agents.size() + "\n" + "clock = " + clock + "\n");
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public int getClock() {
        return clock;
    }
}