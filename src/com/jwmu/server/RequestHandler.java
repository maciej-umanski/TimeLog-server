package com.jwmu.server;
import com.jwmu.common.CodesInterface;
import com.jwmu.common.Task;
import com.jwmu.testMisc.TaskHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class RequestHandler {
    private final ClientHandler clientHandler;
    private final ResponseSender responseSender;
    private final DatabaseHandler databaseHandler;

    public RequestHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.responseSender = clientHandler.getResponseSender();
        this.databaseHandler = clientHandler.getDatabaseHandler();
    }

    public void connectionNotification() throws IOException {
        responseSender.sendCode(CodesInterface.CLIENT_CONNECTED);
    }

    public void LoginUser(String[] tokens) throws IOException, SQLException {
        if(!clientHandler.isLoggedIn()){
            if (tokens.length == 3) {
                boolean isAuthenticated = databaseHandler.authenticateUser(tokens[1],tokens[2]);
                if (isAuthenticated) {
                    clientHandler.logIn(databaseHandler.getUserId(tokens[1]));
                    responseSender.sendCode(CodesInterface.SUCCESSFUL_LOGIN);
                }else{
                    responseSender.sendCode(CodesInterface.WRONG_CREDENTIALS);
                }
            } else {
                responseSender.sendCode(CodesInterface.WRONG_CREDENTIALS);
            }
        }else{
            responseSender.sendCode(CodesInterface.CLIENT_IS_LOGGED);
        }
    }

    public void logoffUser() throws IOException {
        if(!clientHandler.isLoggedIn()){
            responseSender.sendCode(CodesInterface.CLIENT_NOT_LOGGED);
        }else{
            clientHandler.logOff();
            responseSender.sendCode(CodesInterface.SUCCESSFUL_LOGOFF);
        }
    }

    public void wrongCommand() throws IOException {
        responseSender.sendCode(CodesInterface.WRONG_COMMAND);
    }

    public void sendExampleTask() throws IOException {
        if(clientHandler.isLoggedIn()){
            Task newTask = new TaskHandler().createTask("0","tytuł", "notatka", new Date(), new Date());
            responseSender.sendObject(newTask);
        }else{
            responseSender.sendCode(CodesInterface.CLIENT_NOT_LOGGED);
        }
    }
}
