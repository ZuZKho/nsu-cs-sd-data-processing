package ru.nsu.goncharov.task8;

public class AtomicDouble {

    public synchronized void add(double cur) {
        result += cur;
    }

    public synchronized double get() {
        return result;
    }

    private double result = 0;
}
