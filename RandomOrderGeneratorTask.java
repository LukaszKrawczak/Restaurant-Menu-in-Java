package com.codegym.task.task27.task2712;


import java.util.List;

public class RandomOrderGeneratorTask implements Runnable {
    private int interval;
    private List<Tablet> tablets;


    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval) {
        this.tablets = tablets;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {

            try {
                int min = 0, max = 4;
                int random = (int) (Math.random() * (max - min + 1) + min);

                Tablet tablet = tablets.get(random);
                tablet.createTestOrder();
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
