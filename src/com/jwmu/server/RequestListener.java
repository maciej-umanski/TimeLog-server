package com.jwmu.server;

import com.jwmu.common.Task;
import com.jwmu.common.User;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

public class RequestListener extends Thread{

    private final ObjectInputStream objectInputStream;
    RequestHandler requestHandler;
    ServerLogger logger;

    public RequestListener(RequestHandler requestHandler, InputStream inputStream, ServerLogger logger) throws IOException {
        this.logger = logger;
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
        try{
            logger.clientConnected();
            while ((request = objectInputStream.readObject()) != null) {
                if (request instanceof String)
                    handleStringRequest((String) request);
                if (request instanceof User)
                    handleUserRequest((User) request);
                if (request instanceof List)
                    handleTaskRequest((List<Task>) request);
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            logger.clientDisconnected();
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
            case "get_task" -> requestHandler.sendTask(tokens);
            case "whoami" -> requestHandler.whoami();
            default -> requestHandler.wrongCommand();
        }
    }

    private void handleUserRequest(User user) throws IOException {
        requestHandler.modifyUser(user);
    }

    private void handleTaskRequest(List<Task> tasks) throws IOException {
        requestHandler.newTask(tasks);
    }
}
