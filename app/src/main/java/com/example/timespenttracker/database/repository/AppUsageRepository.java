package com.example.timespenttracker.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.timespenttracker.database.AppDatabase;
import com.example.timespenttracker.database.dao.AppUsageDao;
import com.example.timespenttracker.database.entity.AppUsage;
import com.example.timespenttracker.model.AppUsageDetails;
import com.example.timespenttracker.model.AppUsageAndLaunches;
import com.example.timespenttracker.model.AppUsageMinimal;
import com.example.timespenttracker.model.AppUsageOnlyUsage;
import com.example.timespenttracker.model.AppUsageOnlyLaunches;
import com.example.timespenttracker.model.DayStats;
import com.example.timespenttracker.model.MonthStats;
import com.example.timespenttracker.model.WeekStats;
import com.example.timespenttracker.util.AppUsageUtil;
import com.example.timespenttracker.util.DateTransUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AppUsageRepository {
    private final AppUsageDao appUsageDao;

    public AppUsageRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        appUsageDao = database.getAppUsageDao();
    }
    public LiveData<AppUsageOnlyUsage> getMostUsedApp(long startDate, long endDate){return appUsageDao.getMostUsedApp(startDate, endDate);}
    public LiveData<AppUsageOnlyLaunches> getMostLaunchedApp(long startDate, long endDate){return appUsageDao.getMostLaunchedApp(startDate, endDate);}
    public LiveData<AppUsageMinimal> getAppUsageMinimalOnePackage(long startDate, long endDate, String packageName){
        return appUsageDao.getAppUsageMinimalOnePackage(startDate, endDate, packageName);}
    public LiveData<AppUsageMinimal> getAppUsageMinimal(long startDate, long endDate){return appUsageDao.getAppUsageDetails(startDate, endDate);}
    public LiveData<List<AppUsageAndLaunches>> getDailyStatsOnePackage(long startDate, long endDate, String packageName){
        return Transformations.map(appUsageDao.getDailyStatsOnePackage(startDate,endDate,packageName), v -> AppUsageUtil.fixDailyUsageList(startDate,endDate,v)
        );
    }
    public LiveData<List<AppUsageAndLaunches>> getDailyStats(long startDate, long endDate){
        return appUsageDao.getDailyStats(startDate,endDate);
    }
    public LiveData<List<WeekStats>> getAllWeeklyStats(){
        return Transformations.map(appUsageDao.getAllWeeklyStats(), v->{
            List<WeekStats> result = new ArrayList<>();
            if(v!=null) {
                int limit = v.size()-1;
                for (int i = 0; i <limit; i++) {
                    result.add(new WeekStats(
                            v.get(i).getDate(),
                            v.get(i).getUsage(),
                            v.get(i).getLaunches(),
                            v.get(i+1).getUsage(),
                            v.get(i+1).getLaunches()
                    ));
                }
                if(limit>=0) {
                    result.add(new WeekStats(v.get(limit).getDate(), v.get(limit).getUsage(), v.get(limit).getLaunches(), 0, 0));
                }
            }
            return result;
        });
    }

    public LiveData<List<MonthStats>> getAllMonthlyStats(){
        return Transformations.map(appUsageDao.getAllMonthlyStats(), v->{
            List<MonthStats> result = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            if(v!=null) {
                int limit = v.size()-1;
                for (int i = 0; i <limit; i++) {
                    calendar.setTimeInMillis(v.get(i).getDate());
                    result.add(new MonthStats(
                            DateTransUtil.stampToDateLLLLYY(v.get(i).getDate()),
                            v.get(i).getUsage(),
                            v.get(i).getLaunches(),
                            v.get(i+1).getUsage(),
                            v.get(i+1).getLaunches(),
                            calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                    ));
                }
                if(limit>=0) {
                    calendar.setTimeInMillis(v.get(limit).getDate());
                    result.add(new MonthStats(DateTransUtil.stampToDateLLLLYY(v.get(limit).getDate()), v.get(limit).getUsage(), v.get(limit).getLaunches(), 0, 0, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)));
                }
            }
            return result;
        });
    }

    public LiveData<List<DayStats>> getAllDailyStats(){
        return Transformations.map(appUsageDao.getAllDailyStats(), v->{
            List<DayStats> result = new ArrayList<>();
            if(v!=null) {
                int limit = v.size()-1;
                for (int i = 0; i <limit; i++) {
                    result.add(new DayStats(
                            DateTransUtil.stampToDateDDMMMYY(v.get(i).getDate()),
                            v.get(i).getUsage(),
                            v.get(i).getLaunches(),
                            v.get(i+1).getUsage(),
                            v.get(i+1).getLaunches()
                    ));
                }
                if(limit>=0) {
                    result.add(new DayStats(DateTransUtil.stampToDateDDMMMYY(v.get(limit).getDate()), v.get(limit).getUsage(), v.get(limit).getLaunches(), 0, 0));
                }
            }
            return result;
        });
    }

    public LiveData<List<AppUsageDetails>> getUsedApps(long startDate, long endDate){
        return appUsageDao.getUsedApps(startDate, endDate);}

    public LiveData<List<AppUsageDetails>> getUsedAppOnePackage(long startDate, long endDate, String packageName){
        return appUsageDao.getUsedAppOnePackage(startDate, endDate, packageName);}

    public LiveData<List<Long>> getAppUsageDaySummary(long startDate, long endDate, String packageName){
        return appUsageDao.getAppUsageDaySummary(startDate, endDate, packageName);}

    public LiveData<List<AppUsageDetails>> getMostUsedApps(long startDate, long endDate){
        return appUsageDao.getMostUsedApps(startDate, endDate);}

    public LiveData<List<AppUsageOnlyUsage>> getMostUsedAppsSummary(long startDate, long endDate){
        return appUsageDao.getMostUsedAppsSummary(startDate, endDate);}

    public LiveData<List<AppUsageOnlyUsage>> getMostUsedAppsSummaryForPieChart(long startDate, long endDate){
        return appUsageDao.getMostUsedAppsSummaryForPieChart(startDate, endDate);}

    public LiveData<List<AppUsageDetails>> getAppsUsageSummary(long startDate, long endDate){
        return appUsageDao.getAppsUsageSummary(startDate, endDate);}

    public LiveData<List<AppUsageOnlyLaunches>> getMostLaunchedAppsSummary(long startDate, long endDate){
        return appUsageDao.getMostLaunchedAppsSummary(startDate, endDate);}

    private static class InsertSingleUseTimeAsyncTask extends AsyncTask<AppUsage, Void, Void>{
        private final AppUsageDao appUsageDao;

        private InsertSingleUseTimeAsyncTask(AppUsageDao appUsageDao){
            this.appUsageDao = appUsageDao;
        }

        @Override
        protected Void doInBackground(AppUsage... appUsages) {
            appUsageDao.insert(appUsages[0]);
            return null;
        }
    }

    public void deleteAll(){
        appUsageDao.deleteALL();}

}
