package com.jwmu.server;
import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {

    private boolean isLoggedIn = false;

    private final InputStream inputStream;
    private final OutputStream outputStream;
    private ResponseSender responseSender;
    private final DatabaseHandler databaseHandler;

    public ClientHandler(Socket newClientConnection, DatabaseHandler databaseHandler) throws IOException {
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

    protected void logIn(String Id){
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
}
