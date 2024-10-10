package ru.nsu.goncharov.task13;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        ReentrantLock[] forks = new ReentrantLock[placesCount];
        for(int i = 0; i < placesCount; i++) {
            forks[i] = new ReentrantLock();
        }

        ReentrantLock waiter = new ReentrantLock();
        Condition waiterCondition = waiter.newCondition();

        Philosopher[] philosophers = new Philosopher[placesCount];
        for(int i = 0; i < placesCount; i++) {
            philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % placesCount], waiter, waiterCondition);
        }
        for(int i = 0; i < placesCount; i++) {
            philosophers[i].start();
        }
    }

    private static final int placesCount = 5;
}