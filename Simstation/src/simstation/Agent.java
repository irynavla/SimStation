package simstation;

import mvc.Publisher;
import mvc.Utilities;

import javax.swing.*;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Agent is an abstract class that represents a unit of behavior (like a "worker" or "actor")
// in the simulation. It runs as a separate thread and can be paused, resumed, and stopped.
public abstract class Agent extends Publisher implements Serializable, Runnable {

    private final String name;               // Name of the agent
    protected Heading heading;              // Direction the agent is moving
    private int xc;                          // X-coordinate
    private int yc;                          // Y-coordinate
    private boolean suspended = false;       // If true, agent is suspended
    private boolean stopped = false;         // If true, agent is stopped
    public synchronized boolean isStopped() { return stopped; }
    public synchronized boolean isSuspended() { return suspended; }

    transient protected Thread myThread;     // The thread running this agent (not serialized)
    private Simulation world;                // The simulation this agent belongs to
    private Agent partner = null;            // Optional: another agent it interacts with
    transient private ExecutorService executor; // For background task management (not serialized)

    // Constructor: sets a random heading and random position
    public Agent(String name) {
        this.name = name;
        heading = Heading.random();
        myThread = null;
        xc = Utilities.rng.nextInt(500);
        yc = Utilities.rng.nextInt(500);
    }

    // Called when thread starts
    public synchronized void run() {
        myThread = Thread.currentThread();  // Capture this agent's thread
        checkSuspended();                   // Wait if suspended
        SwingWorkerSubclass sw = new SwingWorkerSubclass(); // Background work
        executor.submit(sw);               // Submit to executor
        sw.execute();                       // Start the worker
        onExit();                           // Custom logic on exit
    }

    // Starts a new thread for this agent and assigns an executor
    public synchronized void start() {
        this.executor = Executors.newFixedThreadPool(world.getAgents().size());
        Thread thread = new Thread(this);
        thread.start();
    }

    // Suspends the agent
    public synchronized void suspend() {
        suspended = true;
    }

    // Waits for the agent's thread to finish
    public synchronized void join() {
        try {
            if (myThread != null) myThread.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    // Resumes the agent from suspension
    public synchronized void resume() {
        notify();
    }

    // Signals the agent to stop
    public synchronized void stop() {
        stopped = true;
    }

    // Abstract method: subclasses must define how this agent updates each cycle
    public abstract void update() throws Exception;

    // Moves the agent in its heading direction step-by-step with wrap-around
    public synchronized void move(int steps) throws InterruptedException {
        int frameSize = 500;

        for (int i = 0; i < steps; i++) {
            switch (heading) {
                case NORTH -> yc--;
                case SOUTH -> yc++;
                case EAST  -> xc++;
                case WEST  -> xc--;
            }

            // wrap-around (teleport from one edge to the opposite)
            xc = (xc + frameSize) % frameSize;
            yc = (yc + frameSize) % frameSize;

            world.changed(); // Notify that the view needs to repaint
            Thread.sleep(20); // Slight pause for animation effect
        }
    }

    // Links this agent to the simulation it's part of
    public synchronized void setSimulation(Simulation simulation) {
        this.world = simulation;
    }

    // If suspended, wait until notified
    private synchronized void checkSuspended() {
        try {
            while(!stopped && suspended) {
                wait();
                suspended = false;
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    // Hooks for subclasses to define custom behavior
    public abstract void onStart();        // What to do at the start of each cycle
    public abstract void onInterrupted();  // What to do if interrupted
    public abstract void onExit();         // What to do when agent finishes

    // Returns the name with status for display/debugging
    public synchronized String getName() {
        String result = name;
        if (stopped) result += " (stopped)";
        else if (suspended) result += " (suspended)";
        else result += " (running)";
        return result;
    }

    // Get agent coordinates
    public int getXc() { return xc; }
    public int getYc() { return yc; }

    // Assigns a nearby agent within a radius as this agent’s “partner”
    public synchronized void setPartner(int radius) {
        this.partner = world.getNeighbor(this, radius);
    }

    // Gets the agent’s current partner
    public synchronized Agent getPartner() {
        return this.partner;
    }

    // Background worker that repeatedly runs agent logic while not stopped
    class SwingWorkerSubclass extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() {
            while (!stopped) {
                try {
                    onStart();        // Custom logic before update
                    onInterrupted();  // Custom logic after resume/suspend
                    update();         // Core logic for the agent
                    Thread.sleep(1000); // Wait for other agents
                    if (SwingUtilities.isEventDispatchThread()) {
                        System.out.println("Is event dispatch thread 3");
                    }

                    synchronized (this) {
                        checkSuspended(); // Wait if suspended
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        }
    }
}
