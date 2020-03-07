package com.klxl.github.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.klxl.github.data.HistoryFindRepository;
import com.klxl.github.data.Result;
import com.klxl.github.data.local.HistoryFind;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HistoryFindViewModel extends AndroidViewModel {
    private HistoryFindRepository repository;
    private static HistoryFindViewModel instance;
    private final MutableLiveData<List<HistoryFind>> historyFindList = new MutableLiveData<>();
    private final MutableLiveData<Result> result = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable;
    public HistoryFindViewModel(@NonNull Application application) {
        super(application);
        repository = new HistoryFindRepository(application);
        compositeDisposable = new CompositeDisposable();
    }
    public static HistoryFindViewModel getInstance(Application application){
        if (instance == null){
            synchronized (HistoryFindViewModel.class){
                if (instance == null){
                    instance = new HistoryFindViewModel(application);
                }
            }
        }
        return instance;
    }

    public void getAllHistoryFinds(){
        repository.getHistoryFindList()
                .subscribeOn(Schedulers.io())
                .subscribe(new MaybeObserver<List<HistoryFind>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (compositeDisposable == null)
                            compositeDisposable = new CompositeDisposable();
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<HistoryFind> historyFinds) {
                        historyFindList.postValue(historyFinds);
                        result.postValue(new Result.Success<>(true));
                    }

                    @Override
                    public void onError(Throwable e) {
                        result.postValue(new Result.Failure(new Exception()));
                    }

                    @Override
                    public void onComplete() {
                        result.postValue(new Result.Success<>(true));
                        historyFindList.postValue(new ArrayList<>());
                    }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }
}
