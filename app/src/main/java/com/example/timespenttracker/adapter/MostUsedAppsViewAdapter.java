package com.example.timespenttracker.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timespenttracker.databinding.ItemAppMostUsedBinding;
import com.example.timespenttracker.model.AppUsageOnlyUsage;
import com.example.timespenttracker.util.MyHandler;

import java.util.List;

public class MostUsedAppsViewAdapter extends RecyclerView.Adapter<MostUsedAppsViewAdapter.ViewHolder> {
    private List<AppUsageOnlyUsage> appUsageList;

    public MostUsedAppsViewAdapter() {
    }

    public void setData(List<AppUsageOnlyUsage> data) {
        this.appUsageList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MostUsedAppsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemAppMostUsedBinding itemBinding = ItemAppMostUsedBinding.inflate(layoutInflater, parent, false);
        return new MostUsedAppsViewAdapter.ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MostUsedAppsViewAdapter.ViewHolder holder, int position) {
        AppUsageOnlyUsage appUsage = appUsageList.get(position);
        holder.bind(appUsage);
    }

    @Override
    public int getItemCount() {
        return appUsageList != null ? appUsageList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemAppMostUsedBinding binding;

        public ViewHolder(ItemAppMostUsedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(AppUsageOnlyUsage appUsageOnlyVisits) {
            binding.setAppUsageDetails(appUsageOnlyVisits);
            binding.setHandler(new MyHandler());
            binding.executePendingBindings();
        }
    }
}
