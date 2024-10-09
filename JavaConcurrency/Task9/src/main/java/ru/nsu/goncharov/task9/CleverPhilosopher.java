package ru.nsu.goncharov.task9;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class CleverPhilosopher extends Thread implements Philosopher {

    CleverPhilosopher(int philosopherId, ReentrantLock leftFork, ReentrantLock rightFork, Object waiter) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        id = philosopherId;
        this.waiter = waiter;
    }

    public void run() {
        while(!isInterrupted())  {
            // wait
            long milliseconds = ThreadLocalRandom.current().nextInt(20) + 1;
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // take forks
            System.out.println("Philosopher " + id + " tries to take forks.");
            synchronized (waiter) {
                leftFork.lock();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                rightFork.lock();
            }

            // eat
            System.out.println("Philosopher " + id + " started eating.");
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Philosopher " + id + " finished eating.");
            // leave forks
            leftFork.unlock();
            rightFork.unlock();
        }
    }

    private int id;
    private ReentrantLock leftFork;
    private ReentrantLock rightFork;
    private Object waiter;
}
