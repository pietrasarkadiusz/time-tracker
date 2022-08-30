package com.example.timespenttracker.ui.home;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.timespenttracker.database.repository.AppUsageRepository;
import com.example.timespenttracker.database.repository.PackageRepository;
import com.example.timespenttracker.model.AppUsageAndLaunches;
import com.example.timespenttracker.model.AppUsageMinimal;
import com.example.timespenttracker.model.AppUsageOnlyLaunches;
import com.example.timespenttracker.model.AppUsageOnlyUsage;
import com.example.timespenttracker.model.TodayPieChartData;
import com.example.timespenttracker.util.AppUsageUtil;
import com.example.timespenttracker.util.Constant;
import com.example.timespenttracker.util.DateTransUtil;

import java.util.Calendar;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final LiveData<long[]> appUsageTodayList;
    private final LiveData<List<AppUsageAndLaunches>> appUsage7DaysList;
    private final LiveData<AppUsageMinimal> usageThisDay;
    private final LiveData<List<AppUsageOnlyUsage>> appUsageForPieChart;
    private final LiveData<TodayPieChartData> todayPieChartData;
    private final PackageRepository packageRepository;
    private final LiveData<AppUsageOnlyUsage> mostUsedApp;
    private final LiveData<AppUsageOnlyLaunches> mostVisitedApp;
    private final AppUsageRepository appUsageRepository;
    private final LiveData<AppUsageMinimal> avgUsageDay;
    private final long startDay;
    private final long endDay;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        packageRepository = new PackageRepository(application);
        appUsageRepository = new AppUsageRepository(application);
        Calendar calendar = Calendar.getInstance();
        startDay = DateTransUtil.getDayStart(calendar);
        endDay = startDay + Constant.MILLISECONDS_IN_DAY;
        appUsageTodayList = Transformations.map(appUsageRepository.getUsedApps(startDay, endDay),
                v -> AppUsageUtil.getUsageInHourlyIntervals(startDay, v));
        appUsage7DaysList = appUsageRepository.getDailyStats(startDay - Constant.MILLISECONDS_IN_WEEK, endDay);
        mostUsedApp = Transformations.map(appUsageRepository.getMostUsedApp(startDay, endDay), v -> {
            if (v != null) {
                PackageManager packageManager = getApplication().getPackageManager();
                try {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(v.getPackageName(), 0);
                    v.setAppIcon(packageManager.getApplicationIcon(v.getPackageName()));
                    v.setAppName(String.valueOf(applicationInfo.loadLabel(packageManager)));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            return v;
        });
        mostVisitedApp = Transformations.map(appUsageRepository.getMostLaunchedApp(startDay, endDay), v -> {
            if (v != null) {
                PackageManager packageManager = getApplication().getPackageManager();
                try {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(v.getPackageName(), 0);
                    v.setAppIcon(packageManager.getApplicationIcon(v.getPackageName()));
                    v.setAppName(String.valueOf(applicationInfo.loadLabel(packageManager)));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            return v;
        });
        usageThisDay = appUsageRepository.getAppUsageMinimal(startDay, endDay);
        avgUsageDay = Transformations.map(appUsageRepository.getAppUsageMinimal(startDay - Constant.MILLISECONDS_IN_WEEK, endDay),
                v -> {
                    v.divideUsageAndLaunches(7);
                    return v;
                });

        todayPieChartData = Transformations.switchMap(usageThisDay,
                v -> Transformations.map(appUsageRepository.getMostUsedAppsSummaryForPieChart(startDay, endDay), z -> {
                    if (z != null) {
                        PackageManager packageManager = getApplication().getPackageManager();
                        for (int i = 0; i < z.size(); i++) {
                            try {
                                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(z.get(i).getPackageName(), 0);
                                z.get(i).setAppIcon(packageManager.getApplicationIcon(z.get(i).getPackageName()));
                                z.get(i).setAppName(String.valueOf(applicationInfo.loadLabel(packageManager)));
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }

                        }
                    }
                    return new TodayPieChartData(z, v.getUsage());
                }));

        appUsageForPieChart = Transformations.switchMap(usageThisDay,
                v -> Transformations.map(appUsageRepository.getMostUsedAppsSummaryForPieChart(startDay, endDay), z -> {
                    if (z != null) {
                        PackageManager packageManager = getApplication().getPackageManager();
                        for (int i = 0; i < z.size(); i++) {
                            try {
                                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(z.get(i).getPackageName(), 0);
                                z.get(i).setAppIcon(packageManager.getApplicationIcon(z.get(i).getPackageName()));
                                z.get(i).setAppName(String.valueOf(applicationInfo.loadLabel(packageManager)));
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }

                        }
                        z.add(new AppUsageOnlyUsage("Total", v.getUsage()));
                    }
                    return z;
                }));
    }

    public LiveData<TodayPieChartData> getTodayPieChartData() {
        return todayPieChartData;
    }

    public LiveData<List<AppUsageOnlyUsage>> getAppUsageForPieChart() {
        return appUsageForPieChart;
    }

    public LiveData<AppUsageMinimal> getUsageThisDay() {
        return usageThisDay;
    }

    public LiveData<AppUsageMinimal> getAvgUsageDay() {
        return avgUsageDay;
    }

    public void deleteAll() {
        packageRepository.deleteAll();
    }

    public LiveData<AppUsageOnlyUsage> getMostUsedApp() {
        return mostUsedApp;
    }

    public LiveData<AppUsageOnlyLaunches> getMostVisitedApp() {
        return mostVisitedApp;
    }

    public LiveData<long[]> getAppUsageToday() {
        return appUsageTodayList;
    }

    public LiveData<List<AppUsageAndLaunches>> getAppUsage7Days() {
        return appUsage7DaysList;
    }

}