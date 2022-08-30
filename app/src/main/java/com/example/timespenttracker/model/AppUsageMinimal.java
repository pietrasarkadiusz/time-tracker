package com.example.timespenttracker.model;

import com.example.timespenttracker.util.DateTransUtil;

public final class AppUsageMinimal {
    private long usage;
    private int launches;

    public AppUsageMinimal(long usage, int launches) {
        this.usage = usage;
        this.launches = launches;
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

    public void divideUsageAndLaunches(int divider) {
        if (usage != 0) usage /= divider;
        if (launches != 0) launches /= divider;
    }

}


