package com.example.timespenttracker.fragment.dailyRaport;

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

import com.example.timespenttracker.adapter.DailyRaportViewAdapter;
import com.example.timespenttracker.databinding.FragmentDailyRaportBinding;
import com.example.timespenttracker.model.DayStats;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DailyRaport extends Fragment {
    private FragmentDailyRaportBinding binding;
    private DailyRaportViewAdapter adapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DailyRaportViewModel dailyRaportViewModel = new ViewModelProvider(this).get(DailyRaportViewModel.class);
        binding = FragmentDailyRaportBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        setupRecyclerView();
        dailyRaportViewModel.getDailyStatsList().observe(getViewLifecycleOwner(), new Observer<List<DayStats>>() {
            @Override
            public void onChanged(List<DayStats> list) {
                adapter.setData(list);
                Toast.makeText(getActivity(), "daily raport", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }

    private void setupRecyclerView() {
        adapter = new DailyRaportViewAdapter();
        RecyclerView recyclerView = binding.recyclerViewDailyRaport;
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
