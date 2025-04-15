package simstation;

import java.util.*;
import java.util.Timer;

import mvc.*;

// Base class for simulations using the SimStation framework
public abstract class Simulation extends Model {

    // Timer for updating the simulation clock; marked transient because it's not serializable
    transient private Timer timer;

    // Simulation clock (increments every second)
    private int clock = 0;

    // List of all agents participating in the simulation
    protected List<Agent> agents;

    // Constructor: initializes the list of agents
    public Simulation() {
        agents = new ArrayList<>();
    }

    // Resets the simulation state (clears agents and resets clock)
    public void init() {
        agents.clear();
        clock = 0;
        changed(); // Notifies observers that the model has changed
    }

    // Starts the simulation clock using Java's Timer
    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new ClockUpdater(), 1000, 1000); // tick every 1 sec
    }

    // Stops and cleans up the timer
    private void stopTimer() {
        timer.cancel();
        timer.purge();
    }

    // Adds an agent to the simulation and links it back to the simulation
    public void addAgent(Agent agent) {
        agents.add(agent);
        agent.setSimulation(this);
    }

    // Starts the simulation: initializes, populates agents, starts timer, and launches agents
    public void start() throws Exception {
        init();        // reset everything
        populate();    // add custom agents (defined in subclass)
        startTimer();  // start ticking clock

        for (Agent agent : agents) {
            System.out.println(agent.getName());
            agent.start(); // starts agent in a new thread
            changed();     // notify observers
        }
    }

    // Suspends the simulation: pauses timer and suspends each agent
    public void suspend() {
        stopTimer();
        for (Agent agent : agents) {
            agent.suspend();
            changed();
            System.out.println("agent " + agent.getName() + " suspends");
        }
    }

    // Resumes the simulation after suspension
    public void resume() {
        startTimer();
        for (Agent agent : agents) {
            agent.resume();
            changed();
        }
    }

    // Stops the simulation: tells each agent to stop, waits for them to finish
    public void stop() {
        for (Agent agent : agents) {
            agent.stop();
            changed();
        }

        // Wait for all agents to terminate (join blocks until thread ends)
        for (Agent agent : agents) {
            try {
                agent.join();
            } finally {
                System.out.println(agent.getName() + " has died");
            }
        }

        System.out.println("all done");
        stopTimer();
        init(); // reset state
    }

    // Computes distance between two agents
    private synchronized double distance(Agent a, Agent b) {
        return Math.sqrt((a.getXc() - b.getXc()) * (a.getXc() - b.getXc()) +
                (a.getYc() - b.getYc()) * (a.getYc() - b.getYc()));
    }

    // Returns a random agent within a given radius of the specified agent `a`
    public synchronized Agent getNeighbor(Agent a, double radius) {
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

    // Abstract method: subclasses must define how agents are created and added
    public abstract void populate() throws Exception;

    // Inner class to update the clock every second
    private class ClockUpdater extends TimerTask {
        public void run() {
            clock++; // tick the clock
        }
    }

    // Abstract method: subclasses must define what stats to display
    public abstract String[] getStats();

    // Displays simulation stats in a dialog box (calls getStats())
    public void stats() {
        Utilities.inform(getStats());
    }

    // Accessor for the list of agents
    public List<Agent> getAgents() {
        return agents;
    }
    // Returns the number of agents that are currently running
    public int getRunningAgents() {
        int count = 0;
        for (Agent agent : agents) {
            if (agent.isRunning()) {
                count++;
            }
        }
        return count;
    }


    // Accessor for the simulation clock
    public int getClock() {
        return clock;
    }
}
