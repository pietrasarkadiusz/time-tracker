package com.example.timespenttracker.util;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateTransUtil {

    public static long getMonthEnd(@NotNull Calendar date) {
        date.set(Calendar.DAY_OF_MONTH, date.getActualMaximum(Calendar.DAY_OF_MONTH) + 1);
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        return date.getTimeInMillis();
    }

    public static long getMonthStart(@NotNull Calendar date) {
        date.set(Calendar.DAY_OF_MONTH, 1);
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        return date.getTimeInMillis();
    }

    public static long getDayStart(@NotNull Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        return date.getTimeInMillis();
    }

    public static long getDayEnd(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getWeekStart(@NotNull Calendar date) {
        date.set(Calendar.DAY_OF_WEEK, date.getFirstDayOfWeek());
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        return date.getTimeInMillis();
    }

    public static long getWeekStart(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static String millisToHMSFormat(long j) {
        long j2 = TimeUnit.MILLISECONDS.toHours(j);
        long j3 = TimeUnit.MILLISECONDS.toMinutes(j) % 60;
        long j4 = TimeUnit.MILLISECONDS.toSeconds(j) % 60;
        StringBuilder stringBuilder = new StringBuilder();
        if (j2 != 0) {
            stringBuilder.append(j2).append("h ");
        }
        if (j3 != 0) {
            stringBuilder.append(j3).append("m ");
        }
        if (j4 != 0) {
            stringBuilder.append(j4).append("s");
        }
        return stringBuilder.toString();
    }

    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public static long getStartTime(int paramInt) {
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(11);
        int j = calendar.get(12);
        int k = calendar.get(13);
        int m = calendar.get(14);
        calendar.add(10, paramInt * -1 * 24);
        calendar.add(13, k * -1);
        calendar.add(12, j * -1);
        calendar.add(10, i * -1);
        calendar.add(14, m * -1);
        return calendar.getTimeInMillis();
    }

    public static String stampToDateHHMM(long paramLong) {
        return (new SimpleDateFormat("HH:mm")).format(new Date(paramLong));
    }

    public static String stampToDateDDMM(long paramLong) {
        return (new SimpleDateFormat("dd-MM")).format(new Date(paramLong));
    }

    public static String stampToDateDDMMM(long paramLong) {
        return (new SimpleDateFormat("dd MMM")).format(new Date(paramLong));
    }

    public static String stampToDateLLLLYY(Calendar paramLong) {
        return (new SimpleDateFormat("LLLL yyyy", Locale.getDefault())).format(paramLong.getTime());
    }

    public static String stampToDateLLLLYY(long paramLong) {
        return (new SimpleDateFormat("LLLL yyyy", Locale.getDefault())).format(new Date(paramLong));
    }

    public static String stampToDateDDMMYY(long paramLong) {
        return (new SimpleDateFormat("dd-MM-yyyy")).format(new Date(paramLong));
    }

    public static String stampToDateDDMMMYY(long paramLong) {
        return (new SimpleDateFormat("dd MMM, yyyy")).format(new Date(paramLong));
    }

    public static String stampToDateDDLLLLYY(long paramLong) {
        return (new SimpleDateFormat("dd LLLL yyyy")).format(new Date(paramLong));
    }

    public static String stampToDateTest(Calendar calendar) {
        return (new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z")).format(calendar.getTime());
    }

}

