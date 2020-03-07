package com.klxl.github.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = HistoryFind.class, exportSchema = false, version = 1)
public abstract class HistoryFindDatabase extends RoomDatabase {
    private static HistoryFindDatabase instance;
    public abstract HistoryFindDao getHistoryFindDao();
    public static HistoryFindDatabase getInstance(Context context){
        if (instance == null){
            synchronized (HistoryFindDatabase.class){
                if (instance == null){
                    instance = Room.databaseBuilder(context, HistoryFindDatabase.class, "HistoryFindDb.db").build();
                }
            }
        }
        return instance;
    }
}
