package com.example.timespenttracker.model;

import com.example.timespenttracker.util.Constant;
import com.example.timespenttracker.util.DateTransUtil;

public class WeekStats {
    private long date;
    private long usage;
    private int visits;
    private long lastWeekUsage;
    private int LastWeekLaunches;

    public WeekStats(long date, long usage, int visits, long lastWeekUsage, int LastWeekLaunches) {
        this.date = date;
        this.usage = usage;
        this.visits = visits;
        this.lastWeekUsage = lastWeekUsage;
        this.LastWeekLaunches = LastWeekLaunches;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getWeekDate() {
        long startWeek = DateTransUtil.getWeekStart(date);
        return DateTransUtil.stampToDateDDMMM(startWeek) + " - " + DateTransUtil.stampToDateDDMMMYY(startWeek + Constant.MILLISECONDS_IN_WEEK - 100);
    }

    public long getUsage() {
        return usage;
    }

    public void setUsage(long usage) {
        this.usage = usage;
    }

    public String getAvgUsage() {
        return usage > 7 ? DateTransUtil.millisToHMSFormat(usage / 7) : "0s";
    }

    public String getAvgVisits() {
        return visits > 7 ? String.valueOf(visits / 7) : "0";
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public long getLastWeekUsage() {
        return lastWeekUsage;
    }

    public void setLastWeekUsage(long lastWeekUsage) {
        this.lastWeekUsage = lastWeekUsage;
    }

    public int getLastWeekLaunches() {
        return LastWeekLaunches;
    }

    public void setLastWeekLaunches(int lastWeekLaunches) {
        this.LastWeekLaunches = lastWeekLaunches;
    }


    public final int getUsagePercentChange() {
        if (lastWeekUsage > 0) {
            return Math.round(100 * ((usage - lastWeekUsage) / (float) lastWeekUsage));
        }
        return 0;
    }

    public final int getVisitPercentChange() {
        if (LastWeekLaunches > 0) {
            return Math.round(100 * ((visits - LastWeekLaunches) / (float) LastWeekLaunches));
        }
        return 0;
    }

    public final String getUsagePercentChangeString() {
        if (lastWeekUsage > 0) {
            return Math.round(100 * ((usage - lastWeekUsage) / (float) lastWeekUsage)) + "%";
        }
        return "";
    }

    public final String getVisitPercentChangeString() {
        if (LastWeekLaunches > 0) {
            return Math.round(100 * ((visits - LastWeekLaunches) / (float) LastWeekLaunches)) + "%";
        }
        return "";
    }

    public final String getUsageInHMSFormat() {
        return usage != 0 ? DateTransUtil.millisToHMSFormat(usage) : "0s";
    }
}
