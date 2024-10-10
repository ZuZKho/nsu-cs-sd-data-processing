package ru.nsu.goncharov.task8;

import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            throw new RuntimeException("Wrong arguments");
        }

        int nThread = Integer.valueOf(args[0]);
        Semaphore semaphore = new Semaphore(nThread);
        ExecutorService executorService = Executors.newFixedThreadPool(nThread);
        System.out.println("Starting computation with " + nThread + " threads.");

        AtomicDouble result = new AtomicDouble();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                executorService.shutdown();
                try {
                    executorService.awaitTermination(10, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println(result.get() * 4);
            }
        });

        long denominator = 1;
        while(!executorService.isShutdown()) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                executorService.execute(new MyRunnable(denominator, iterations, result, semaphore));
                denominator += iterations * 2;
            } catch (RejectedExecutionException e) {
                break;
            }
        }
    }

    private static long iterations = 1000L;

    private static class MyRunnable implements Runnable {

        public MyRunnable(long initialDenominator, long iterations, AtomicDouble result, Semaphore sem) {
            this.initialDenominator = initialDenominator;
            this.iterations = iterations;
            this.result = result;
            this.sem = sem;
        }

        public void run() {
            double current = 0;
            for(long i = 0; i < iterations; i++) {
                double denominator = initialDenominator + 2 * i;
                current += (double)(i % 2 == 0 ? 1 : -1) / denominator;
            }
            result.add(current);
            sem.release();
        }

        private final long initialDenominator;
        private final long iterations;
        private final AtomicDouble result;
        private final Semaphore sem;
    }
}