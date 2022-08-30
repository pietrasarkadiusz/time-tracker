package com.example.timespenttracker.activity.timeline;

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

public class TimelineViewModel extends AndroidViewModel {
    private final AppUsageRepository appUsageRepository;
    private final LiveData<List<AppUsageDetails>> appUsageList;
    private final MutableLiveData<Long> date = new MutableLiveData<>();
    private final LiveData<String> dayDate;

    public TimelineViewModel(@NonNull Application application) {
        super(application);
        appUsageRepository = new AppUsageRepository(application);
        date.setValue(DateTransUtil.getDayStart(Calendar.getInstance()));
        dayDate = Transformations.map(date,
                v -> DateTransUtil.stampToDateDDLLLLYY(v));
        appUsageList = Transformations.switchMap(date,
                v -> Transformations.map(appUsageRepository.getMostUsedApps(v, v + Constant.MILLISECONDS_IN_DAY), z -> {
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

    public void setDate(long date) {
        this.date.setValue(date);
    }

    public LiveData<String> getDayDate() {
        return dayDate;
    }

    public LiveData<List<AppUsageDetails>> getAppUsageList() {
        return appUsageList;
    }

}
