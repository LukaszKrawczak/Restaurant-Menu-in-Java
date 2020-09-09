package com.codegym.task.task27.task2712.kitchen;

public enum Dish {
    Fish(25),
    Steak(30),
    Soup(15),
    Juice(5),
    Water(3);

    Dish(int duration) {
        this.duration = duration;
    }

    public static String allDishesToString() {
        StringBuilder builder = new StringBuilder();
        for (Dish dish : Dish.values()) {
            builder.append(dish).append(", ");
        }
        return builder.toString();
    }

    private int duration;

    public int getDuration() {
        return duration;
    }
}



