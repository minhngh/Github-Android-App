package com.klxl.github.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "History")
public class HistoryFind {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;

    @ColumnInfo(name = "username")
    String userName;

    @ColumnInfo(name = "result")
    String result;

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getResult() {
        return result;
    }
}
