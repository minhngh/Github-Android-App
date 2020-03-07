package com.klxl.github.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.klxl.github.R;
import com.klxl.github.data.model.Project;
import com.klxl.github.data.model.ProjectInView;

import java.util.List;

public class RvProjectAdapter extends RecyclerView.Adapter<RvProjectAdapter.MyHolder> {
    private List<ProjectInView> projectInViews;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        ProjectInView data = projectInViews.get(position);
        holder.tvIdRepos.setText(String.valueOf(data.getId()));
        holder.tvReposName.setText(data.getName());
        holder.tvUserName.setText(data.getUserName());
    }

    @Override
    public int getItemCount() {
        return projectInViews == null ? 0 : projectInViews.size();
    }

    public void setDataChanged(@NonNull List<ProjectInView> projectInViews){
        this.projectInViews = projectInViews;
        notifyDataSetChanged();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView tvIdRepos;
        TextView tvReposName;
        TextView tvUserName;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvIdRepos = itemView.findViewById(R.id.tv_id_repos);
            tvReposName = itemView.findViewById(R.id.tv_repos_name);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
        }
    }
}
