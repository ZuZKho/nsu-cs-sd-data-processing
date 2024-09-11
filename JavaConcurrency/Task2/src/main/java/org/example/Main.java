package org.example;

public class Main {
    public static void main(String[] args) {

        Runnable task = new Runnable() {
            public void run() {
                for(int i = 1; i <= 10; i++) {
                    System.out.println("I'm child thread number " + i);
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for(int i = 1; i <= 10; i++) {
            System.out.println("I'm parent thread number " + i);
        }
    }
}