package com.example.timespenttracker.fragment.weeklyRaport;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.timespenttracker.database.repository.AppUsageRepository;
import com.example.timespenttracker.model.WeekStats;

import java.util.List;

public class WeeklyRaportViewModel extends AndroidViewModel {
    private final LiveData<List<WeekStats>> weeklyStatsList;

    public WeeklyRaportViewModel(@NonNull Application application) {
        super(application);
        AppUsageRepository appUsageRepository = new AppUsageRepository(application);
        weeklyStatsList = appUsageRepository.getAllWeeklyStats();
    }

    public LiveData<List<WeekStats>> getWeeklyStatsList() {
        return weeklyStatsList;
    }
}