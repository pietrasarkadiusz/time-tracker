package com.example.timespenttracker.ui.month;

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

import com.example.timespenttracker.adapter.AppUsageAdapter;
import com.example.timespenttracker.databinding.FragmentMonthBinding;
import com.example.timespenttracker.model.AppUsageDetails;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;

public class MonthFragment extends Fragment {
    private final Calendar calendar = Calendar.getInstance();
    private MonthViewModel monthViewModel;
    private FragmentMonthBinding binding;
    private AppUsageAdapter adapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        monthViewModel =
                new ViewModelProvider(this).get(MonthViewModel.class);
        binding = FragmentMonthBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(monthViewModel);

        monthViewModel.getAppUsageList().observe(getViewLifecycleOwner(), new Observer<List<AppUsageDetails>>() {
            @Override
            public void onChanged(List<AppUsageDetails> list) {
                setupRecyclerView(list);
                Toast.makeText(getActivity(), "month", Toast.LENGTH_SHORT).show();
            }
        });
        binding.monthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int yearSelected = calendar.get(Calendar.YEAR);
                int monthSelected = calendar.get(Calendar.MONTH);
                calendar.add(Calendar.YEAR, -10);
                MonthYearPickerDialogFragment dialogFragment = MonthYearPickerDialogFragment
                        .getInstance(monthSelected, yearSelected, calendar.getTimeInMillis(), Calendar.getInstance().getTimeInMillis());
                dialogFragment.setOnDateSetListener(new MonthYearPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int year, int monthOfYear) {
                        calendar.set(year, monthOfYear, 1);
                        calendar.getTime();
                        monthViewModel.setDate(calendar);
                    }
                });
                dialogFragment.show(getFragmentManager(), null);
            }
        });
        return binding.getRoot();
    }

    private void setupRecyclerView(List<AppUsageDetails> data) {
        adapter = new AppUsageAdapter(data);
        RecyclerView recyclerView = binding.recyclerviewMonthUsage;
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