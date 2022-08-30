package com.example.timespenttracker.util;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.List;

public class DayXAxisValueFormatter extends ValueFormatter {
    final List<Long> dateList;

    public DayXAxisValueFormatter(List<Long> dateList) {
        this.dateList = dateList;
    }

    @Override
    public String getFormattedValue(float value) {
        int index = Math.round(value);
        if (index < 0 || index >= dateList.size() || index != (int) value)
            return "";
        return DateTransUtil.stampToDateDDMM(dateList.get(index));
    }
}