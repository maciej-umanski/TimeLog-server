package com.jwmu.common;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    private final String userID;
    private Date startDate;
    private Date endDate;
    private String title;
    private String note;
    private boolean isStarted;
    private boolean isFinished;

    public Task(String userID, String title, String note){
        this.userID = userID;
        this.title = title;
        this.note = note;
        this.startDate = new Date();
        this.endDate = new Date();
    }

    public String getUserID() {
        return userID;
    }
    public String getTitle(){
        return title;
    }
    public Date getStartDate() {
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public String getNote() {
        return note;
    }
    public boolean isStarted(){
        return isStarted;
    }
    public boolean isFinished() {
        return isFinished;
    }

    public void setTitle(String newTitle){
        this.title = newTitle;
    }
    public void setNote(String newNote){
        this.note = newNote;
    }

    public boolean setStartDate(Date newStartDate){
        if(endDate != null && newStartDate.after(endDate))
            return false;
        else{
            this.startDate = newStartDate;
            this.updateStartState();
            return true;
        }
    }
    public boolean setEndDate(Date newEndDate){
        if(startDate != null && newEndDate.before(this.startDate)){
            return false;
        }else{
            this.endDate = newEndDate;
            this.updateFinishState();
            return true;
        }
    }

    private void updateStartState(){
        this.isStarted = this.startDate.before(new Date());
    }
    private void updateFinishState() {
        this.isFinished = this.endDate.before(new Date());
    }

    public String getFormattedTaskAsString(){
        return title + " " + note + " " + startDate + " " + endDate + " " + isStarted + " " + isFinished;
    }
}
