package ru.nsu.goncharov.task14;

import java.util.concurrent.Semaphore;

public class Producer extends Thread {

    public Producer(long millis, Semaphore sem, String name) {
        this.millis = millis;
        this.sem = sem;
        this.name = name;
    }

    public void run() {
        while(true) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Detail " + name + " was produced.");
            sem.release();
        }
    }

    private final long millis;
    private final Semaphore sem;
    private final String name;
}
