package com.jwmu.common;

import java.io.Serializable;

public class Task implements Serializable {
    private final int taskId;
    private final String title;

    public Task(int taskId, String title){
        this.taskId = taskId;
        this.title = title;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }
}
