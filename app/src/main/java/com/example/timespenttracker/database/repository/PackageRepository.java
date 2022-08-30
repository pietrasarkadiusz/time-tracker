package com.example.timespenttracker.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.timespenttracker.database.AppDatabase;
import com.example.timespenttracker.database.dao.PackageDao;
import com.example.timespenttracker.database.entity.Package;

public class PackageRepository {
    private final PackageDao packageDao;

    public PackageRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        packageDao = database.getPackageDao();
    }

    public void deleteAll() {
        packageDao.deleteALL();
    }

    public boolean checkIfExist(String packageName) {
        return packageDao.checkIfExist(packageName);
    }

    private class InsertPackageAsyncTask extends AsyncTask<Package, Void, Void> {
        private final PackageDao packageDaoAsync;

        private InsertPackageAsyncTask(PackageDao packageDao) {
            packageDaoAsync = packageDao;
        }

        @Override
        protected Void doInBackground(Package... packages) {
            packageDaoAsync.insert(packages[0]);
            return null;
        }
    }
}
