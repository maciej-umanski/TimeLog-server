package com.jwmu.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class ConnectionHandler extends Thread{

    private final String databaseUrl;
    private final String databaseUser;
    private final String databasePassword;
    private final int serverPort;
    private final ServerLogger logger;

    private ServerSocket serverSocket;
    protected DatabaseHandler databaseHandler;
    private boolean isServerActive = false;
    private boolean isDatabaseConnected = false;
    boolean errPromptShown = false;

    public ConnectionHandler(String databaseUrl, String databaseUser, String databasePassword, int serverPort, ServerLogger logger) {
        this.logger = logger;
        this.databaseUrl = databaseUrl;
        this.databaseUser = databaseUser;
        this.databasePassword = databasePassword;
        this.serverPort = serverPort;
        this.start();
    }

    @Override
    public void run() {
        try {
            initializeServer();
            initializeDatabase();
            while(isServerActive && isDatabaseConnected){
                handleNewConnections();
                if(!isDatabaseConnected && !errPromptShown) {
                    logger.databaseDisconnectedErr();
                    errPromptShown = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeServer() throws IOException {
        serverSocket = new ServerSocket(serverPort);
        isServerActive = true;
    }

    private void initializeDatabase(){
        databaseHandler = new DatabaseHandler(this, databaseUrl, databaseUser, databasePassword, logger);
        isDatabaseConnected = databaseHandler.isConnected();
    }

    private void handleNewConnections() throws IOException {
        Socket newClient = serverSocket.accept();
        isDatabaseConnected = databaseHandler.checkConnection();
        if(newClient != null)
            if (isDatabaseConnected) {
                new ClientHandler(newClient, databaseHandler, logger);
                errPromptShown = false;
            }
            else
                newClient.close();
    }

    public void setDatabaseConnectionState(boolean isDatabaseConnected){
        this.isDatabaseConnected = isDatabaseConnected;
    }

}
