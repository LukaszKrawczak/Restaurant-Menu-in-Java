package com.codegym.task.task27.task2712.ad;

import java.util.Objects;

public class Advertisement {
    private Object content;
    private String name;
    private long amountPaid;
    private int impressionsRemaining;
    private int duration;
    private long amountPerImpression;

    public Advertisement(Object content, String name, long amountPaid, int impressionsRemaining, int duration) {
        this.content = content;
        this.name = name;
        this.amountPaid = amountPaid;
        this.impressionsRemaining = impressionsRemaining;
        this.duration = duration;

        if (impressionsRemaining == 0)
            amountPerImpression = 0;
        else
            this.amountPerImpression = amountPaid / impressionsRemaining;
    }

    public void revalidate() {
        if (impressionsRemaining <= 0)
            throw new UnsupportedOperationException();

        impressionsRemaining--;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public long getAmountPerImpression() {
        return amountPerImpression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advertisement that = (Advertisement) o;
        return content.equals(that.content) &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, name);
    }

    public int getImpressionsRemaining() {
        return impressionsRemaining;
    }

    public boolean isActive(){
        if(impressionsRemaining > 0) return true;
        else return false;
    }
}
