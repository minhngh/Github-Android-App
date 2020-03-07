package com.klxl.github.data.remote;

import com.klxl.github.data.model.Project;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {
    String GITHUB_API_URL = "https://api.github.com";
    @GET("/users/{user}/repos")
    Single<List<Project>> getProjectList(@Path("user")String user);
}
