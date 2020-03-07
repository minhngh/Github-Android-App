package com.klxl.github.data;

import android.app.Application;

import com.klxl.github.data.local.HistoryFind;
import com.klxl.github.data.local.HistoryFindDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public class HistoryFindRepository {
    private HistoryFindDatabase historyFindDatabase;
    public HistoryFindRepository(Application application){
        historyFindDatabase = HistoryFindDatabase.getInstance(application);
    }
    public Maybe<List<HistoryFind>> getHistoryFindList(){
        return historyFindDatabase.getHistoryFindDao().getHistoryFindList();
    }
    public Completable insert(HistoryFind historyFind){
        return historyFindDatabase.getHistoryFindDao().insert(historyFind);
    }
    public Completable deleteAllHistoryFinds(){
        return historyFindDatabase.getHistoryFindDao().deleteAllHistoryFinds();
    }
}
