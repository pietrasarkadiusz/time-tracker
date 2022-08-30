package com.example.timespenttracker.ui.day;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timespenttracker.adapter.AppUsageAdapter;
import com.example.timespenttracker.databinding.FragmentDayBinding;
import com.example.timespenttracker.model.AppUsageDetails;
import com.example.timespenttracker.util.DateTransUtil;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;

public class DayFragment extends Fragment {
    private final Calendar calendar = Calendar.getInstance();
    private DayViewModel dayViewModel;
    private DatePickerDialog picker;
    private FragmentDayBinding binding;
    private AppUsageAdapter adapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dayViewModel =
                new ViewModelProvider(this).get(DayViewModel.class);
        binding = FragmentDayBinding.inflate(inflater, container, false);
        binding.setViewModel(dayViewModel);
        binding.setLifecycleOwner(this);
        dayViewModel.getAppUsageList().observe(getViewLifecycleOwner(), new Observer<List<AppUsageDetails>>() {
            @Override
            public void onChanged(List<AppUsageDetails> list) {
                setupRecyclerView(list);
                Toast.makeText(getActivity(), "day", Toast.LENGTH_SHORT).show();
            }
        });
        binding.dayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(year, month, dayOfMonth);
                                dayViewModel.setDate(DateTransUtil.getDayStart(calendar));
                            }
                        }, year, month, day);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
                picker.show();
            }
        });
        return binding.getRoot();
    }

    private void setupRecyclerView(List<AppUsageDetails> data) {
        adapter = new AppUsageAdapter(data);
        RecyclerView recyclerView = binding.recyclerviewDayUsage;
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

