package com.example.timespenttracker.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.timespenttracker.R;
import com.example.timespenttracker.fragment.mostUsed.MostUsedAppsFragment;
import com.example.timespenttracker.fragment.mostVisited.MostVisitedAppsFragment;

public class AppRankingTabAdapter extends FragmentStatePagerAdapter {
    private static final int MOSTUSED = 0;
    private static final int MOSTVISITED = 1;

    private static final int[] TABS = new int[]{MOSTUSED, MOSTVISITED};

    private final Context context;

    public AppRankingTabAdapter(final Context context, final FragmentManager fm) {
        super(fm);
        this.context = context.getApplicationContext();
    }

    @Override
    public Fragment getItem(int position) {
        switch (TABS[position]) {
            case MOSTUSED:
                return new MostUsedAppsFragment();
            case MOSTVISITED:
                return new MostVisitedAppsFragment();
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
            case MOSTUSED:
                return context.getResources().getString(R.string.most_used);
            case MOSTVISITED:
                return context.getResources().getString(R.string.most_launched);
        }
        return null;
    }
}

