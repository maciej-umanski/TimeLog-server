package com.jwmu.common;

import java.io.Serializable;
import java.sql.Timestamp;

public class Task implements Serializable {

    private int id;
    private String title;
    private String description;
    private Timestamp creationDate;
    private Timestamp dueToDate;
    private int creator;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getDueToDate() {
        return dueToDate;
    }

    public void setDueToDate(Timestamp dueToDate) {
        this.dueToDate = dueToDate;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }
}
