package com.example.timespenttracker.database.entity;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Package.class,
        parentColumns = "id",
        childColumns = "packageId",
        onDelete = CASCADE))
public class AppUsage {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long date;
    @ColumnInfo(name = "packageId", index = true)
    private long packageId;
    private long usage;
    @Ignore
    private String packageName;
    public AppUsage(long date, long packageId, long usage) {
        this.date = date;
        this.packageId = packageId;
        this.usage = usage;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long id) { this.packageId = id;}

    public long getUsage() {
        return usage;
    }

    public void setUsage(long usage) { this.usage = usage; }

    public String getPackageName() { return packageName; }

    public void setPackageName(String packageName) { this.packageName = packageName; }
}
