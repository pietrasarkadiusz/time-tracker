package com.example.timespenttracker.fragment.mostVisited;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timespenttracker.adapter.MostVisitedAppsViewAdapter;
import com.example.timespenttracker.databinding.FragmentMostvisitedBinding;
import com.example.timespenttracker.model.AppUsageOnlyLaunches;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MostVisitedAppsFragment extends Fragment {
    private FragmentMostvisitedBinding binding;
    private MostVisitedAppsViewAdapter adapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MostVisitedAppsViewModel mostVisitedAppsViewModel = new ViewModelProvider(this).get(MostVisitedAppsViewModel.class);
        binding = FragmentMostvisitedBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        setupRecyclerView();
        mostVisitedAppsViewModel.getMostUsedApps().observe(getViewLifecycleOwner(), new Observer<List<AppUsageOnlyLaunches>>() {
            @Override
            public void onChanged(List<AppUsageOnlyLaunches> list) {
                adapter.setData(list);
                Toast.makeText(getActivity(), "most visited", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }

    private void setupRecyclerView() {
        adapter = new MostVisitedAppsViewAdapter();
        RecyclerView recyclerView = binding.recyclerViewAppRanking;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}