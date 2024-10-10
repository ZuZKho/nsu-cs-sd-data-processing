package ru.nsu.goncharov.task10;

import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {
        ReentrantLock[] mutexes = new ReentrantLock[3];
        for(int i = 0; i < 3; i++) {
            mutexes[i] = new ReentrantLock();
        }
        mutexes[2].lock();

        Runnable task = new Runnable() {
            public void run() {
                int m1 = 1, m2 = 2;
                mutexes[m1].lock();
                for(int i = 1; i <= 50; i++) {
                    mutexes[m2].lock();
                    System.out.println("I'm child thread number " + i);
                    mutexes[m1].unlock();
                    m1 = (m1 + 1) % 3;
                    m2 = (m2 + 1) % 3;
                }
            }
        };

        Thread thread = new Thread(task);
        thread.start();

        // Parent thread
        int m1 = 0, m2 = 1;
        while(!mutexes[1].isLocked()) {
        }
        mutexes[0].lock();
        mutexes[2].unlock();
        for(int i = 1; i <= 50; i++) {
            mutexes[m2].lock();
            System.out.println("I'm parent thread number " + i);
            mutexes[m1].unlock();
            m1 = (m1 + 1) % 3;
            m2 = (m2 + 1) % 3;
        }
    }
}