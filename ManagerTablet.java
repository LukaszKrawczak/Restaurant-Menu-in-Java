package com.codegym.task.task27.task2712;

import com.codegym.task.task27.task2712.ad.Advertisement;
import com.codegym.task.task27.task2712.ad.StatisticsAdvertisementManager;
import com.codegym.task.task27.task2712.statistics.StatisticsManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ManagerTablet {
    private final DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

    public void printAdRevenue() {
        StatisticsManager statisticsManager = StatisticsManager.getInstance();
        Double total = (double) 0;

        for (Map.Entry<Date, Double> revenuePerDay : statisticsManager.getRevenuePerDays().descendingMap().entrySet()) {
            ConsoleHelper.writeMessage(dateFormat.format(revenuePerDay.getKey()) + " - " + String.format("%.2f", revenuePerDay.getValue() / 100));
            total += revenuePerDay.getValue();
        }
        ConsoleHelper.writeMessage("Total - " + String.format("%.2f", total / 100));
    }

    public void printCookUtilization() {
        StatisticsManager statisticsManager = StatisticsManager.getInstance();
        for (Map.Entry<Date, TreeMap<String, Long>> cookingPerDay : statisticsManager.getCookUtilization().entrySet()) {
            ConsoleHelper.writeMessage(dateFormat.format(cookingPerDay.getKey()));
            for (Map.Entry<String, Long> timePerCook : cookingPerDay.getValue().entrySet()) {
                ConsoleHelper.writeMessage(timePerCook.getKey() + " - " + timePerCook.getValue() + " min");
            }
            ConsoleHelper.writeMessage("");
        }
    }

    public void printActiveVideoSet() {
        StatisticsAdvertisementManager.getInstance().printCommercials(true);
    }

    public void printArchivedVideoSet() {
        StatisticsAdvertisementManager.getInstance().printCommercials(false);
    }
}
