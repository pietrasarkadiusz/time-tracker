package com.example.timespenttracker.worker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.timespenttracker.R;
import com.example.timespenttracker.activity.MainActivity;
import com.example.timespenttracker.database.AppDatabase;
import com.example.timespenttracker.database.dao.AppUsageDao;
import com.example.timespenttracker.database.dao.PackageDao;
import com.example.timespenttracker.database.entity.AppUsage;
import com.example.timespenttracker.database.entity.Package;
import com.example.timespenttracker.util.AppUsageDataManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SavePhoneUsageWorker extends Worker {

    private static final String TAG = SavePhoneUsageWorker.class.getSimpleName();
    public final String CHANNEL_ID = "12";
    public final int NOTIFICATION_ID = 1;
    private final Context context;
    private PackageDao packageDao;
    private long lastTimeAppUsageSave = 0;
    private List<Package> packageList;
    private List<AppUsage> appUsageList = new ArrayList<>();

    public SavePhoneUsageWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    public static void periodicWorkRequest() {
        PeriodicWorkRequest sendLogsWorkRequest = new PeriodicWorkRequest.Builder(SavePhoneUsageWorker.class, 20, TimeUnit.HOURS)
                .addTag("SavePhoneUsageWorker")
                .build();
        WorkManager.getInstance().enqueueUniquePeriodicWork(
                "SavePhoneUsageWorker",
                ExistingPeriodicWorkPolicy.REPLACE,
                sendLogsWorkRequest);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.i(TAG, "startWork");
        AppDatabase database = AppDatabase.getInstance(context);
        AppUsageDao appUsageDao = database.getAppUsageDao();
        packageDao = database.getPackageDao();
        lastTimeAppUsageSave = appUsageDao.getLastTimeAppUsageSave();
        saveAllPackageNames();
        packageList = packageDao.getPackageList();
        getOneTimeUsagesList();
        appUsageDao.insertAll(appUsageList);
        createNotificationChannel();
        createNotification();
        Log.i(TAG, "endWork");
        return Result.success();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "timeSpentTrackerChannel";
            String description = "Channel for timeSpentTracker notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
            notificationManagerCompat.createNotificationChannel(channel);
        }
    }

    public void createNotification() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_home_24)
                .setContentTitle("Event Reminder")
                .setContentText("Hello, 4 days remaining to event xyz")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    public void insertPackage(Package pkg) {
        packageDao.insert(pkg);
    }

    public void getOneTimeUsagesList() {
        this.appUsageList = new AppUsageDataManager(context, packageList).getFilteredAppUsageList(lastTimeAppUsageSave);
    }

    private void saveAllPackageNames() {
        List<String> packageNamesList = getNamesOfInstalledAppPackages();
        for (String packageName : packageNamesList) {
            if (!packageDao.checkIfExist(packageName)) {
                insertPackage(new Package(packageName));
            }
        }
    }

    private List<String> getNamesOfInstalledAppPackages() {
        PackageManager packageManager = context.getPackageManager();
        List<String> nonSystemPackages = new ArrayList<>();
        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(0);
        for (ApplicationInfo applicationInfo : installedApplications) {
            if (packageManager.getLaunchIntentForPackage(applicationInfo.packageName) != null) {
                nonSystemPackages.add(applicationInfo.packageName);
            }
        }
        return nonSystemPackages;
    }

    boolean isUserApp(ApplicationInfo ai) {
        int mask = ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP;
        return (ai.flags & mask) == 0;
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        if ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0)
            return false;
        else if ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0)
            return true;
        else return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_INSTALLED) == 0;
    }

    private boolean isSystemPackage2(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM);
    }

    private boolean isSystemPackage3(String pkg) {
        PackageManager packageManager = context.getPackageManager();
        String systemPackageName = "android";
        return packageManager.checkSignatures(systemPackageName, pkg) == PackageManager.SIGNATURE_MATCH;
    }
}
