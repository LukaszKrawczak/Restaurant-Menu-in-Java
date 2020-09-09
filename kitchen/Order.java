package com.codegym.task.task27.task2712.kitchen;

import com.codegym.task.task27.task2712.ConsoleHelper;
import com.codegym.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.List;

public class Order {
    private final Tablet tablet;
    protected List<Dish> dishes;

    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;
        initDishes();
    }

    protected void initDishes() {
        dishes = ConsoleHelper.getAllDishesForOrder();
    }

    public int getTotalCookingTime() {
        int cookingTime = 0;
        for (Dish dish : dishes)
            cookingTime += dish.getDuration();

        return cookingTime;
    }

    public boolean isEmpty() {
        return dishes.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Your order: [");
        if (dishes.isEmpty())
            return "";
        else {
            for (Dish dish : dishes)
                builder.append(dish).append(", ");

            if (builder.toString().endsWith(", "))
                builder.delete(builder.length() - 2, builder.length());
        }
        builder.append("] ");

        builder.append("from ").append(tablet).append(", ");
        builder.append("cooking time ").append(getTotalCookingTime()).append(" min");
        return builder.toString();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public Tablet getTablet() {
        return tablet;
    }
}
