package com.jwmu.server;

import com.jwmu.common.Codes;
import com.jwmu.common.Role;
import com.jwmu.common.Task;

import java.io.IOException;
import java.sql.SQLException;

public class RequestHandler {
    private final ClientHandler clientHandler;
    private final ResponseSender responseSender;
    private final DatabaseHandler databaseHandler;
    private final ServerLogger logger;

    public RequestHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.responseSender = clientHandler.getResponseSender();
        this.databaseHandler = clientHandler.getDatabaseHandler();
        this.logger = clientHandler.getLogger();
    }

    public void connectionNotification() throws IOException {
        responseSender.send(Codes.CLIENT_CONNECTED);
    }

    public void LoginUser(String[] tokens) throws IOException, SQLException {
        if(!clientHandler.isLoggedIn()){
            if(databaseHandler.checkConnection()){
                if (tokens.length == 3) {
                    boolean isAuthenticated = databaseHandler.authenticateUser(tokens[1], tokens[2]);
                    if (isAuthenticated) {
                        clientHandler.logIn(databaseHandler.getUserId(tokens[1]), databaseHandler.getRole(tokens[1]));
                        responseSender.send(Codes.SUCCESSFUL_LOGIN);
                        logger.userLoggedIn(clientHandler.getUserId());
                    } else {
                        responseSender.send(Codes.WRONG_CREDENTIALS);
                    }
                } else {
                    responseSender.send(Codes.WRONG_CREDENTIALS);
                }
            }else{
                responseSender.send(Codes.DATABASE_DISCONNECTED);
            }
        }else{
            responseSender.send(Codes.CLIENT_IS_LOGGED);
        }
    }

    public void logoffUser() throws IOException {
        if(!clientHandler.isLoggedIn()){
            responseSender.send(Codes.CLIENT_NOT_LOGGED);
        }else{
            clientHandler.logOff();
            responseSender.send(Codes.SUCCESSFUL_LOGOFF);
            logger.userLoggedOff(clientHandler.getUserId());
        }
    }

    public void wrongCommand() throws IOException {
        responseSender.send(Codes.WRONG_COMMAND);
    }

    public void databaseNotification() throws IOException {
        String codeToSend;
        if(databaseHandler.checkConnection())
            codeToSend = Codes.DATABASE_CONNECTED;
        else
            codeToSend = Codes.DATABASE_DISCONNECTED;

        responseSender.send(codeToSend);
    }

    public void sendTask() throws IOException {
        Task taskToSend = new Task(0,"test");
        responseSender.send(taskToSend);
    }

    public void registerUser(String[] tokens) throws IOException {
        try{
            if (!databaseHandler.checkConnection()) {
                responseSender.send(Codes.DATABASE_DISCONNECTED);
                return;
            }
            if (!clientHandler.isLoggedIn()) {
                responseSender.send(Codes.CLIENT_NOT_LOGGED);
                return;
            }
            if(clientHandler.getRole() != Role.OWNER){
                responseSender.send(Codes.NO_PERMISSION);
                return;
            }
            if (tokens.length != 3) {
                responseSender.send(Codes.WRONG_CREDENTIALS);
                return;
            }
            if (databaseHandler.isUserExist(tokens[1])) {
                responseSender.send(Codes.LOGIN_EXIST);
                return;
            }

            int id = databaseHandler.registerUser(tokens[1], tokens[2]);
            logger.userRegistered(id, tokens[1]);
            responseSender.send(Codes.SUCCESSFUL_REGISTER);

        }catch (IOException | SQLException e) {
            responseSender.send(Codes.INTERNAL_ERROR);
            e.printStackTrace();
        }
    }

    public void whoami() throws IOException {
        if (!clientHandler.isLoggedIn()) {
            responseSender.send(Codes.CLIENT_NOT_LOGGED);
        }else{
            responseSender.send(this.clientHandler.getRole());
        }
    }
}
