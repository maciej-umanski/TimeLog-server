package com.jwmu.server;

import java.sql.*;

public class DatabaseHandler extends Thread{
    private final String databaseUrl;
    private final String databaseUser;
    private final String databasePassword;
    private final ServerLogger logger;

    private Connection connection;
    private boolean isConnected;

    public DatabaseHandler(String databaseUrl, String databaseUser, String databasePassword, ServerLogger logger){
        this.logger = logger;
        this.databaseUrl = databaseUrl;
        this.databaseUser = databaseUser;
        this.databasePassword = databasePassword;
        isConnected = connect();
    }

    private boolean connect(){
        int count = 1;
        final int maxTries = 3;
        logger.databaseConnect();
        while(true){
            try {
                Class.forName("org.postgresql.Driver");
                this.connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
                this.connection.setAutoCommit(false);
                logger.databaseConnected();
                return true;
            } catch (ClassNotFoundException | SQLException ignored) {
                logger.databaseConnectionRetry(count);
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(count++ == maxTries){
                    logger.databaseDisconnected();
                    return false;
                }
            }
        }
    }

    public boolean authenticateUser(String username, String password) throws SQLException {
        Statement statement = this.connection.createStatement();
        String str = "SELECT * FROM USERS WHERE USERNAME = '" + username + "' AND PASSWORD = '" + password + "';";
        ResultSet resultSet = statement.executeQuery(str);
        if (!resultSet.isBeforeFirst()) {
            statement.close();
            resultSet.close();
            return false;
        }
        statement.close();
        resultSet.close();
        return true;
    }

    public int getUserId(String username) throws SQLException {
        Statement statement = this.connection.createStatement();
        String str = "SELECT id FROM USERS WHERE USERNAME = '" + username + "';";
        ResultSet resultSet = statement.executeQuery(str);
        resultSet.next();
        String id = resultSet.getString("id");
        statement.close();
        resultSet.close();
        return Integer.parseInt(id);
    }

    public boolean checkConnection(){
        try {
            this.connection.createStatement().executeQuery("SELECT 1");
        } catch (SQLException ignored) {
            logger.databaseReconnection();
            if(!(isConnected = connect())){
                return false;
            }
        }
        return true;
    }

    public boolean isConnected() {
        return isConnected;
    }
}
