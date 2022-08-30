package com.example.timespenttracker.model;

import com.example.timespenttracker.util.DateTransUtil;

import org.jetbrains.annotations.NotNull;

public class MonthStats {
    private final int days;
    @NotNull
    private String date;
    private long usage;
    private int visits;
    private long lastMonthUsage;
    private int lastMonthLaunches;

    public MonthStats(@NotNull String date, long usage, int visits, long lastMonthUsage, int lastMonthLaunches, int days) {
        this.date = date;
        this.usage = usage;
        this.visits = visits;
        this.lastMonthUsage = lastMonthUsage;
        this.lastMonthLaunches = lastMonthLaunches;
        this.days = days;
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

    public long getLastMonthUsage() {
        return lastMonthUsage;
    }

    public void setLastMonthUsage(long lastMonthUsage) {
        this.lastMonthUsage = lastMonthUsage;
    }

    public int getLastMonthLaunches() {
        return lastMonthLaunches;
    }

    public void setLastMonthLaunches(int lastMonthLaunches) {
        this.lastMonthLaunches = lastMonthLaunches;
    }

    public String getAvgUsage() {
        return usage > 7 ? DateTransUtil.millisToHMSFormat(usage / days) : "0s";
    }

    public String getAvgVisits() {
        return visits > days ? String.valueOf(visits / days) : "0";
    }

    public final int getUsagePercentChange() {
        if (lastMonthUsage > 0) {
            return Math.round(100 * ((usage - lastMonthUsage) / (float) lastMonthUsage));
        }
        return 0;
    }

    public final int getVisitPercentChange() {
        if (lastMonthLaunches > 0) {
            return Math.round(100 * ((visits - lastMonthLaunches) / (float) lastMonthLaunches));
        }
        return 0;
    }

    public final String getUsagePercentChangeString() {
        if (lastMonthUsage > 0) {
            return Math.round(100 * ((usage - lastMonthUsage) / (float) lastMonthUsage)) + "%";
        }
        return "";
    }

    public final String getVisitPercentChangeString() {
        if (lastMonthLaunches > 0) {
            return Math.round(100 * ((visits - lastMonthLaunches) / (float) lastMonthLaunches)) + "%";
        }
        return "";
    }

    public final String getUsageInHMSFormat() {
        return usage != 0 ? DateTransUtil.millisToHMSFormat(usage) : "0s";
    }
}
