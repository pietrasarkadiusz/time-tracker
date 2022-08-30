package com.example.timespenttracker.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.timespenttracker.database.entity.Package;

import java.util.List;

@Dao
public interface PackageDao {

    @Query("SELECT * FROM package")
    LiveData<List<Package>> getAll();

    @Query("SELECT * FROM package")
    List<Package> getPackageList();

    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insert(Package pkg);

    @Delete
    void delete(Package pkg);

    @Update
    void update(Package pkg);

    @Query("DELETE FROM package")
    void deleteALL();

    @Query("SELECT EXISTS(SELECT 1 FROM package WHERE packageName=:packageName LIMIT 1);")
    boolean checkIfExist(String packageName);
}