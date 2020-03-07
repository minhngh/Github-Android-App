package com.klxl.github.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.klxl.github.data.ProjectRepository;

public class ProjectViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private static ProjectViewModelFactory projectViewModelFactory;
    private ProjectViewModelFactory(Application application){
        this.application = application;
    }
    public static ProjectViewModelFactory getInstance(Application application){
        if (projectViewModelFactory == null){
            synchronized (ProjectViewModelFactory.class){
                if (projectViewModelFactory == null){
                    projectViewModelFactory = new ProjectViewModelFactory(application);
                }
            }
        }
        return projectViewModelFactory;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ProjectViewModel.class)){
            return (T) ProjectViewModel.getInstance();
        }
        throw new IllegalArgumentException("Unknown viewmodel class");
    }
}
