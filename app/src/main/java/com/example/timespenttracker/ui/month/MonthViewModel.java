package com.example.timespenttracker.ui.month;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.timespenttracker.database.repository.AppUsageRepository;
import com.example.timespenttracker.model.AppUsageDetails;
import com.example.timespenttracker.util.DateTransUtil;

import java.util.Calendar;
import java.util.List;

public class MonthViewModel extends AndroidViewModel {
    private final AppUsageRepository appUsageRepository;
    private final LiveData<List<AppUsageDetails>> appUsageList;
    private final MutableLiveData<Calendar> date = new MutableLiveData<>();
    private final LiveData<String> dateString;

    public MonthViewModel(@NonNull Application application) {
        super(application);
        appUsageRepository = new AppUsageRepository(application);
        date.setValue(Calendar.getInstance());
        dateString = Transformations.map(date, v -> DateTransUtil.stampToDateLLLLYY(v));
        appUsageList = Transformations.switchMap(date, v -> {
            long startMonth = DateTransUtil.getMonthStart(v);
            long endMonth = DateTransUtil.getMonthEnd(v);
            return Transformations.map(appUsageRepository.getAppsUsageSummary(startMonth, endMonth), z -> {
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
                return z;
            });
        });
    }

    public void setDate(Calendar date) {
        this.date.setValue(date);
    }

    public LiveData<String> getDateString() {
        return dateString;
    }

    public LiveData<List<AppUsageDetails>> getAppUsageList() {
        return appUsageList;
    }
}