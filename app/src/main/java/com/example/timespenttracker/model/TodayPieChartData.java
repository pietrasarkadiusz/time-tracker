package com.example.timespenttracker.model;

import java.util.List;

public class TodayPieChartData {
    private final List<AppUsageOnlyUsage> usageList;
    private final long totalUsage;

    public TodayPieChartData(List<AppUsageOnlyUsage> usageList, long totalUsage) {
        this.usageList = usageList;
        this.totalUsage = totalUsage;
    }

    public List<AppUsageOnlyUsage> getUsageList() {
        return usageList;
    }

    public long getTotalUsage() {
        return totalUsage;
    }
}
