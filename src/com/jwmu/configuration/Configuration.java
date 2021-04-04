package com.jwmu.configuration;

public class Configuration {
    private final String databaseUrl;
    private final String databaseUser;
    private final String databasePassword;
    private final int serverPort;

    public Configuration(String activeProfile){
        if(activeProfile.equalsIgnoreCase("develop")){
            databaseUrl = "jdbc:postgresql://postgres:5432/timelog";
        }else{
            databaseUrl = "jdbc:postgresql://localhost:5432/timelog";
        }
        databaseUser = "admin";
        databasePassword = "admin";
        serverPort = 8818;
    }

    public String getDatabaseUrl(){
        return databaseUrl;
    }

    public String getDatabaseUser(){
        return databaseUser;
    }

    public String getDatabasePassword(){
        return databasePassword;
    }

    public int getServerPort(){
        return serverPort;
    }
}
