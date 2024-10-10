package ru.nsu.goncharov.task12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        MyLinkedList linkedList = new MyLinkedList();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    linkedList.sort();
                }
            }
        };
        new Thread(runnable).start();


        String line;
        while(true) {
            line = reader.readLine();
            if (line.isEmpty()) {
                linkedList.print();
            } else {
                linkedList.add(line);
            }
        }
    }
}