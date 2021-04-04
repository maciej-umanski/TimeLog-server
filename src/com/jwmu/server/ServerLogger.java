package com.jwmu.server;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerLogger {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("[yy-MM-dd HH:mm:ss] ");

    //INFORMATION
    public void databaseConnect(){
        System.out.println(getDate() + "Trying to resolve connection to database");
    }

    public void databaseConnected(){
        System.out.println(getDate() + "Successfully connected to database");
    }

    public void userLoggedIn(int userId){
        System.out.println(getDate() + "User " + userId + " logged in");
    }

    public void userLoggedOff(int userId){
        System.out.println(getDate()+ "User " + userId + " logged off");
    }

    //ERROR
    public void databaseDisconnected(){
        System.err.println(getDate() + "Failed to connect to database");
    }

    public void databaseConnectionRetry(int count) {
        System.err.println(getDate() + count + " Trying to resolve connection to database, retry in 3 seconds");
    }

    public void databaseDisconnectedErr() {
        System.err.println(getDate() + "Cant resolve connection to database, every connection will be terminated, current will be saved locally");
    }

    //OTHERS

    private String getDate(){
        return simpleDateFormat.format(new Date());
    }
}
