package ru.nsu.goncharov.task14;

import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Semaphore semA = new Semaphore(0);
        Semaphore semB = new Semaphore(0);
        Semaphore semC = new Semaphore(0);

        new Producer(1000, semA, "A").start();
        new Producer(2000, semB, "B").start();
        new Producer(3000, semC, "C").start();

        while(true) {
            semA.acquire();
            semB.acquire();
            semC.acquire();

            System.out.println("Widget was produced");
        }
    }
}