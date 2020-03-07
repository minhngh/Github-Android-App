package com.klxl.github.data.model;

public class ProjectInView {
    private int id;
    private String name;
    private String userName;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public ProjectInView(int id, String name, String userName){
        this.id = id;
        this.name = name;
        this.userName = userName;
    }
}