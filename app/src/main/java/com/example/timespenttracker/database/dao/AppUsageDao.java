package com.example.timespenttracker.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.timespenttracker.database.entity.AppUsage;
import com.example.timespenttracker.model.AppUsageAndLaunches;
import com.example.timespenttracker.model.AppUsageDetails;
import com.example.timespenttracker.model.AppUsageMinimal;
import com.example.timespenttracker.model.AppUsageOnlyLaunches;
import com.example.timespenttracker.model.AppUsageOnlyUsage;

import java.util.List;

@Dao
public interface AppUsageDao {

    @Query("SELECT (date+usage) FROM AppUsage WHERE date = (SELECT MAX(date) FROM AppUsage)")
    long getLastTimeAppUsageSave();

    @Query("DELETE FROM AppUsage")
    void deleteALL();

    @Transaction
    @Query("SELECT packageName, usage AS usage, 1 AS launches, date FROM AppUsage INNER JOIN package ON AppUsage.packageId = package.id WHERE date > :startDate AND date <= :endDate ORDER BY date DESC")
    LiveData<List<AppUsageDetails>> getMostUsedApps(long startDate, long endDate);

    @Transaction
    @Query("SELECT packageName, sum(usage) AS usage, sum(packageName) AS launches, date FROM AppUsage INNER JOIN package ON AppUsage.packageId = package.id WHERE date > :startDate AND date <= :endDate GROUP BY packageName ORDER BY usage DESC")
    LiveData<List<AppUsageDetails>> getAppsUsageSummary(long startDate, long endDate);

    @Transaction
    @Query("SELECT packageName, sum(usage) AS usage FROM AppUsage INNER JOIN package ON AppUsage.packageId = package.id WHERE date > :startDate AND date <= :endDate GROUP BY packageName ORDER BY usage DESC")
    LiveData<List<AppUsageOnlyUsage>> getMostUsedAppsSummary(long startDate, long endDate);

    @Transaction
    @Query("SELECT packageName, sum(usage) AS usage FROM AppUsage INNER JOIN package ON AppUsage.packageId = package.id WHERE date > :startDate AND date <= :endDate GROUP BY packageName ORDER BY usage DESC LIMIT 4")
    LiveData<List<AppUsageOnlyUsage>> getMostUsedAppsSummaryForPieChart(long startDate, long endDate);

    @Transaction
    @Query("SELECT packageName, count(packageName) AS launches FROM AppUsage INNER JOIN package ON AppUsage.packageId = package.id WHERE date > :startDate AND date <= :endDate GROUP BY packageName ORDER BY launches DESC")
    LiveData<List<AppUsageOnlyLaunches>> getMostLaunchedAppsSummary(long startDate, long endDate);

    @Transaction
    @Query("SELECT packageName, usage AS usage, 1 AS launches, date FROM AppUsage INNER JOIN package ON AppUsage.packageId = package.id WHERE date >= :startDate AND date < :endDate ORDER BY date ASC")
    LiveData<List<AppUsageDetails>> getUsedApps(long startDate, long endDate);

    @Transaction
    @Query("SELECT sum(usage) FROM AppUsage INNER JOIN package ON AppUsage.packageId = package.id WHERE packageName == :packageName AND date >= :startDate AND date < :endDate GROUP BY strftime('%Y %m %d', datetime(date/1000, 'unixepoch', 'localtime'))")
    LiveData<List<Long>> getAppUsageDaySummary(long startDate, long endDate, String packageName);

    @Transaction
    @Query("SELECT date ,sum(usage) as usage, count(packageName) as launches FROM AppUsage INNER JOIN package ON AppUsage.packageId = package.id WHERE packageName == :packageName AND date >= :startDate AND date < :endDate GROUP BY strftime('%Y %m %d', datetime(date/1000, 'unixepoch', 'localtime'))")
    LiveData<List<AppUsageAndLaunches>> getDailyStatsOnePackage(long startDate, long endDate, String packageName);

    @Transaction
    @Query("SELECT date ,sum(usage) as usage, count(packageName) as launches FROM AppUsage INNER JOIN package ON AppUsage.packageId = package.id WHERE date >= :startDate AND date < :endDate GROUP BY strftime('%Y %m %d', datetime(date/1000, 'unixepoch', 'localtime'))")
    LiveData<List<AppUsageAndLaunches>> getDailyStats(long startDate, long endDate);

    @Transaction
    @Query("SELECT date ,sum(usage) as usage, count(packageName) as launches FROM AppUsage INNER JOIN package ON AppUsage.packageId = package.id GROUP BY strftime('%d %m %Y', datetime(date/1000, 'unixepoch', 'localtime')) ORDER BY date DESC")
    LiveData<List<AppUsageAndLaunches>> getAllDailyStats();

    @Transaction
    @Query("SELECT date ,sum(usage) as usage, count(packageName) as launches FROM AppUsage INNER JOIN package ON AppUsage.packageId = package.id GROUP BY strftime('%W %Y', datetime(date/1000, 'unixepoch', 'localtime')) ORDER BY date DESC")
    LiveData<List<AppUsageAndLaunches>> getAllWeeklyStats();

    @Transaction
    @Query("SELECT date ,sum(usage) as usage, count(packageName) as launches FROM AppUsage INNER JOIN package ON AppUsage.packageId = package.id GROUP BY strftime('%m %Y', datetime(date/1000, 'unixepoch', 'localtime')) ORDER BY date DESC")
    LiveData<List<AppUsageAndLaunches>> getAllMonthlyStats();

    @Transaction
    @Query("SELECT packageName, usage AS usage, 1 AS launches, date FROM AppUsage INNER JOIN package ON AppUsage.packageId = package.id WHERE packageName == :packageName AND date >= :startDate AND date < :endDate ORDER BY date ASC")
    LiveData<List<AppUsageDetails>> getUsedAppOnePackage(long startDate, long endDate, String packageName);

    @Transaction
    @Query("SELECT sum(usage) AS usage, count(packageName) AS launches FROM AppUsage INNER JOIN package ON AppUsage.packageId = package.id WHERE packageName == :packageName AND date >= :startDate AND date < :endDate")
    LiveData<AppUsageMinimal> getAppUsageMinimalOnePackage(long startDate, long endDate, String packageName);

    @Transaction
    @Query("SELECT sum(usage) AS usage, count(packageName) AS launches FROM AppUsage INNER JOIN package ON AppUsage.packageId = package.id WHERE date >= :startDate AND date < :endDate")
    LiveData<AppUsageMinimal> getAppUsageDetails(long startDate, long endDate);

    @Transaction
    @Query("SELECT packageName, sum(usage) AS usage FROM AppUsage, package WHERE AppUsage.packageId = package.id AND date >= :startDate AND date < :endDate GROUP BY packageName ORDER BY usage DESC LIMIT 1")
    LiveData<AppUsageOnlyUsage> getMostUsedApp(long startDate, long endDate);

    @Transaction
    @Query("SELECT packageName, count(packageName) AS launches FROM AppUsage, package WHERE AppUsage.packageId = package.id AND date >= :startDate AND date < :endDate GROUP BY packageName ORDER BY launches DESC LIMIT 1")
    LiveData<AppUsageOnlyLaunches> getMostLaunchedApp(long startDate, long endDate);

    @Insert
    void insertAll(List<AppUsage> appUsageList);

    @Insert
    void insert(AppUsage appUsage);

    @Delete
    void delete(AppUsage appUsage);

    @Update
    void update(AppUsage appUsage);


}