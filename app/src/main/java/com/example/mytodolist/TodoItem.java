package com.example.mytodolist;

public class TodoItem {
    private String id;
    private String titleName;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return titleName;
    }

    public void setTaskName(String titleName) {
        this.titleName = titleName;
    }
}
