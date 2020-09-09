package com.codegym.task.task27.task2712;

import com.codegym.task.task27.task2712.ad.AdvertisementManager;
import com.codegym.task.task27.task2712.ad.NoVideoAvailableException;
import com.codegym.task.task27.task2712.kitchen.Order;
import com.codegym.task.task27.task2712.kitchen.TestOrder;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet {
    private final int number;
    private static java.util.logging.Logger logger = Logger.getLogger(Tablet.class.getName());
    private LinkedBlockingQueue<Order> queue;

    public Tablet(int number) {
        this.number = number;
    }

    public Order createOrder() {
        Order order = null;
        try {
            order = new Order(this);
            if (!order.isEmpty()) {
                processOrder(order);
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "The console is unavailable.");
        } catch (NoVideoAvailableException e) {
            logger.log(Level.INFO, "No video is available for the following order: " + order);
        }
        return order;
    }

    public void createTestOrder() {
        TestOrder order;
        try {
            order = new TestOrder(this);
            processOrder(order);
        } catch (IOException e) {}
    }

    private void processOrder(Order order) {
        queue.add(order);
        int seconds = order.getTotalCookingTime() * 60;
        AdvertisementManager manager = new AdvertisementManager(seconds);
        manager.processVideos();
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    @Override
    public String toString() {
        return "Tablet{" +
                "number=" + number +
                '}';
    }
}