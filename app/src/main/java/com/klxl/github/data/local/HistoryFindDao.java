package com.klxl.github.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface HistoryFindDao {
    @Query("SELECT * FROM History")
    Maybe<List<HistoryFind>> getHistoryFindList();
    @Insert
    Completable insert(HistoryFind historyFind);
    @Query("DELETE FROM History")
    Completable deleteAllHistoryFinds();
}
