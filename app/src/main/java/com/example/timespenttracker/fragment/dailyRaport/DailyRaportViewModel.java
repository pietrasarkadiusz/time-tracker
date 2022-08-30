package com.example.timespenttracker.fragment.dailyRaport;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.timespenttracker.database.repository.AppUsageRepository;
import com.example.timespenttracker.model.DayStats;

import java.util.List;

public class DailyRaportViewModel extends AndroidViewModel {
    private final LiveData<List<DayStats>> dailyStatsList;

    public DailyRaportViewModel(@NonNull Application application) {
        super(application);
        AppUsageRepository appUsageRepository = new AppUsageRepository(application);
        dailyStatsList = appUsageRepository.getAllDailyStats();
    }

    public LiveData<List<DayStats>> getDailyStatsList() {
        return dailyStatsList;
    }
}
