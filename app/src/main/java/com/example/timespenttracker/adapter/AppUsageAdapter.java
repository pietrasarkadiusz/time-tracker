package com.example.timespenttracker.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timespenttracker.databinding.ItemAppUsageBinding;
import com.example.timespenttracker.model.AppUsageDetails;
import com.example.timespenttracker.util.MyHandler;

import java.util.List;

public class AppUsageAdapter extends RecyclerView.Adapter<AppUsageAdapter.ViewHolder> {
    private List<AppUsageDetails> appUsageDetailsList;
    public AppUsageAdapter(List<AppUsageDetails> data) {
        this.appUsageDetailsList = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemAppUsageBinding itemBinding = ItemAppUsageBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemBinding);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppUsageDetails appUsageDetails = appUsageDetailsList.get(position);
        holder.bind(appUsageDetails);
    }
    @Override
    public int getItemCount() {
        return appUsageDetailsList != null ? appUsageDetailsList.size() : 0;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemAppUsageBinding binding;

        public ViewHolder(ItemAppUsageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(AppUsageDetails appUsageDetails) {
            binding.setModel(appUsageDetails);
            binding.setHandler(new MyHandler());
            binding.executePendingBindings();
        }
    }
}

