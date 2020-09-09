package com.codegym.task.task27.task2712.ad;

import com.codegym.task.task27.task2712.ConsoleHelper;

import java.util.*;

public class StatisticsAdvertisementManager {
    private static StatisticsAdvertisementManager instance = null;
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();

    private StatisticsAdvertisementManager() {
    }

    public static StatisticsAdvertisementManager getInstance() {
        if (instance == null)
            instance = new StatisticsAdvertisementManager();

        return instance;
    }

    public List<Advertisement> getListOfActiveCommercials() {
        List<Advertisement> adHelperList = new ArrayList<>();
        if (!storage.list().isEmpty()) {
            for (Advertisement ad : storage.list()) {
                if (ad.getImpressionsRemaining() > 0) {
                    adHelperList.add(ad);
                }
            }
        }
        return adHelperList;
    }

    public List<Advertisement> getListOfInactiveCommercials() {
        List<Advertisement> adHelperList = new ArrayList<>();

        if (!storage.list().isEmpty()) {
            for (Advertisement ad : storage.list()) {
                if (ad.getImpressionsRemaining() <= 50 )
                    adHelperList.add(ad);
            }
        }

        Collections.sort(adHelperList, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                String one = o1.getName(), two = o2.getName();
                return one.compareToIgnoreCase(two);
            }
        });
        return adHelperList;
    }

    public void printCommercials(boolean isActive) {
        List<Advertisement> temp = new ArrayList<>(storage.list());
        Iterator<Advertisement> x = temp.iterator();
        while (x.hasNext()) {
            if (isActive) {
                if (!x.next().isActive()) x.remove();
            } else {
                if (x.next().isActive()) x.remove();
            }
        }

        String template = isActive ? "%s - %d" : "%s";
        if (!temp.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            Collections.sort(temp, new Comparator<Advertisement>() {
                @Override
                public int compare(Advertisement o1, Advertisement o2) {
                    String one = o1.getName(), two = o2.getName();

                    return one.compareToIgnoreCase(two);
                }
            });
            Advertisement x1 = temp.get(0);

            sb.append(String.format(template, x1.getName(), x1.getImpressionsRemaining()));
            for (int i = 1; i < temp.size(); i++) {
                x1 = temp.get(i);
                sb.append(String.format("\n" + template, x1.getName(), x1.getImpressionsRemaining()));
            }
            ConsoleHelper.writeMessage(sb.toString());
        }else ConsoleHelper.writeMessage("");

    }



}
