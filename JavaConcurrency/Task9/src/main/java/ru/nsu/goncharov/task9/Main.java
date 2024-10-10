package ru.nsu.goncharov.task9;

import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        ReentrantLock[] forks = new ReentrantLock[placesCount];
        for(int i = 0; i < placesCount; i++) {
            forks[i] = new ReentrantLock();
        }

        Philosopher[] philosophers = getCleverPhilosophers(forks);
        for(int i = 0; i < placesCount; i++) {
            philosophers[i].start();
        }
    }

    private static Philosopher[] getStupidPhilosophers(ReentrantLock[] forks) {
        Philosopher[] philosophers = new StupidPhilosopher[placesCount];
        for(int i = 0; i < placesCount; i++) {
            philosophers[i] = new StupidPhilosopher(i, forks[i], forks[(i + 1) % placesCount]);
        }
        return philosophers;
    }

    private static Philosopher[] getCleverPhilosophers(ReentrantLock[] forks) {
        Philosopher[] philosophers = new CleverPhilosopher[placesCount];
        Object waiter = new Object();
        for(int i = 0; i < placesCount; i++) {
            philosophers[i] = new CleverPhilosopher(i, forks[i], forks[(i + 1) % placesCount], waiter);
        }
        return philosophers;
    }

    private static final int placesCount = 5;
}