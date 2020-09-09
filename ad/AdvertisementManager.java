package com.codegym.task.task27.task2712.ad;

import com.codegym.task.task27.task2712.ConsoleHelper;
import com.codegym.task.task27.task2712.statistics.StatisticsManager;
import com.codegym.task.task27.task2712.statistics.event.VideosSelectedEventDataRow;

import java.util.*;

public class AdvertisementManager {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private final List<Advertisement> availableVideos = new LinkedList<>(storage.list());
    private List<Advertisement> optimalVideos = new LinkedList<>();
    private final int timeSeconds;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() throws NoVideoAvailableException {
        if (availableVideos.isEmpty())
            throw new NoVideoAvailableException();
        getOptimalPlaylist(availableVideos, new ArrayList<>(), timeSeconds);
        if (availableVideos.isEmpty())
            throw new NoVideoAvailableException();

        Collections.sort(optimalVideos, new ImpressionsComparator());
        StatisticsManager.getInstance().record(new VideosSelectedEventDataRow(optimalVideos, getOptimalAmount(), getTotalDuration()));
        optimalVideos.forEach(v -> ConsoleHelper.writeMessage(
                String.format("Displaying %s... %s, %s", v.getName(), v.getAmountPerImpression(), (1000L * v.getAmountPerImpression() / v.getDuration())))
        );
    }

    public void getOptimalPlaylist(List<Advertisement> remainingVideos, List<Advertisement> selectedVideos, int remainingTime) {
        List<Advertisement> newRemainingVideos = new ArrayList<>(remainingVideos);
        List<Advertisement> newSelectedVideos = new ArrayList<>(selectedVideos);
        newRemainingVideos.sort((o1, o2) -> (int) (o2.getAmountPerImpression() - o1.getAmountPerImpression()));
        if (newRemainingVideos.isEmpty()) {
            if (optimalVideos.isEmpty())
                optimalVideos = newSelectedVideos;
            else {
                long playListAmount = 0;
                int playListTotalTime = 0;
                for (Advertisement ad : optimalVideos) {
                    playListAmount += ad.getAmountPerImpression();
                    playListTotalTime += ad.getDuration();
                }
                long newSelectedVideosAmount = 0;
                int newSelectedVideosTotalTime = 0;
                for (Advertisement ad : newSelectedVideos) {
                    newSelectedVideosAmount += ad.getAmountPerImpression();
                    newSelectedVideosTotalTime += ad.getDuration();
                }
                if (playListAmount == newSelectedVideosAmount && playListTotalTime == newSelectedVideosTotalTime) {
                    if (optimalVideos.size() > newSelectedVideos.size())
                        optimalVideos = newSelectedVideos;
                } else if (playListAmount == newSelectedVideosAmount) {
                    if (playListTotalTime < newSelectedVideosTotalTime)
                        optimalVideos = newSelectedVideos;
                }
            }
        } else {
            Advertisement video = newRemainingVideos.remove(0);
            if (video.getDuration() <= remainingTime && video.getImpressionsRemaining() > 0) {
                newSelectedVideos.add(video);
                video.revalidate();
                getOptimalPlaylist(newRemainingVideos, newSelectedVideos, remainingTime - video.getDuration());
            } else {
                getOptimalPlaylist(newRemainingVideos, newSelectedVideos, remainingTime);
            }
        }
    }

    private long getOptimalAmount() {
        long amount = 0;
        for (Advertisement ad : optimalVideos)
            amount += ad.getAmountPerImpression();

        return amount;
    }

    private int getTotalDuration() {
        int totalDuration = 0;
        for (Advertisement ad : optimalVideos)
            totalDuration += ad.getDuration();

        return totalDuration;
    }

    private class ImpressionsComparator implements Comparator<Advertisement> {
        @Override
        public int compare(Advertisement o1, Advertisement o2) {
            if (o2.getAmountPerImpression() > o1.getAmountPerImpression())
                return 1;
            else if (o2.getAmountPerImpression() == o1.getAmountPerImpression()){
                long amPerImpression1 = o1.getAmountPerImpression() / o1.getDuration();
                long amPerImpression2 = o2.getAmountPerImpression() / o2.getDuration();
                return (int) (amPerImpression1 - amPerImpression2);
            }
            return -1;
        }
    }


}
