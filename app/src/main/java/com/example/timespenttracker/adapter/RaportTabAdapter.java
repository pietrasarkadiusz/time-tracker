package com.example.timespenttracker.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.timespenttracker.R;
import com.example.timespenttracker.fragment.dailyRaport.DailyRaport;
import com.example.timespenttracker.fragment.monthlyRaport.MonthlyRaport;
import com.example.timespenttracker.fragment.weeklyRaport.WeeklyRaport;

import org.jetbrains.annotations.NotNull;

public class RaportTabAdapter extends FragmentStatePagerAdapter {
    private static final int DAILY = 0;
    private static final int WEEKLY = 1;
    private static final int MONTHLY = 2;
    private static final int[] TABS = new int[]{DAILY, WEEKLY, MONTHLY};
    private final Context context;
    public RaportTabAdapter(final Context context, final FragmentManager fm) {
        super(fm);
        this.context = context.getApplicationContext();
    }
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (TABS[position]) {
            case DAILY:
                return new DailyRaport();
            case WEEKLY:
                return new WeeklyRaport();
            case MONTHLY:
                return new MonthlyRaport();
        }
        return null;
    }
    @Override
    public int getCount() {
        return TABS.length;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (TABS[position]) {
            case DAILY:
                return context.getResources().getString(R.string.daily);
            case WEEKLY:
                return context.getResources().getString(R.string.weekly);
            case MONTHLY:
                return context.getResources().getString(R.string.monthly);
        }
        return null;
    }
}
