package org.example;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Random rnd = new Random();
                while(true) {
                    char c = (char)('a' + rnd.nextInt(26));
                    System.out.print(c);
                    if (Thread.interrupted()) {
                        break;
                    }
                }
            }
        };

        Thread child = new Thread(runnable);
        child.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        child.interrupt();
    }
}