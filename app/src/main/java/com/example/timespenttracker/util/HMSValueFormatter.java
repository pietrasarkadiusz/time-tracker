package com.example.timespenttracker.util;

import com.example.timespenttracker.util.DateTransUtil;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class HMSValueFormatter extends ValueFormatter {

    public HMSValueFormatter(){}

    @Override
    public String getFormattedValue(float value) {
        return DateTransUtil.millisToHMSFormat((long) value);
    }
}