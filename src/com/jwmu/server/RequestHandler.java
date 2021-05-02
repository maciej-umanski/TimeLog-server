package com.jwmu.server;

import com.jwmu.common.Codes;
import com.jwmu.common.Role;
import com.jwmu.common.Task;
import com.jwmu.common.User;

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
                        int id = databaseHandler.getUserId(tokens[1]);
                        clientHandler.logIn(databaseHandler.getUserData(id));
                        responseSender.send(clientHandler.getUser());
                        logger.userLoggedIn(clientHandler.getUser().getId());
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
            logger.userLoggedOff(clientHandler.getUser().getId());
            clientHandler.logOff();
            responseSender.send(Codes.SUCCESSFUL_LOGOFF);
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
            if(clientHandler.getUser().getRole() != Role.OWNER){
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
            responseSender.send(this.clientHandler.getUser());
        }
    }

    public void modifyUser(User userNew) throws IOException {
        try {
            if (!databaseHandler.checkConnection()) {
                responseSender.send(Codes.DATABASE_DISCONNECTED);
                return;
            }
            if (!clientHandler.isLoggedIn()) {
                responseSender.send(Codes.CLIENT_NOT_LOGGED);
                return;
            }

            if(!clientHandler.getUser().getId().equals(userNew.getId())){
                responseSender.send(Codes.WRONG_ID);
                return;
            }

            User userOld = databaseHandler.getUserData(userNew.getId());

            if (userNew.getName() != null || !userOld.getName().equals(userNew.getName()))
                userOld.setName(userNew.getName());

            if (userNew.getSurname() != null || !userOld.getSurname().equals(userNew.getSurname()))
                userOld.setSurname(userNew.getSurname());

            if (userNew.getMail() != null || !userOld.getMail().equals(userNew.getMail()))
                userOld.setMail(userNew.getMail());

            User updatedUser = databaseHandler.updateUserData(userNew);

            responseSender.send(updatedUser);

        } catch (IOException | SQLException e) {
            responseSender.send(Codes.INTERNAL_ERROR);
            e.printStackTrace();
        }
    }
}
