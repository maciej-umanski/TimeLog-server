package com.jwmu.server;

import java.sql.*;

public class DatabaseHandler {
    private Connection connection = null;

    public DatabaseHandler(){
        try{
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection("jdbc:postgresql://balarama.db.elephantsql.com:5432/mwcnzcvg", "mwcnzcvg", "RW7RXxmv416OWBAKigB_AsdpN4kgOBPH");
            this.connection.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticateUser(String username, String password) throws SQLException {
        Statement statement = this.connection.createStatement();
        String str = "SELECT * FROM USERS WHERE USERNAME = '" + username + "' AND pass = '" + password + "';";
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
