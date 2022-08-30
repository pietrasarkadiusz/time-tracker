package com.example.timespenttracker.ui.week;

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
import com.example.timespenttracker.util.Constant;
import com.example.timespenttracker.util.DateTransUtil;

import java.util.Calendar;
import java.util.List;

public class WeekViewModel extends AndroidViewModel {
    private final AppUsageRepository appUsageRepository;
    private final LiveData<List<AppUsageDetails>> appUsageList;
    private final MutableLiveData<Long> date = new MutableLiveData<>();
    private final LiveData<String> dateString;

    public WeekViewModel(@NonNull Application application) {
        super(application);
        appUsageRepository = new AppUsageRepository(application);
        date.setValue(DateTransUtil.getWeekStart(Calendar.getInstance()));
        dateString = Transformations.map(date, v -> DateTransUtil.stampToDateDDMMM(v) + " - " + DateTransUtil.stampToDateDDMMM(v + Constant.MILLISECONDS_IN_WEEK - 1000));
        appUsageList = Transformations.switchMap(date,
                v -> Transformations.map(appUsageRepository.getAppsUsageSummary(v, v + Constant.MILLISECONDS_IN_WEEK), z -> {
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
                }));
    }

    public void setDate(Long date) {
        this.date.setValue(date);
    }

    public LiveData<String> getWeekDate() {
        return dateString;
    }

    public LiveData<List<AppUsageDetails>> getAppUsageList() {
        return appUsageList;
    }
}