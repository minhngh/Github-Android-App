package com.klxl.github.viewmodel;

import android.app.Application;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.klxl.github.R;
import com.klxl.github.data.ProjectRepository;
import com.klxl.github.data.Result;
import com.klxl.github.data.model.Project;
import com.klxl.github.data.model.ProjectInView;
import com.klxl.github.ui.find.FindResult;
import com.klxl.github.ui.find.FindState;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;


public class ProjectViewModel extends ViewModel {
    private final int TIME_OUT_SECONDS = 60;
    private final MutableLiveData<FindState> findState = new MutableLiveData<>();
    private final MutableLiveData<FindResult> findResult = new MutableLiveData<>();
    private static ProjectViewModel instance;
    private CompositeDisposable compositeDisposable;

    public ProjectViewModel() {}

    public static ProjectViewModel getInstance(){
        if (instance == null){
            synchronized (ProjectViewModel.class){
                if (instance == null){
                    instance = new ProjectViewModel();
                }
            }
        }
        return instance;
    }

    private ProjectRepository projectRepository;

    private ProjectViewModel(Application application){
        super();
        this.projectRepository = new ProjectRepository();
        compositeDisposable = new CompositeDisposable();
    }

    public LiveData<FindState> getFindState() {
        return findState;
    }

    public LiveData<FindResult> getFindResult() {
        return findResult;
    }

    public LiveData<List<ProjectInView>> getAllProjects(){
        return projectRepository.getProjectList();
    }

    public void loginDataChanged(String username){
        if (!isUsernameValid(username)){
            findState.setValue(new FindState(R.string.invalid_username));
        }
        else{
            findState.setValue(new FindState(true));
        }
    }

    public void findProjectList(String username){
        projectRepository.getProjectListWithRemote(username)
        .subscribeOn(Schedulers.io())
        .timeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
        .subscribe(new SingleObserver<Result>() {
            @Override
            public void onSubscribe(Disposable d) {
                if (compositeDisposable == null)
                    compositeDisposable = new CompositeDisposable();
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(Result result) {
                findResult.postValue(new FindResult(FindResult.SUCCESS));
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException){
                    findResult.postValue(new FindResult(FindResult.FAILURE, R.string.wrong_username_message));
                }
                else if (e instanceof TimeoutException){
                    findResult.postValue(new FindResult(FindResult.FAILURE, R.string.time_out_message));
                }
                else{
                    findResult.postValue(new FindResult(FindResult.FAILURE, R.string.find_error_message));
                }
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

    private boolean isUsernameValid(String username){
        return username != null && username.trim().length() > 0;
    }
}
