package ru.nsu.goncharov.task13;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Philosopher extends Thread {

    Philosopher(int philosopherId, ReentrantLock leftFork, ReentrantLock rightFork, ReentrantLock waiter, Condition waiterCondition) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.id = philosopherId;
        this.waiter = waiter;
        this.waiterCondition = waiterCondition;
    }

    public void run() {
        while(!isInterrupted())  {
            // wait
            long milliseconds = ThreadLocalRandom.current().nextInt(10, 20);
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            waiter.lock();
            if (leftFork.isLocked() || rightFork.isLocked()) {
                try {
                    waiterCondition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            leftFork.lock();
            rightFork.lock();
            waiter.unlock();

            System.out.println("Philosopher " + id + " started eating.");
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            leftFork.unlock();
            rightFork.unlock();
            waiter.lock();
            waiterCondition.signal();
            waiter.unlock();
            System.out.println("Philosopher " + id + " finished eating.");
        }
    }

    private int id;
    private ReentrantLock leftFork;
    private ReentrantLock rightFork;
    private ReentrantLock waiter;
    private Condition waiterCondition;
}