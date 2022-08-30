package com.example.timespenttracker.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timespenttracker.databinding.ItemAppMostVisitedBinding;
import com.example.timespenttracker.model.AppUsageOnlyLaunches;
import com.example.timespenttracker.util.MyHandler;

import java.util.List;

public class MostVisitedAppsViewAdapter extends RecyclerView.Adapter<MostVisitedAppsViewAdapter.ViewHolder> {
    private List<AppUsageOnlyLaunches> appUsageList;

    public MostVisitedAppsViewAdapter() {
    }

    public void setData(List<AppUsageOnlyLaunches> data) {
        this.appUsageList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MostVisitedAppsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemAppMostVisitedBinding itemBinding = ItemAppMostVisitedBinding.inflate(layoutInflater, parent, false);
        return new MostVisitedAppsViewAdapter.ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MostVisitedAppsViewAdapter.ViewHolder holder, int position) {
        AppUsageOnlyLaunches appUsage = appUsageList.get(position);
        holder.bind(appUsage);
    }

    @Override
    public int getItemCount() {
        return appUsageList != null ? appUsageList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemAppMostVisitedBinding binding;

        public ViewHolder(ItemAppMostVisitedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(AppUsageOnlyLaunches appUsageOnlyLaunches) {
            binding.setAppUsageDetails(appUsageOnlyLaunches);
            binding.setHandler(new MyHandler());
            binding.executePendingBindings();
        }
    }
}
