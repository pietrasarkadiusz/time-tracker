package com.example.timespenttracker.model;

import com.example.timespenttracker.util.DateTransUtil;

public final class AppUsageAndLaunches {
    private long date;
    private long usage;
    private int launches;

    public AppUsageAndLaunches(long date, long usage, int launches) {
        this.date = date;
        this.usage = usage;
        this.launches = launches;
    }

    public long getDate() {
        return this.date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public final int getLaunches() {
        return this.launches;
    }

    public final void setLaunches(int i) {
        this.launches = i;
    }

    public final long getUsage() {
        return this.usage;
    }

    public final void setUsage(long usage) {
        this.usage = usage;
    }

    public final String getUsageInHMSFormat() {
        return usage != 0 ? DateTransUtil.millisToHMSFormat(usage) : "0s";
    }
}