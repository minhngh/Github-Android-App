package com.klxl.github.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.klxl.github.R;
import com.klxl.github.ui.adapter.RvProjectAdapter;
import com.klxl.github.ui.find.FindActivity;
import com.klxl.github.viewmodel.ProjectViewModel;
import com.klxl.github.viewmodel.ProjectViewModelFactory;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    TextView tvSumRepos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findWidgets();
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(FindActivity.USER_KEY)){
                getSupportActionBar().setTitle(getIntent().getExtras().getString(FindActivity.USER_KEY));
            }
            else{
                getSupportActionBar().setTitle(R.string.app_name);
            }
        }


        RvProjectAdapter projectAdapter = new RvProjectAdapter();
        recyclerView.setAdapter(projectAdapter);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0,0);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        ProjectViewModel projectViewModel = new ViewModelProvider(this, ProjectViewModelFactory.getInstance(getApplication())).get(ProjectViewModel.class);
        projectViewModel.getAllProjects().observe(this, projectInViews -> {
                tvSumRepos.setText(String.format("Số repository(es): %d", projectInViews.size()));
                projectAdapter.setDataChanged(projectInViews);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void findWidgets(){
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        tvSumRepos = findViewById(R.id.tv_sum_repos);
    }
}
