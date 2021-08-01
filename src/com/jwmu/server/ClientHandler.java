package com.jwmu.server;

import com.jwmu.common.Role;
import com.jwmu.common.User;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {

    private User user;
    private boolean isLoggedIn = false;

    private final InputStream inputStream;
    private final ResponseSender responseSender;
    private final DatabaseHandler databaseHandler;

    private final ServerLogger logger;

    public ClientHandler(Socket newClientConnection, DatabaseHandler databaseHandler, ServerLogger logger) throws IOException {
        this.logger = logger;
        this.inputStream = new DataInputStream(newClientConnection.getInputStream());
        OutputStream outputStream = new DataOutputStream(newClientConnection.getOutputStream());
        this.databaseHandler = databaseHandler;
        this.responseSender = new ResponseSender(outputStream);
        this.start();
    }

    @Override
    public void run() {
        try {
            RequestHandler requestHandler = new RequestHandler(this);
            new RequestListener(requestHandler, inputStream, logger);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    protected void logIn(User user){
        this.user = user;
        isLoggedIn = true;
    }

    protected void logOff(){
        user = null;
        isLoggedIn = false;
    }

    public ResponseSender getResponseSender() {
        return responseSender;
    }

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public ServerLogger getLogger(){
        return logger;
    }

    public User getUser() {
        return user;
    }
}
