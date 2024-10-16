package org.example;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Worker implements Runnable {

    public Worker(Company company, int id, CyclicBarrier cyclicBarrier) {
        this.company = company;
        this.id = id;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        company.getFreeDepartment(id).performCalculations();
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }

    private Company company;
    private int id;
    private CyclicBarrier cyclicBarrier;
}