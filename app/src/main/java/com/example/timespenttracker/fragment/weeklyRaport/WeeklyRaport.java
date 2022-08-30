package com.example.timespenttracker.fragment.weeklyRaport;

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

import com.example.timespenttracker.adapter.WeeklyRaportViewAdapter;
import com.example.timespenttracker.databinding.FragmentWeeklyRaportBinding;
import com.example.timespenttracker.model.WeekStats;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WeeklyRaport extends Fragment {
    private FragmentWeeklyRaportBinding binding;
    private WeeklyRaportViewAdapter adapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        WeeklyRaportViewModel weeklyRaportViewModel = new ViewModelProvider(this).get(WeeklyRaportViewModel.class);
        binding = FragmentWeeklyRaportBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        setupRecyclerView();
        weeklyRaportViewModel.getWeeklyStatsList().observe(getViewLifecycleOwner(), new Observer<List<WeekStats>>() {
            @Override
            public void onChanged(List<WeekStats> list) {
                adapter.setData(list);
                Toast.makeText(getActivity(), "daily raport", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }

    private void setupRecyclerView() {
        adapter = new WeeklyRaportViewAdapter();
        RecyclerView recyclerView = binding.recyclerViewWeeklyRaport;
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