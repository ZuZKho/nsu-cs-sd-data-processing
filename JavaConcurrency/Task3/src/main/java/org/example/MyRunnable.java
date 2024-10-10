package org.example;

public class MyRunnable implements Runnable{

    public MyRunnable(String[] strings) {
        this.strings = strings;
    }

    public void run() {
        print(strings);
    }

    private static void print(String[] strings) {
        for(String str : strings) {
            System.out.println(str);
        }
    }

    private String[] strings;
}
`