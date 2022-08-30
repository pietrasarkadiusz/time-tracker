package com.example.timespenttracker.activity.timeline;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timespenttracker.adapter.TimeLineAdapter;
import com.example.timespenttracker.databinding.ActivityTimelineBinding;
import com.example.timespenttracker.model.AppUsageDetails;
import com.example.timespenttracker.util.DateTransUtil;

import java.util.Calendar;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {
    private final Calendar calendar = Calendar.getInstance();
    private TimelineViewModel timelineViewModel;
    private DatePickerDialog picker;
    private ActivityTimelineBinding binding;
    private TimeLineAdapter adapter;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        timelineViewModel =
                new ViewModelProvider(this).get(TimelineViewModel.class);
        binding = ActivityTimelineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.setViewModel(timelineViewModel);
        binding.setLifecycleOwner(this);
        setSupportActionBar(binding.toolbarTimeline);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupRecyclerView();
        timelineViewModel.getAppUsageList().observe(this, new Observer<List<AppUsageDetails>>() {
            @Override
            public void onChanged(List<AppUsageDetails> list) {
                adapter.setData(list);
            }
        });
        binding.dayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                picker = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(year, month, dayOfMonth);
                                timelineViewModel.setDate(DateTransUtil.getDayStart(calendar));
                            }
                        }, year, month, day);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
                picker.show();
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new TimeLineAdapter();
        RecyclerView recyclerView = binding.timelineRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
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