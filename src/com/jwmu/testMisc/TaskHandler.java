package com.jwmu.testMisc;

import com.jwmu.common.Task;

import java.util.Date;

public class TaskHandler {

    public Task createTask(String userId, String title, String note, Date startDate, Date endDate){
        Task newTask = new Task(userId,title,note);
        if(newTask.setStartDate(startDate) && newTask.setEndDate(endDate)){
            return newTask;
        }else
            return null;
    }
}
