package com.jwmu.server;

import java.io.*;
import java.sql.SQLException;

public class RequestListener extends Thread{
    private final ObjectInputStream objectInputStream;
    RequestHandler requestHandler;

    public RequestListener(RequestHandler requestHandler, InputStream inputStream) throws IOException {
        this.objectInputStream = new ObjectInputStream(inputStream);
        this.requestHandler = requestHandler;
        this.start();
    }

    @Override
    public void run() {
        try {
            listen();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void listen() throws IOException, SQLException, ClassNotFoundException {
        Object request;
        while((request = objectInputStream.readObject()) != null){
            if(request.getClass() == String.class)
                handleStringRequest((String) request);
        }
    }

    private void handleStringRequest(String request) throws IOException, SQLException {
        String[] tokens = request.toLowerCase().split(" ");

        switch (tokens[0]) {
            case "register" -> requestHandler.registerUser(tokens);
            case "login" -> requestHandler.LoginUser(tokens);
            case "logoff" -> requestHandler.logoffUser();
            case "connection" -> requestHandler.connectionNotification();
            case "database" -> requestHandler.databaseNotification();
            case "task" -> requestHandler.sendTask();
            default -> requestHandler.wrongCommand();
        }
    }
}
