package org.example;

public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            throw new RuntimeException("Wrong arguments");
        }

        int nThread = Integer.valueOf(args[0]);
        System.out.println("Starting computation with " + nThread + " threads.");

        long threadIterations = iterations / nThread;
        threadIterations = (threadIterations % 2 == 0) ? threadIterations : threadIterations + 1;

        MyThread[] threads = new MyThread[nThread];
        for(int i = 0; i < nThread; i++) {
            threads[i] = new MyThread( threadIterations * i * 2 + 1, threadIterations);
            threads[i].start();
        }

        double result = 0;
        for(int i = 0; i < nThread; i++) {
            try {
                threads[i].join();
                result += threads[i].get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(result * 4);
    }

    private static final long iterations = 10000000000l;

    private static class MyThread extends Thread {

        public MyThread(long initialDenominator, long iterations) {
            this.initialDenominator = initialDenominator;
            this.iterations = iterations;
        }

        public void run() {
            for(long i = 0; i < iterations; i++) {
                double denominator = initialDenominator + 2 * i;
                this.result += (double)(i % 2 == 0 ? 1 : -1) / denominator;
            }
        }

        public double get() {
            return result;
        }

        private final long initialDenominator;
        private final long iterations;
        private double result = 0;
    }
}