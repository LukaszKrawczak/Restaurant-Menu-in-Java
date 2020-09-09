package com.codegym.task.task27.task2712.kitchen;

import com.codegym.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.ArrayList;

public class TestOrder extends Order {

    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    @Override
    protected void initDishes() {
        dishes = new ArrayList<>();
        int randomSetOfDishes = getRandom(1, 6);

        for (int i = 0; i < randomSetOfDishes; i++) {
            int random = getRandom(0, 4);
            dishes.add(Dish.values()[random]);
        }
    }

    private int getRandom(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
}
