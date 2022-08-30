package com.example.timespenttracker.fragment.mostVisited;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.timespenttracker.database.repository.AppUsageRepository;
import com.example.timespenttracker.model.AppUsageOnlyLaunches;
import com.example.timespenttracker.util.Constant;
import com.example.timespenttracker.util.DateTransUtil;

import java.util.Calendar;
import java.util.List;

public class MostVisitedAppsViewModel extends AndroidViewModel {
    private final LiveData<List<AppUsageOnlyLaunches>> mostVisitedApps;

    public MostVisitedAppsViewModel(@NonNull Application application) {
        super(application);
        AppUsageRepository appUsageRepository = new AppUsageRepository(application);
        long dayStart = DateTransUtil.getDayStart(Calendar.getInstance());
        mostVisitedApps = Transformations.map(appUsageRepository.getMostLaunchedAppsSummary(dayStart, dayStart + Constant.MILLISECONDS_IN_DAY), v -> {
            if (v != null) {
                PackageManager packageManager = getApplication().getPackageManager();
                for (int i = 0; i < v.size(); i++) {
                    try {
                        ApplicationInfo applicationInfo = packageManager.getApplicationInfo(v.get(i).getPackageName(), 0);
                        v.get(i).setAppIcon(packageManager.getApplicationIcon(v.get(i).getPackageName()));
                        v.get(i).setAppName(String.valueOf(applicationInfo.loadLabel(packageManager)));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
            return v;
        });
    }

    public LiveData<List<AppUsageOnlyLaunches>> getMostUsedApps() {
        return mostVisitedApps;
    }
}