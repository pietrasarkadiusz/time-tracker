package com.example.timespenttracker.model;

import android.graphics.drawable.Drawable;

import androidx.room.Ignore;

import com.example.timespenttracker.util.DateTransUtil;

import org.jetbrains.annotations.NotNull;

public final class AppUsageOnlyUsage {
    private final long usage;
    @NotNull
    private String packageName;
    @Ignore
    private Drawable appIcon;
    @Ignore
    private String appName;

    public AppUsageOnlyUsage(@NotNull String packageName, long usage) {
        this.packageName = packageName;
        this.usage = usage;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getUsageInHMSFormat() {
        return (" " + DateTransUtil.millisToHMSFormat(this.usage));
    }

    public long getUsage() {
        return usage;
    }

    @NotNull
    public String getAppName() {
        return appName != null ? appName : packageName;
    }

    public void setAppName(@NotNull String appName) {
        this.appName = appName;
    }

    @NotNull
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(@NotNull String packageName) {
        this.packageName = packageName;
    }
}