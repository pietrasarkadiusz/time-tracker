package com.example.timespenttracker.model;

import android.graphics.drawable.Drawable;

import androidx.room.Ignore;

import org.jetbrains.annotations.NotNull;

public final class AppUsageOnlyLaunches {
    @NotNull
    private String packageName;
    private int launches;
    @Ignore
    private Drawable appIcon;
    @Ignore
    private String appName;

    public AppUsageOnlyLaunches(@NotNull String packageName, int launches) {
        this.packageName = packageName;
        this.launches = launches;
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

    public final String getLaunchesString() {
        return (" " + launches + " razy");
    }

    @NotNull
    public final String getPackageName() {
        return packageName;
    }

    public final void setPackageName(@NotNull String packageName) {
        this.packageName = packageName;
    }

    @NotNull
    public final String getAppName() {
        return appName != null ? appName : packageName;
    }

    public final void setAppName(@NotNull String appName) {
        this.appName = appName;
    }
}

