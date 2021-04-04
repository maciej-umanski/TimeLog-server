package com.jwmu.server;
import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {

    private int userId;
    private boolean isLoggedIn = false;

    private final InputStream inputStream;
    private final OutputStream outputStream;
    private ResponseSender responseSender;
    private final DatabaseHandler databaseHandler;

    private final ServerLogger logger;

    public ClientHandler(Socket newClientConnection, DatabaseHandler databaseHandler, ServerLogger logger) throws IOException {
        this.logger = logger;
        this.inputStream = new DataInputStream(newClientConnection.getInputStream());
        this.outputStream = new DataOutputStream(newClientConnection.getOutputStream());
        this.databaseHandler = databaseHandler;
        this.start();
    }

    @Override
    public void run() {
        try {
            responseSender = new ResponseSender(outputStream);
            RequestHandler requestHandler = new RequestHandler(this);
            new RequestListener(requestHandler, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    protected void logIn(int userId){
        this.userId = userId;
        this.isLoggedIn = true;
    }

    protected void logOff(){
        this.isLoggedIn = false;
    }

    public ResponseSender getResponseSender() {
        return responseSender;
    }

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public int getUserId() {
        return userId;
    }

    public ServerLogger getLogger(){
        return logger;
    }
}
