package com.example.timespenttracker.activity.raport;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.timespenttracker.adapter.RaportTabAdapter;
import com.example.timespenttracker.databinding.ActivityRaportsBinding;
import com.google.android.material.tabs.TabLayout;

public class RaportActivity extends AppCompatActivity {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    private final String TAG = "AppRankingActivity";
    private ActivityRaportsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRaportsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.setLifecycleOwner(this);
        setSupportActionBar(binding.toolbarRaports);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = binding.viewPagerRaports;
        tabLayout = binding.tabLayoutRaports;
        RaportTabAdapter adapter = new RaportTabAdapter(this, getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}