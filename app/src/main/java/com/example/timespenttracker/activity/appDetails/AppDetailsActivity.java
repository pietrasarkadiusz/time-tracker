package com.example.timespenttracker.activity.appDetails;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.timespenttracker.databinding.ActivityAppDetailsBinding;

public class AppDetailsActivity extends AppCompatActivity {
    private ActivityAppDetailsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String packageName = intent.getStringExtra("PackageName");
        AppDetailsViewModel appDetailsViewModel = new ViewModelProvider(this,
                new AppDetailsViewModelFactory(this.getApplication(), packageName))
                .get(AppDetailsViewModel.class);
        binding = ActivityAppDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.setViewModel(appDetailsViewModel);
        binding.setLifecycleOwner(this);
        setSupportActionBar(binding.toolbarAppDetails);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(appDetailsViewModel.getAppName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
