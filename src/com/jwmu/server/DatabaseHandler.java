package com.jwmu.server;

import java.sql.*;

public class DatabaseHandler {
    private Connection connection;

    public DatabaseHandler(){
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/timelog", "admin", "admin");
            this.connection.setAutoCommit(false);
            System.out.println("Successfully connected to database");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.print('e');
        }
    }

    public boolean authenticateUser(String username, String password) throws SQLException {
        Statement statement = this.connection.createStatement();
        String str = "SELECT * FROM USERS WHERE USERNAME = '" + username + "' AND PASSWORD = '" + password + "';";
        ResultSet resultSet = statement.executeQuery(str);
        if(!resultSet.isBeforeFirst()){
            statement.close();
            resultSet.close();
            return false;
        }
        statement.close();
        resultSet.close();
        return true;
    }

    public String getUserId(String username) throws SQLException {
        Statement statement = this.connection.createStatement();
        String str = "SELECT id FROM USERS WHERE USERNAME = '" + username + "';";
        ResultSet resultSet = statement.executeQuery(str);
        resultSet.next();
        String id = resultSet.getString("id");
        statement.close();
        resultSet.close();
        return id;
    }

}
