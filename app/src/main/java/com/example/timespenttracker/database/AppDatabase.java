package com.example.timespenttracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.timespenttracker.database.dao.AppUsageDao;
import com.example.timespenttracker.database.dao.PackageDao;
import com.example.timespenttracker.database.entity.AppUsage;
import com.example.timespenttracker.database.entity.Package;


@Database(entities = {AppUsage.class, Package.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "timeTracker_database";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract AppUsageDao getAppUsageDao();

    public abstract PackageDao getPackageDao();
}