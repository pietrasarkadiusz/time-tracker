package com.example.timespenttracker.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Package {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private final String packageName;

    public Package(String packageName) {
        this.packageName = packageName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }
}
