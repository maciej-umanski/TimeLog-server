package com.jwmu.server;

import com.jwmu.common.CodesInterface;
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
        responseSender.send(CodesInterface.CLIENT_CONNECTED);
    }

    public void LoginUser(String[] tokens) throws IOException, SQLException {
        if(!clientHandler.isLoggedIn()){
            if(databaseHandler.checkConnection()){
                if (tokens.length == 3) {
                    boolean isAuthenticated = databaseHandler.authenticateUser(tokens[1], tokens[2]);
                    if (isAuthenticated) {
                        clientHandler.logIn(databaseHandler.getUserId(tokens[1]));
                        responseSender.send(CodesInterface.SUCCESSFUL_LOGIN);
                        logger.userLoggedIn(clientHandler.getUserId());
                    } else {
                        responseSender.send(CodesInterface.WRONG_CREDENTIALS);
                    }
                } else {
                    responseSender.send(CodesInterface.WRONG_CREDENTIALS);
                }
            }else{
                responseSender.send(CodesInterface.DATABASE_DISCONNECTED);
            }
        }else{
            responseSender.send(CodesInterface.CLIENT_IS_LOGGED);
        }
    }

    public void logoffUser() throws IOException {
        if(!clientHandler.isLoggedIn()){
            responseSender.send(CodesInterface.CLIENT_NOT_LOGGED);
        }else{
            clientHandler.logOff();
            responseSender.send(CodesInterface.SUCCESSFUL_LOGOFF);
            logger.userLoggedOff(clientHandler.getUserId());
        }
    }

    public void wrongCommand() throws IOException {
        responseSender.send(CodesInterface.WRONG_COMMAND);
    }

    public void databaseNotification() throws IOException {
        String codeToSend;
        if(databaseHandler.checkConnection())
            codeToSend = CodesInterface.DATABASE_CONNECTED;
        else
            codeToSend = CodesInterface.DATABASE_DISCONNECTED;

        responseSender.send(codeToSend);
    }

    public void sendTask() throws IOException {
        Task taskToSend = new Task(0,"test");
        responseSender.send(taskToSend);
    }
}
