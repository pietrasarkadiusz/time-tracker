package com.example.timespenttracker.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timespenttracker.databinding.ItemRaportWeekBinding;
import com.example.timespenttracker.model.WeekStats;

import java.util.List;

public class WeeklyRaportViewAdapter extends RecyclerView.Adapter<WeeklyRaportViewAdapter.ViewHolder> {
    private List<WeekStats> weekStatsList;

    public WeeklyRaportViewAdapter() {
    }

    public void setData(List<WeekStats> data) {
        this.weekStatsList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeeklyRaportViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemRaportWeekBinding itemBinding = ItemRaportWeekBinding.inflate(layoutInflater, parent, false);
        return new WeeklyRaportViewAdapter.ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeeklyRaportViewAdapter.ViewHolder holder, int position) {
        WeekStats weekStats = weekStatsList.get(position);
        holder.bind(weekStats);
    }

    @Override
    public int getItemCount() {
        return weekStatsList != null ? weekStatsList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemRaportWeekBinding binding;

        public ViewHolder(ItemRaportWeekBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(WeekStats weekStats) {
            binding.setWeekStats(weekStats);
            binding.executePendingBindings();
        }
    }
}
