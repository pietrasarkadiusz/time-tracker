package com.example.timespenttracker.adapter;

import android.content.Context;

import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.BindingAdapter;

import com.example.timespenttracker.R;
import com.example.timespenttracker.model.AppUsageAndLaunches;
import com.example.timespenttracker.model.AppUsageOnlyUsage;
import com.example.timespenttracker.model.TodayPieChartData;
import com.example.timespenttracker.util.Constant;
import com.example.timespenttracker.util.DateTransUtil;
import com.example.timespenttracker.util.DayXAxisValueFormatter;
import com.example.timespenttracker.util.HMSValueFormatter;
import com.example.timespenttracker.util.MinutesValueFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class ChartBindingAdapter {

    @BindingAdapter("setTodayPieChartData")
    public static void setTodayPieChartData(PieChart pieChart, TodayPieChartData todayPieChartData) {
        if (todayPieChartData != null) {
            List<AppUsageOnlyUsage> appUsageList = todayPieChartData.getUsageList();
            ArrayList<PieEntry> values = new ArrayList<>();
            Context context = pieChart.getContext();
            int colorText = ResourcesCompat.getColor(context.getResources(), R.color.primary_text, context.getTheme());
            int colorHole = ResourcesCompat.getColor(context.getResources(), R.color.colorBackground, context.getTheme());
            int limit = appUsageList.size();
            long totalUsage = todayPieChartData.getTotalUsage();
            long usageTime = 0;
            for (int i = 0; i < limit; i++) {
                values.add(new PieEntry(appUsageList.get(i).getUsage(), appUsageList.get(i).getAppName()));
                usageTime += appUsageList.get(i).getUsage();
            }
            if (limit >= 4) values.add(new PieEntry((totalUsage - usageTime), "Inne"));
            PieDataSet dataSet = new PieDataSet(values, "Usage");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(Constant.COLORS);
            PieData data = new PieData(dataSet);
            data.setValueTextSize(0f);
            pieChart.setCenterTextColor(colorText);
            pieChart.setEntryLabelColor(colorText);
            pieChart.setHoleColor(colorHole);
            pieChart.setUsePercentValues(false);
            pieChart.getDescription().setEnabled(false);
            pieChart.setExtraOffsets(20, 20, 20, 20);
            pieChart.setCenterText(DateTransUtil.millisToHMSFormat(totalUsage) + "\nDzisiaj");
            pieChart.setDragDecelerationEnabled(true);
            pieChart.setHoleRadius(85f);
            pieChart.setDrawRoundedSlices(true);
            pieChart.getLegend().setEnabled(false);
            pieChart.setData(data);
            pieChart.invalidate();
        }
    }

    @BindingAdapter("setTodayBarChartData")
    public static void setTodayBarChartData(BarChart barChart, long[] list) {
        if (list != null) {
            ArrayList<BarEntry> values = new ArrayList<>();
            Context context = barChart.getContext();
            int colorText = ResourcesCompat.getColor(context.getResources(), R.color.primary_text, context.getTheme());
            for (int i = 1; i <= list.length; i++) {
                values.add(new BarEntry(i, list[i - 1]));
            }
            BarDataSet dataSet = new BarDataSet(values, "Usage");
            dataSet.setColors(Constant.COLORS);
            dataSet.setDrawValues(true);
            BarData data = new BarData(dataSet);
            data.setValueFormatter(new MinutesValueFormatter());
            data.setValueTextSize(11f);
            data.setValueTextColor(colorText);
            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setGranularity(1f);
            xAxis.setTextColor(colorText);
            barChart.setPinchZoom(false);
            barChart.getDescription().setEnabled(false);
            barChart.setDrawBarShadow(false);
            barChart.setClickable(false);
            barChart.setDrawGridBackground(false);
            barChart.getLegend().setEnabled(false);
            barChart.setHighlightPerTapEnabled(false);
            barChart.setScaleYEnabled(false);
            barChart.getAxisRight().setEnabled(false);
            barChart.getAxisLeft().setEnabled(false);
            barChart.getAxisLeft().setAxisMinimum(0.01f);
            barChart.animateY(1500);
            barChart.getDescription().setEnabled(false);
            barChart.setDrawValueAboveBar(true);
            barChart.setExtraOffsets(5, 5, 5, 5);
            barChart.setData(data);
            barChart.setFitBars(true);
            barChart.invalidate();
        }
    }

    @BindingAdapter("setWeekBarChartData")
    public static void setWeekBarChartData(BarChart barChart, List<AppUsageAndLaunches> list) {
        if (list != null) {
            ArrayList<BarEntry> values = new ArrayList<>();
            List<Long> xAxisValues = new ArrayList<>();
            Context context = barChart.getContext();
            int colorText = ResourcesCompat.getColor(context.getResources(), R.color.primary_text, context.getTheme());
            for (int i = 0; i < list.size(); i++) {
                values.add(new BarEntry(i, list.get(i).getUsage()));
                xAxisValues.add(list.get(i).getDate());
            }
            BarDataSet dataSet = new BarDataSet(values, "Usage");
            dataSet.setColors(Constant.COLORS);
            dataSet.setDrawValues(true);
            BarData data = new BarData(dataSet);
            data.setValueFormatter(new HMSValueFormatter());
            data.setValueTextSize(11f);
            data.setValueTextColor(colorText);
            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setGranularity(1f);
            xAxis.setTextColor(colorText);
            xAxis.setValueFormatter(new DayXAxisValueFormatter(xAxisValues));
            barChart.setPinchZoom(false);
            barChart.getDescription().setEnabled(false);
            barChart.setDrawBarShadow(false);
            barChart.setDrawGridBackground(false);
            barChart.getLegend().setEnabled(false);
            barChart.setHighlightPerTapEnabled(false);
            barChart.setScaleYEnabled(false);
            barChart.setClickable(false);
            barChart.getAxisRight().setEnabled(false);
            barChart.getAxisLeft().setEnabled(false);
            barChart.getAxisLeft().setAxisMinimum(0.01f);
            barChart.animateY(1500);
            barChart.getDescription().setEnabled(false);
            barChart.setDrawValueAboveBar(true);
            barChart.setExtraOffsets(5, 5, 5, 5);
            barChart.setData(data);
            barChart.setFitBars(true);
            barChart.invalidate();
        }
    }
}
