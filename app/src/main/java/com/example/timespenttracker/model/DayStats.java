package com.example.timespenttracker.model;

import com.example.timespenttracker.util.DateTransUtil;

import org.jetbrains.annotations.NotNull;

public class DayStats {
    @NotNull
    private String date;
    private long usage;
    private int visits;
    private long yesterdayUsage;
    private int yesterdayLaunches;

    public DayStats(@NotNull String date, long usage, int visits, long yesterdayUsage, int yesterdayLaunches) {
        this.date = date;
        this.usage = usage;
        this.visits = visits;
        this.yesterdayUsage = yesterdayUsage;
        this.yesterdayLaunches = yesterdayLaunches;
    }

    @NotNull
    public String getDate() {
        return date;
    }

    public void setDate(@NotNull String date) {
        this.date = date;
    }

    public long getUsage() {
        return usage;
    }

    public void setUsage(long usage) {
        this.usage = usage;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public long getYesterdayUsage() {
        return yesterdayUsage;
    }

    public void setYesterdayUsage(long yesterdayUsage) {
        this.yesterdayUsage = yesterdayUsage;
    }

    public int getYesterdayLaunches() {
        return yesterdayLaunches;
    }

    public void setYesterdayLaunches(int yesterdayLaunches) {
        this.yesterdayLaunches = yesterdayLaunches;
    }

    public final int getUsagePercentChange() {
        if (yesterdayUsage > 0) {
            return Math.round(100 * ((usage - yesterdayUsage) / (float) yesterdayUsage));
        }
        return 0;
    }

    public final int getVisitPercentChange() {
        if (yesterdayLaunches > 0) {
            return Math.round(100 * ((visits - yesterdayLaunches) / (float) yesterdayLaunches));
        }
        return 0;
    }

    public final String getUsagePercentChangeString() {
        if (yesterdayUsage > 0) {
            return Math.round(100 * ((usage - yesterdayUsage) / (float) yesterdayUsage)) + "%";
        }
        return "";
    }

    public final String getVisitPercentChangeString() {
        if (yesterdayLaunches > 0) {
            return Math.round(100 * ((visits - yesterdayLaunches) / (float) yesterdayLaunches)) + "%";
        }
        return "";
    }


    public final String getUsageInHMSFormat() {
        return usage != 0 ? DateTransUtil.millisToHMSFormat(usage) : "0s";
    }
}
