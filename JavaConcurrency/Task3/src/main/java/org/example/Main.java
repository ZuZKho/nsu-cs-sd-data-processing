package org.example;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random rnd = new Random();

        for(int i = 0; i < nThreads; i++) {
            int length = rnd.nextInt(10) + 1;
            strings[i] = new String[length];
            for(int j = 0; j < length; j++) {
                strings[i][j] = "This is random number " + rnd.nextInt() + " from thread " + i + ".";
            }
        }

        for(int i = 0; i < nThreads; i++) {
            new Thread(new MyRunnable(strings[i])).start();
        }
    }

    private static final int nThreads = 4;
    private static String[][] strings = new String[nThreads][];
}