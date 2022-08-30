package com.example.timespenttracker.model;

import android.graphics.drawable.Drawable;

import androidx.room.Ignore;

import com.example.timespenttracker.util.DateTransUtil;

import org.jetbrains.annotations.NotNull;

public final class AppUsageDetails {
    @NotNull
    private String packageName;
    private long date;
    @Ignore
    private String appName;
    private long usage;
    private int launches;
    @Ignore
    private Drawable appIcon;

    public AppUsageDetails(@NotNull String packageName, long usage, int launches, long date) {
        this.packageName = packageName;
        this.usage = usage;
        this.launches = launches;
        this.date = date;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public final int getLaunches() {
        return launches;
    }

    public final void setLaunches(int i) {
        this.launches = i;
    }

    public final String getUsageInHMSFormat() {
        return usage != 0 ? DateTransUtil.millisToHMSFormat(usage) : "0s";
    }

    public final String getDateInHMFormat() {
        return usage != 0 ? DateTransUtil.stampToDateHHMM(date) : "0s";
    }

    @NotNull
    public final String getAppName() {
        return appName != null ? appName : packageName;
    }

    public final void setAppName(@NotNull String appName) {
        this.appName = appName;
    }

    @NotNull
    public final String getPackageName() {
        return packageName;
    }

    public final void setPackageName(@NotNull String packageName) {
        this.packageName = packageName;
    }

    public final long getUsage() {
        return usage;
    }

    public final void setUsage(long usage) {
        this.usage = usage;
    }

    public final long getDate() {
        return date;
    }

    public final void setDate(long str) {
        this.date = str;
    }


}

