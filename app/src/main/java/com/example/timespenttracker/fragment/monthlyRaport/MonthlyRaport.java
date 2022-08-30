package com.example.timespenttracker.fragment.monthlyRaport;

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

import com.example.timespenttracker.adapter.MonthlyRaportViewAdapter;
import com.example.timespenttracker.databinding.FragmentMonthlyRaportBinding;
import com.example.timespenttracker.model.MonthStats;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MonthlyRaport extends Fragment {
    private FragmentMonthlyRaportBinding binding;
    private MonthlyRaportViewAdapter adapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MonthlyRaportViewModel viewModel = new ViewModelProvider(this).get(MonthlyRaportViewModel.class);
        binding = FragmentMonthlyRaportBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        setupRecyclerView();
        viewModel.getMonthlyStatsList().observe(getViewLifecycleOwner(), new Observer<List<MonthStats>>() {
            @Override
            public void onChanged(List<MonthStats> list) {
                adapter.setData(list);
                Toast.makeText(getActivity(), "daily raport", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }

    private void setupRecyclerView() {
        adapter = new MonthlyRaportViewAdapter();
        RecyclerView recyclerView = binding.recyclerViewMonthlyRaport;
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