package com.example.timespenttracker.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timespenttracker.databinding.ItemTimelineBinding;
import com.example.timespenttracker.model.AppUsageDetails;
import com.example.timespenttracker.util.MyHandler;

import java.util.List;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.ViewHolder> {
    private List<AppUsageDetails> appUsageDetailsList;

    public TimeLineAdapter() {
    }

    public void setData(List<AppUsageDetails> data) {
        this.appUsageDetailsList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TimeLineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemTimelineBinding itemBinding = ItemTimelineBinding.inflate(layoutInflater, parent, false);
        return new TimeLineAdapter.ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineAdapter.ViewHolder holder, int position) {
        AppUsageDetails appUsageDetails = appUsageDetailsList.get(position);
        holder.bind(appUsageDetails);
    }

    @Override
    public int getItemCount() {
        return appUsageDetailsList != null ? appUsageDetailsList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemTimelineBinding binding;

        public ViewHolder(ItemTimelineBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(AppUsageDetails appUsageDetails) {
            binding.setAppUsageDetails(appUsageDetails);
            binding.setHandler(new MyHandler());
            binding.executePendingBindings();
        }
    }

}
