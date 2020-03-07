package com.klxl.github.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.klxl.github.data.local.HistoryFind;
import com.klxl.github.data.local.HistoryFindDatabase;
import com.klxl.github.data.model.Project;
import com.klxl.github.data.model.ProjectInView;
import com.klxl.github.data.remote.GitHubService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectRepository {
    private GitHubService gitHubService = new Retrofit.Builder()
            .baseUrl(GitHubService.GITHUB_API_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubService.class);
    private MutableLiveData<List<ProjectInView>> projectList = new MutableLiveData<>();

    public ProjectRepository(){}

    public LiveData<List<ProjectInView>> getProjectList() {
        return projectList;
    }

    public Single<Result> getProjectListWithRemote(String username){
        return gitHubService.getProjectList(username)
                .map(projects -> {
                    if (projects != null){
                        List<ProjectInView> projectInViews = new ArrayList<>();
                        for(Project item : projects){
                            ProjectInView temp = new ProjectInView(item.getId(), item.getName(), item.getOwner().getLogin());
                            projectInViews.add(temp);
                        }
                        projectList.postValue(projectInViews);
                        return new Result.Success<>(true);
                    }
                    return new Result.Failure(new IOException("failure"));
                });
    }
}
