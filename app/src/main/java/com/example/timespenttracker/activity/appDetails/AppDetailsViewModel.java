package com.example.timespenttracker.activity.appDetails;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.timespenttracker.database.repository.AppUsageRepository;
import com.example.timespenttracker.model.AppUsageAndLaunches;
import com.example.timespenttracker.model.AppUsageMinimal;
import com.example.timespenttracker.util.AppUsageUtil;
import com.example.timespenttracker.util.Constant;
import com.example.timespenttracker.util.DateTransUtil;

import java.util.Calendar;
import java.util.List;

public class AppDetailsViewModel extends AndroidViewModel {
    private final LiveData<AppUsageMinimal> usageThisDay;
    private final LiveData<AppUsageMinimal> usageThisWeek;
    private final LiveData<AppUsageMinimal> usageThisMonth;
    private final LiveData<AppUsageMinimal> avgUsageDay;
    private final LiveData<AppUsageMinimal> avgUsageWeek;
    private final LiveData<AppUsageMinimal> avgUsageMonth;
    private final LiveData<long[]> appUsageTodayList;
    private final LiveData<List<AppUsageAndLaunches>> appUsage7DaysList;
    private String appName;
    private Drawable appIcon;

    public AppDetailsViewModel(@NonNull Application application, String packageName) {
        super(application);
        AppUsageRepository appUsageRepository = new AppUsageRepository(application);
        Calendar calendar = Calendar.getInstance();
        long dayStart = DateTransUtil.getDayStart(calendar);
        long weekStart = DateTransUtil.getWeekStart(calendar);
        long dayEnd = dayStart + Constant.MILLISECONDS_IN_DAY;
        long weekEnd = weekStart + Constant.MILLISECONDS_IN_WEEK;
        calendar = Calendar.getInstance();
        long monthStart = DateTransUtil.getMonthStart(calendar);
        long monthEnd = DateTransUtil.getMonthEnd(calendar);
        usageThisDay = appUsageRepository.getAppUsageMinimalOnePackage(dayStart, dayEnd, packageName);
        usageThisWeek = appUsageRepository.getAppUsageMinimalOnePackage(weekStart, weekEnd, packageName);
        usageThisMonth = appUsageRepository.getAppUsageMinimalOnePackage(monthStart, monthEnd, packageName);
        calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);

        avgUsageDay = Transformations.map(appUsageRepository.getAppUsageMinimalOnePackage(dayStart - (6 * Constant.MILLISECONDS_IN_DAY), dayEnd, packageName),
                v -> {
                    v.divideUsageAndLaunches(7);
                    return v;
                });
        avgUsageWeek = Transformations.map(appUsageRepository.getAppUsageMinimalOnePackage(weekStart - (3 * Constant.MILLISECONDS_IN_WEEK), weekEnd, packageName),
                v -> {
                    v.divideUsageAndLaunches(4);
                    return v;
                });
        avgUsageMonth = Transformations.map(appUsageRepository.getAppUsageMinimalOnePackage(DateTransUtil.getMonthStart(calendar), monthEnd, packageName),
                v -> {
                    v.divideUsageAndLaunches(3);
                    return v;
                });

        appUsageTodayList = Transformations.map(appUsageRepository.getUsedAppOnePackage(dayStart, dayEnd, packageName),
                v -> AppUsageUtil.getUsageInHourlyIntervals(dayStart, v));

        appUsage7DaysList = appUsageRepository.getDailyStatsOnePackage(dayStart - (6 * Constant.MILLISECONDS_IN_DAY), dayEnd, packageName);

        PackageManager packageManager = getApplication().getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            appName = String.valueOf(applicationInfo.loadLabel(packageManager));
            appIcon = packageManager.getApplicationIcon(packageName);
        } catch (Exception exception) {
            appName = packageName;
            exception.printStackTrace();
        }

    }

    public LiveData<AppUsageMinimal> getUsageThisDay() {
        return usageThisDay;
    }

    public LiveData<AppUsageMinimal> getUsageThisWeek() {
        return usageThisWeek;
    }

    public LiveData<AppUsageMinimal> getUsageThisMonth() {
        return usageThisMonth;
    }

    public LiveData<AppUsageMinimal> getAvgUsageDay() {
        return avgUsageDay;
    }

    public LiveData<AppUsageMinimal> getAvgUsageWeek() {
        return avgUsageWeek;
    }

    public LiveData<AppUsageMinimal> getAvgUsageMonth() {
        return avgUsageMonth;
    }

    public LiveData<long[]> getAppUsageToday() {
        return appUsageTodayList;
    }

    public LiveData<List<AppUsageAndLaunches>> getAppUsage7Days() {
        return appUsage7DaysList;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public String getAppName() {
        return appName;
    }

}
