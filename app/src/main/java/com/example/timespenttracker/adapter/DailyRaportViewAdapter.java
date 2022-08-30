package com.example.timespenttracker.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timespenttracker.databinding.ItemRaportDayBinding;
import com.example.timespenttracker.model.DayStats;

import java.util.List;

public class DailyRaportViewAdapter extends RecyclerView.Adapter<DailyRaportViewAdapter.ViewHolder> {
    private List<DayStats> dailyStatsList;

    public DailyRaportViewAdapter() {
    }

    public void setData(List<DayStats> data) {
        this.dailyStatsList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DailyRaportViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemRaportDayBinding itemBinding = ItemRaportDayBinding.inflate(layoutInflater, parent, false);
        return new DailyRaportViewAdapter.ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyRaportViewAdapter.ViewHolder holder, int position) {
        DayStats dayStats = dailyStatsList.get(position);
        holder.bind(dayStats);
    }

    @Override
    public int getItemCount() {
        return dailyStatsList != null ? dailyStatsList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemRaportDayBinding binding;

        public ViewHolder(ItemRaportDayBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DayStats dayStats) {
            binding.setDayStats(dayStats);
            binding.executePendingBindings();
        }
    }
}
