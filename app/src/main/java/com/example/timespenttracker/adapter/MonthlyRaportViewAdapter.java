package com.example.timespenttracker.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timespenttracker.databinding.ItemRaportMonthBinding;
import com.example.timespenttracker.model.MonthStats;

import java.util.List;

public class MonthlyRaportViewAdapter extends RecyclerView.Adapter<MonthlyRaportViewAdapter.ViewHolder> {
    private List<MonthStats> monthStatsList;

    public MonthlyRaportViewAdapter() {
    }

    public void setData(List<MonthStats> data) {
        this.monthStatsList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MonthlyRaportViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemRaportMonthBinding itemBinding = ItemRaportMonthBinding.inflate(layoutInflater, parent, false);
        return new MonthlyRaportViewAdapter.ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthlyRaportViewAdapter.ViewHolder holder, int position) {
        MonthStats monthStats = monthStatsList.get(position);
        holder.bind(monthStats);
    }

    @Override
    public int getItemCount() {
        return monthStatsList != null ? monthStatsList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemRaportMonthBinding binding;

        public ViewHolder(ItemRaportMonthBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MonthStats monthStats) {
            binding.setMonthStats(monthStats);
            binding.executePendingBindings();
        }
    }
}
