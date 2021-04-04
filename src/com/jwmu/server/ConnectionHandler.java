package com.jwmu.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
        databaseHandler = new DatabaseHandler(databaseUrl, databaseUser, databasePassword, logger);
        isDatabaseConnected = databaseHandler.isConnected();
    }

    private void handleNewConnections() throws IOException {
        new ClientHandler(serverSocket.accept(), databaseHandler, logger);
    }


}
