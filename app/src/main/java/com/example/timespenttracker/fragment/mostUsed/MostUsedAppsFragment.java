package com.example.timespenttracker.fragment.mostUsed;

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

import com.example.timespenttracker.adapter.MostUsedAppsViewAdapter;
import com.example.timespenttracker.databinding.FragmentMostusedBinding;
import com.example.timespenttracker.model.AppUsageOnlyUsage;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MostUsedAppsFragment extends Fragment {
    private FragmentMostusedBinding binding;
    private MostUsedAppsViewAdapter adapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MostUsedAppsViewModel mostUsedAppsViewModel = new ViewModelProvider(this).get(MostUsedAppsViewModel.class);
        binding = FragmentMostusedBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        setupRecyclerView();
        mostUsedAppsViewModel.getMostUsedApps().observe(getViewLifecycleOwner(), new Observer<List<AppUsageOnlyUsage>>() {
            @Override
            public void onChanged(List<AppUsageOnlyUsage> list) {
                adapter.setData(list);
                Toast.makeText(getActivity(), "most used", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }

    private void setupRecyclerView() {
        adapter = new MostUsedAppsViewAdapter();
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
