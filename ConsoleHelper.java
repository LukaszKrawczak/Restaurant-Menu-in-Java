package com.codegym.task.task27.task2712;

import com.codegym.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() {
        String line = "";
        try {
            line = reader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public static List<Dish> getAllDishesForOrder() {
        List<Dish> orderedDishes = new ArrayList<>();

        writeMessage(Dish.allDishesToString());
        writeMessage("Please enter the name of the dish: ");
        String dishName;

        while (true) {
            try {
                dishName = readString();
                if (dishName.equals("exit"))
                    break;
                orderedDishes.add(Dish.valueOf(dishName));

            } catch (Exception e) {
                writeMessage(e.getMessage());
            }
        }
        return orderedDishes;
    }
}
