package com.example.timespenttracker.util;

import com.github.mikephil.charting.formatter.ValueFormatter;

public class MinutesValueFormatter extends ValueFormatter {

    public MinutesValueFormatter() {
    }

    @Override
    public String getFormattedValue(float value) {
        return (int) value + "m";
    }
}