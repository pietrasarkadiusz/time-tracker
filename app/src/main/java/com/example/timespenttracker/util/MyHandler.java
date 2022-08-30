package com.example.timespenttracker.util;

import android.content.Intent;
import android.view.View;

import com.example.timespenttracker.activity.appDetails.AppDetailsActivity;

public class MyHandler {
    public void onClick(View view, String packageName) {
        Intent myIntent = new Intent(view.getContext(), AppDetailsActivity.class);
        myIntent.putExtra("PackageName", packageName);
        view.getContext().startActivity(myIntent);
    }
}
