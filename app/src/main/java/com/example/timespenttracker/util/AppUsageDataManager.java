package com.example.timespenttracker.util;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import com.example.timespenttracker.database.entity.AppUsage;
import com.example.timespenttracker.database.entity.Package;

import java.util.ArrayList;
import java.util.List;

public class AppUsageDataManager {
    private final Context context;
    private final List<Package> packageList;
    private final List<AppUsage> filteredAppUsageList = new ArrayList<>();
    private final List<AppUsage> appUsageList = new ArrayList<>();
    private List<String> test = new ArrayList<>();
    private List<UsageEvents.Event> aaaa = new ArrayList<>();

    public AppUsageDataManager(Context paramContext, List<Package> packageList) {
        this.context = paramContext;
        this.packageList = packageList;
    }

    public static String getLauncherPackageName(Context paramContext) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        ResolveInfo resolveInfo = paramContext.getPackageManager().resolveActivity(intent, 0);
        return (resolveInfo == null) ? "null" : ((resolveInfo.activityInfo == null) ? "null" :
                (resolveInfo.activityInfo.packageName.equals("android") ? "null" : resolveInfo.activityInfo.packageName));
    }

    public void getAppUsage(long startDate, long endDate) {
        String launcherPackageName = getLauncherPackageName(context);
        UsageEvents usageEvents = ((UsageStatsManager) context.getSystemService("usagestats")).queryEvents(startDate, endDate);
        UsageEvents.Event firstEvent = null;
        UsageEvents.Event oldEvent = null;
        String firstEventPackage;
        String secondEventPackage;
        long usage;
        while (usageEvents.hasNextEvent()) {
            UsageEvents.Event secondEvent = new UsageEvents.Event();
            usageEvents.getNextEvent(secondEvent);
            if (secondEvent.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED || secondEvent.getEventType() == UsageEvents.Event.ACTIVITY_PAUSED) {
                if (firstEvent != null) {
                    test.add(firstEvent.getPackageName());
                    aaaa.add(firstEvent);
                    firstEventPackage = firstEvent.getPackageName();
                    secondEventPackage = secondEvent.getPackageName();
                    if (firstEvent.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED && secondEvent.getEventType() == UsageEvents.Event.ACTIVITY_PAUSED &&
                            firstEventPackage.equals(secondEventPackage)){
                        usage = secondEvent.getTimeStamp() - firstEvent.getTimeStamp();
                        AppUsage appUsage = new AppUsage(firstEvent.getTimeStamp(), 0, usage);
                        appUsage.setPackageName(firstEventPackage);
                        appUsageList.add(appUsage);
                    } else {
                        if ((oldEvent == null) && launcherPackageName.equals(secondEventPackage) && !launcherPackageName.equals(firstEventPackage) &&
                                firstEvent.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED) {
                            oldEvent = firstEvent;
                        } else if ((oldEvent != null) && launcherPackageName.equals(firstEventPackage) && !launcherPackageName.equals(secondEventPackage)) {
                            if (oldEvent.getPackageName().equals(secondEventPackage) && secondEvent.getEventType() == UsageEvents.Event.ACTIVITY_PAUSED) {
                                usage = secondEvent.getTimeStamp() - oldEvent.getTimeStamp();
                                AppUsage appUsage = new AppUsage(oldEvent.getTimeStamp(), 0, usage);
                                appUsage.setPackageName(oldEvent.getPackageName());
                                appUsageList.add(appUsage);
                            } else oldEvent = null;
                        }
                    }
                }
                firstEvent = secondEvent;
            }
        }
        int a = 2 +10;
        int asda = a+20;
    }

//    public void getAppUsage2(long startDate, long endDate) {
//        UsageEvents usageEvents = ((UsageStatsManager) context.getSystemService("usagestats")).queryEvents(startDate, endDate);
//        UsageEvents.Event firstEvent = null;
//        while (usageEvents.hasNextEvent()) {
//            UsageEvents.Event secondEvent = new UsageEvents.Event();
//            usageEvents.getNextEvent(secondEvent);
//            if (secondEvent.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED || secondEvent.getEventType() == UsageEvents.Event.ACTIVITY_PAUSED) {
//                if (firstEvent != null) {
//                    if (firstEvent.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED && secondEvent.getEventType() == UsageEvents.Event.ACTIVITY_PAUSED && firstEvent.getPackageName().equals(secondEvent.getPackageName())) {
//                        long usage = secondEvent.getTimeStamp() - firstEvent.getTimeStamp();
//                        if (usage > 1000L) {
//                            AppUsage appUsage = new AppUsage(firstEvent.getTimeStamp(), 0, usage);
//                            appUsage.setPackageName(firstEvent.getPackageName());
//                            appUsageList2.add(appUsage);
//                        }
//                    }
//                }
//                firstEvent = secondEvent;
//            }
//        }
//    }

    public List<AppUsage> getFilteredAppUsageList(long startDate) {
        if (startDate == 0) startDate = DateTransUtil.getStartTime(0);
        getAppUsage(startDate, DateTransUtil.getCurrentTime());
        filterAppUsageList();
        return filteredAppUsageList;
    }

    private void filterAppUsageList() {
        int i = 0;
        int j;
        while (i < appUsageList.size() - 1) {
            j = i + 1;
            if (appUsageList.get(i).getPackageName().equals(appUsageList.get(j).getPackageName())) {
                appUsageList.get(i).setUsage((appUsageList.get(i)).getUsage() + appUsageList.get(j).getUsage());
                appUsageList.remove(j);
            } else i = j;

        }
        i = 0;
        while (i < appUsageList.size()) {
            AppUsage appUsage = appUsageList.get(i);
            if (appUsage.getUsage() < 1000L) {
                appUsageList.remove(appUsage);
            } else {
                long id = getPackageId(appUsage.getPackageName());
                appUsage.setPackageId(id);
                if (id != 0) {
                    String startDate = DateTransUtil.stampToDateDDMMYY(appUsage.getDate());
                    String endDate = DateTransUtil.stampToDateDDMMYY(appUsage.getDate() + appUsage.getUsage());
                    if (!startDate.equals(endDate)) {
                        long newDate = DateTransUtil.getDayEnd(appUsage.getDate());
                        long oldUsage = appUsage.getUsage();
                        long newUsage = newDate - appUsage.getDate();
                        appUsage.setUsage(newUsage);
                        filteredAppUsageList.add(appUsage);
                        appUsage.setDate(newDate);
                        appUsage.setUsage(oldUsage - newUsage);
                    }
                    filteredAppUsageList.add(appUsage);
                }
                i++;
            }
        }
    }

    private long getPackageId(String packageName) {
        for (Package pkg : packageList) {
            if (pkg.getPackageName().equals(packageName))
                return pkg.getId();
        }
        return 0;
    }
}
