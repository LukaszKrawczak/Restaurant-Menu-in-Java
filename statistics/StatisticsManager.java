package com.codegym.task.task27.task2712.statistics;

import com.codegym.task.task27.task2712.ad.AdvertisementStorage;
import com.codegym.task.task27.task2712.kitchen.Cook;
import com.codegym.task.task27.task2712.statistics.event.EventDataRow;
import com.codegym.task.task27.task2712.statistics.event.EventType;
import com.codegym.task.task27.task2712.statistics.event.OrderReadyEventDataRow;
import com.codegym.task.task27.task2712.statistics.event.VideosSelectedEventDataRow;

import java.util.*;

public class StatisticsManager {
    private static StatisticsManager instance = null;
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private StatisticsStorage statisticsStorage = new StatisticsStorage();
    private TreeMap<Date, Double> revenuePerDays = new TreeMap<Date, Double>();

    private StatisticsManager() {
    }

    public static StatisticsManager getInstance() {
        if (instance == null)
            instance = new StatisticsManager();

        return instance;
    }

    public void record(EventDataRow data) {
        statisticsStorage.put(data);
    }

    public TreeMap<Date, Double> getRevenuePerDays() {
        for (Map.Entry<EventType, List<EventDataRow>> e : statisticsStorage.getStorage().entrySet()) {
            if (e.getKey() == EventType.VIDEOS_SELECTED)
                for (EventDataRow x : e.getValue()) {
                    VideosSelectedEventDataRow videosSelectedEventDataRow = (VideosSelectedEventDataRow) x;
                    Date day = new Date(videosSelectedEventDataRow.getDate().getYear(), videosSelectedEventDataRow.getDate().getMonth(), videosSelectedEventDataRow.getDate().getDate());
                    Double result = revenuePerDays.get(day);
                    if (result == null)
                        result = (double) 0;

                    System.out.println(result);
                    revenuePerDays.put(day, result + videosSelectedEventDataRow.getAmount());

                }
        }
        return revenuePerDays;
    }

    public HashMap<Date, TreeMap<String, Long>> getCookUtilization() {
        HashMap<Date, TreeMap<String, Long>> cookHourPerDay = new HashMap<Date, TreeMap<String, Long>>();
        for (Map.Entry<EventType, List<EventDataRow>> e : statisticsStorage.getStorage().entrySet()) {
            if (e.getKey() == EventType.ORDER_READY) {
                for (EventDataRow x : e.getValue()) {

                    OrderReadyEventDataRow orderReadyEventDataRow = (OrderReadyEventDataRow) x;
                    Date day = new Date(orderReadyEventDataRow.getDate().getYear(), orderReadyEventDataRow.getDate().getMonth(), orderReadyEventDataRow.getDate().getDate());
                    TreeMap<String, Long> cookList = cookHourPerDay.get(day);
                    if (cookList == null)
                        cookList = new TreeMap<String, Long>();
                    Long cookTime = cookList.get(orderReadyEventDataRow.getCookName());
                    if (cookTime == null)
                        cookTime = 0l;
                    cookTime += (long) orderReadyEventDataRow.getCookingTimeSeconds();

                    cookList.put(orderReadyEventDataRow.getCookName(), cookTime);
                    cookHourPerDay.put(day, cookList);
                }
            }
        }
        return cookHourPerDay;
    }

    private class StatisticsStorage {
        private Map<EventType, List<EventDataRow>> storage;

        public StatisticsStorage() {
            storage = new HashMap<>();
            for (EventType type : EventType.values())
                storage.put(type, new ArrayList<EventDataRow>());
        }

        private void put(EventDataRow data) {
            storage.get(data.getType()).add(data);
        }

        public Map<EventType, List<EventDataRow>> getStorage() {
            return storage;
        }
    }

}
