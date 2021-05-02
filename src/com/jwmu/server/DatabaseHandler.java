package com.jwmu.server;

import com.jwmu.common.Role;
import com.jwmu.common.User;

import java.sql.*;
import java.util.Optional;

public class DatabaseHandler extends Thread{
    private final String databaseUrl;
    private final String databaseUser;
    private final String databasePassword;
    private final ServerLogger logger;
    private final ConnectionHandler connectionHandler;

    private Connection connection;
    private boolean isConnected;

    public DatabaseHandler(ConnectionHandler connectionHandler, String databaseUrl, String databaseUser, String databasePassword, ServerLogger logger){
        this.connectionHandler = connectionHandler;
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
                this.connection.setAutoCommit(true);
                logger.databaseConnected();
                connectionHandler.setDatabaseConnectionState(true);
                return true;
            } catch (ClassNotFoundException | SQLException ignored) {
                logger.databaseConnectionRetry(count);
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(count++ == maxTries){
                    connectionHandler.setDatabaseConnectionState(false);
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

        boolean authenticate = resultSet.isBeforeFirst();

        statement.close();
        resultSet.close();

        return authenticate;
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

    public User getUserData(int id) throws SQLException {
        Statement statement = this.connection.createStatement();
        String str = "SELECT id, name, surname, mail, role FROM USERS WHERE id = '" + id + "';";
        ResultSet resultSet = statement.executeQuery(str);
        resultSet.next();
        User user = new User();
        user.setId(Integer.valueOf(resultSet.getString("id")));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setMail(resultSet.getString("mail"));
        Optional<Role> roleOptional = Role.valueOf(Integer.parseInt(resultSet.getString("role")));
        roleOptional.ifPresent(user::setRole);

        return user;
    }

    public User updateUserData(User user) throws SQLException {
        Statement statement = this.connection.createStatement();
        String str = "UPDATE USERS SET NAME = '" + user.getName() + "', SURNAME = '" +
                 user.getSurname() + "', MAIL = '" + user.getMail() + "' WHERE ID = " + user.getId();
        statement.executeUpdate(str);

        return getUserData(user.getId());
    }

    public boolean isUserExist(String username) throws SQLException {

        Statement statement = this.connection.createStatement();
        String str = "SELECT id FROM USERS WHERE USERNAME = '" + username + "';";
        ResultSet resultSet = statement.executeQuery(str);

        boolean isExist = resultSet.isBeforeFirst();

        statement.close();
        resultSet.close();

        return isExist;
    }

    public int registerUser(String username, String password) throws SQLException {
        Statement statement = this.connection.createStatement();
        String str = "INSERT INTO USERS(id, username, password, role) VALUES (nextval('user_seq'), '" + username + "', '" + password + "', " + Role.USER.ordinal() + ")";
        statement.executeUpdate(str);

        return getUserId(username);
    }

    public boolean checkConnection(){
        try {
            this.connection.createStatement().executeQuery("SELECT 1");
        } catch (SQLException ignored) {
            if(!(isConnected = connect())){
                connectionHandler.setDatabaseConnectionState(false);
                return false;
            }
        }
        connectionHandler.setDatabaseConnectionState(true);
        return true;
    }

    public boolean isConnected() {
        return isConnected;
    }
}
