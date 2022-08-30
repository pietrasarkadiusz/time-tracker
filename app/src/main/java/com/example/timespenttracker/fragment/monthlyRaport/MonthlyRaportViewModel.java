package com.example.timespenttracker.fragment.monthlyRaport;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.timespenttracker.database.repository.AppUsageRepository;
import com.example.timespenttracker.model.MonthStats;

import java.util.List;

public class MonthlyRaportViewModel extends AndroidViewModel {
    private final LiveData<List<MonthStats>> monthlyStatsList;

    public MonthlyRaportViewModel(@NonNull Application application) {
        super(application);
        AppUsageRepository appUsageRepository = new AppUsageRepository(application);
        monthlyStatsList = appUsageRepository.getAllMonthlyStats();
    }

    public LiveData<List<MonthStats>> getMonthlyStatsList() {
        return monthlyStatsList;
    }
}
