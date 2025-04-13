package simstation;

import mvc.Publisher;
import mvc.Utilities;

import javax.swing.*;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public abstract class Agent extends Publisher implements Serializable, Runnable {
    private final String name;
    protected Heading heading;
    private int xc;
    private int yc;
    private boolean suspended = false;
    public synchronized boolean isStopped() { return stopped; }
    private boolean stopped = false;
    public synchronized boolean isSuspended() { return suspended;  }
    transient protected Thread myThread;
    private Simulation world;
    private Agent partner = null;
    transient private ExecutorService executor;

    public Agent(String name) {
        this.name = name;
        heading = Heading.random();
        myThread = null;
        xc = Utilities.rng.nextInt(500);
        yc = Utilities.rng.nextInt(500);
    }

    public synchronized void run() {
        myThread = Thread.currentThread(); // catch my thread
        checkSuspended();
        SwingWorkerSubclass sw = new SwingWorkerSubclass();
        executor.submit(sw);
        sw.execute();
        onExit();
    }

    public synchronized void start() {
        this.executor = Executors.newFixedThreadPool(world.getAgents().size());
        Thread thread = new Thread(this);
        thread.start();
    }

    public synchronized void suspend() {
        suspended = true;
    }

    public synchronized void join() {
        try {
            if (myThread != null) myThread.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public synchronized void resume() {
        notify();
    }

    public synchronized void stop() {
        stopped = true;
    }

    public abstract void update() throws Exception;

    public synchronized void move(int steps) throws InterruptedException {
        int frameSize = 500;

        for (int i = 0; i < steps; i++) {
            switch (heading) {
                case NORTH -> yc--;
                case SOUTH -> yc++;
                case EAST  -> xc++;
                case WEST  -> xc--;
            }

            // wrap-around logic
            xc = (xc + frameSize) % frameSize;
            yc = (yc + frameSize) % frameSize;

            world.changed(); // let view know something changed
            Thread.sleep(20); // animation delay
        }
    }


    public synchronized void setSimulation(Simulation simulation) {
        this.world = simulation;
    }

    // wait for notification if I'm not stopped and I am suspended
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

    public abstract void onStart();
    public abstract void onInterrupted();
    public abstract void onExit();

    public synchronized String getName() {
        String result = name;
        if (stopped) result += " (stopped)";
        else if (suspended) result += " (suspended)";
        else result += " (running)";
        return result;
    }

    public int getXc() {
        return xc;
    }
    public int getYc() {
        return yc;
    }

    /*public synchronized void updatePartner(Agent partner) {
        this.partner = partner;
    }*/

    public synchronized void setPartner(int radius) {
        this.partner = world.getNeighbor(this, radius);
    }
    public synchronized Agent getPartner() {
        return this.partner;
    }

    class SwingWorkerSubclass extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() {
            while(!stopped) {
                try {
                    onStart();
                    onInterrupted();
                    update(); // update the business logic
                    Thread.sleep(1000); // allow time for all threads to sync
                    if (SwingUtilities.isEventDispatchThread()) {
                        System.out.println("Is event dispatch thread 3");
                    }

                    synchronized (this) {
                        checkSuspended();
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        }

    }

}