package com.example.timespenttracker.util;

import com.example.timespenttracker.model.AppUsageAndLaunches;
import com.example.timespenttracker.model.AppUsageDetails;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AppUsageUtil {

    public static long[] getUsageInHourlyIntervals(long startTime, @NotNull List<AppUsageDetails> list) {
        long interval = startTime + Constant.MILLISECONDS_IN_HOUR;
        long[] result = new long[24];
        int i = 0;
        long usageTime = 0;
        for (AppUsageDetails appUsageDetailsModel : list) {
            while (appUsageDetailsModel.getDate() > interval) {
                if (i < result.length - 1) {
                    result[i] = TimeUnit.MILLISECONDS.toMinutes(Math.min(Constant.MILLISECONDS_IN_HOUR, usageTime));
                    i++;
                }
                interval += Constant.MILLISECONDS_IN_HOUR;
                usageTime = 0;
            }
            long endTime = appUsageDetailsModel.getDate() + appUsageDetailsModel.getUsage();
            if (endTime <= interval) {
                usageTime += appUsageDetailsModel.getUsage();
            } else {
                usageTime += (interval - appUsageDetailsModel.getDate());
                if (i < result.length - 1) {
                    result[i] = TimeUnit.MILLISECONDS.toMinutes(Math.min(Constant.MILLISECONDS_IN_HOUR, usageTime));
                    i++;
                }
                usageTime = (endTime - interval);
                interval += Constant.MILLISECONDS_IN_HOUR;
                while (usageTime > Constant.MILLISECONDS_IN_HOUR) {
                    if (i < result.length - 1) {
                        result[i] = TimeUnit.MILLISECONDS.toMinutes(Constant.MILLISECONDS_IN_HOUR);
                        i++;
                    }
                    usageTime -= Constant.MILLISECONDS_IN_HOUR;
                    interval += Constant.MILLISECONDS_IN_HOUR;
                }
            }
        }
        if (i < result.length - 1) {
            result[i] = TimeUnit.MILLISECONDS.toMinutes(Math.min(Constant.MILLISECONDS_IN_HOUR, usageTime));
        }
        return result;
    }

    public static List<AppUsageAndLaunches> fixDailyUsageList(long startDay, long endDay, List<AppUsageAndLaunches> list) {
        List<AppUsageAndLaunches> result = new ArrayList<>();
        long start = startDay;
        boolean isOldAdded;
        for (AppUsageAndLaunches appUsageAndLaunches : list) {
            do {
                if (DateTransUtil.stampToDateDDMMYY(appUsageAndLaunches.getDate()).equals(DateTransUtil.stampToDateDDMMYY(start))) {
                    result.add(appUsageAndLaunches);
                    isOldAdded = true;
                } else {
                    result.add(new AppUsageAndLaunches(start, 0, 0));
                    isOldAdded = false;
                }
                start += Constant.MILLISECONDS_IN_DAY;
            } while (!isOldAdded || start > endDay);
        }
        while (start < endDay) {
            result.add(new AppUsageAndLaunches(start, 0, 0));
            start += Constant.MILLISECONDS_IN_DAY;
        }
        return result;
    }
}
