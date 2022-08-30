package com.example.timespenttracker.activity.appDetails;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AppDetailsViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final String param;

    public AppDetailsViewModelFactory(Application application, String param) {
        this.application = application;
        this.param = param;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new AppDetailsViewModel(application, param);
    }
}