package com.codegym.task.task27.task2712.kitchen;

import com.codegym.task.task27.task2712.ConsoleHelper;
import com.codegym.task.task27.task2712.statistics.StatisticsManager;
import com.codegym.task.task27.task2712.statistics.event.OrderReadyEventDataRow;

import java.util.Observable;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class Cook extends Observable implements Runnable {
    private String name;
    private boolean busy = false;
    private LinkedBlockingQueue<Order> queue;

    public Cook(String name) {
        this.name = name;
    }

    public void startCookingOrder(Order order) {
        try {
            busy = true;
            ConsoleHelper.writeMessage("Start cooking - " + order);
            Thread.sleep(order.getTotalCookingTime() * 10);
            setChanged();
            notifyObservers(order);
            StatisticsManager.getInstance().record(new OrderReadyEventDataRow(order.getTablet().toString(), name, order.getTotalCookingTime(), order.getDishes()));
            busy = false;
        } catch (InterruptedException e) {
        }
    }

    public boolean isBusy() {
        return busy;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (!queue.isEmpty()) {
                    if (!isBusy()) {
                        Order order = queue.poll();
                        if (order != null)
                            startCookingOrder(order);
                    }
                }
                Thread.sleep(10);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

}
